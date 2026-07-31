package Roma.menu.skillmenu;

import Roma.magic.MagicDamageSyncPacket;
import Roma.magic.ManaCapability;
import Roma.magic.ManaSyncPacket;
import Roma.magic.config.NetworkHandler;
import Roma.menu.stats.ModStats;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntUnaryOperator;

public class SkillUtil {

    // Internal record to hold cached calculation state
    private record CachedSkill(int lastRawValue, int calculatedLevel) {}

    // Keyed by "PlayerUUID + StatResourceLocation" to track individual skills per player
    private static final Map<String, CachedSkill> LEVEL_CACHE = new ConcurrentHashMap<>();

    /**
     * Safely reads a statistic value from either a ServerPlayer or a LocalPlayer.
     */
    public static int getStatValueSafe(Player player, Stat<?> stat) {
        if (player == null || stat == null) return 0;

        if (player instanceof ServerPlayer serverPlayer) {
            return serverPlayer.getStats().getValue(stat);
        }
        if (player instanceof LocalPlayer localPlayer) {
            return localPlayer.getStats().getValue(stat);
        }

        return 0;
    }

    /**
     * CACHED LEVEL CALCULATOR:
     * Returns the level in O(1) time if the player's raw statistic hasn't changed.
     * Only runs the math loop when XP is gained.
     */
    public static int getSkillLevel(Player player, Stat<?> stat, IntUnaryOperator xpCurve) {
        if (player == null || stat == null) return 1;

        int currentRawValue = getStatValueSafe(player, stat);
        String cacheKey = player.getUUID().toString() + "_" + stat.toString();

        // 1. FAST PATH: Check if we already calculated the level for this exact XP amount
        CachedSkill cached = LEVEL_CACHE.get(cacheKey);
        if (cached != null && cached.lastRawValue == currentRawValue) {
            return cached.calculatedLevel;
        }

        // 2. SLOW PATH: Calculate level only because XP value changed
        int level = 1;
        int remainingXp = currentRawValue;
        int xpForNextLevel = Math.max(1, xpCurve.applyAsInt(level));

        // Dynamically calculates current level by iterating through the XP curve
        while (remainingXp >= xpForNextLevel) {
            remainingXp -= xpForNextLevel;
            level++;
            xpForNextLevel = Math.max(1, xpCurve.applyAsInt(level));
        }

        // 3. Store in cache for future O(1) lookups
        LEVEL_CACHE.put(cacheKey, new CachedSkill(currentRawValue, level));
        return level;
    }

    /**
     * Call this inside a PlayerLoggedOutEvent if you want to clear memory when players leave.
     */
    public static void clearPlayerCache(Player player) {
        if (player == null) return;
        String prefix = player.getUUID().toString();
        LEVEL_CACHE.keySet().removeIf(key -> key.startsWith(prefix));
    }

    /**
     * Checks if the player leveled up and grants +25 Max Mana ONLY on level-up.
     */
    public static void syncMagicMana(Player player) {
        // Only run on the server
        if (player == null || player.level().isClientSide()) return;

        Stat<?> magicStat = Stats.CUSTOM.get(ModStats.MAGIC_USED.get());
        IntUnaryOperator curve = x -> (int) (100 * Math.pow(1.30, x - 1));

        int currentRawValue = getStatValueSafe(player, magicStat);
        String cacheKey = player.getUUID().toString() + "_" + magicStat.toString();

        // Get the previously cached level before we update it
        CachedSkill cached = LEVEL_CACHE.get(cacheKey);
        int oldLevel = (cached != null) ? cached.calculatedLevel : 1;

        // Calculate the current level (this updates the cache inside getSkillLevel)
        int newLevel = getSkillLevel(player, magicStat, curve);

        // Check if the player has advanced to a higher level!
        if (newLevel > oldLevel) {
            int levelsGained = newLevel - oldLevel;
            int manaIncrease = levelsGained * 25;
            int DamageIncrease = levelsGained * 5;

            player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                int newMax = mana.getMaxMana() + manaIncrease;
                int newCurrent = mana.getMana() + manaIncrease; // Fill the new mana pool on level-up

                int NewDamage = mana.getMagicDamage() + DamageIncrease;

                mana.setMaxMana(newMax);
                mana.setMana(newCurrent);
                mana.setMagicDamage(NewDamage);

                // Send network packet to update the HUD client-side
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(newCurrent, newMax), serverPlayer);
                    NetworkHandler.sendToPlayer(new MagicDamageSyncPacket(NewDamage), serverPlayer);
                    serverPlayer.sendSystemMessage(Component.literal("§b✦ Spellcasting Level Up! Reached Level " + newLevel + " (+25 Max Mana) and (+5 Magic Damage)"));
                }
            });
        }
    }
}