package Roma.modcommands;

import Roma.util.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = "rma")
public class CustomKIHandler {

    private static final Map<UUID, List<ItemStack>> keepItemCache = new HashMap<>();
    private static final Map<UUID, Integer> playerXPCache = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (!ModCommands.CUSTOM_KEEP_INVENTORY) return;

        // Save items to keep
        List<ItemStack> savedItems = new ArrayList<>();
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && stack.is(ModTags.Items.KEEPONDEATH)) {
                savedItems.add(stack.copy());
            }
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.OFFHAND) {
                ItemStack stack = player.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.is(ModTags.Items.KEEPONDEATH)) {
                    savedItems.add(stack.copy());
                }
            }
        }
        keepItemCache.put(player.getUUID(), savedItems);

        // Calculate total XP properly
        int totalXP = getPlayerTotalXP(player);
        playerXPCache.put(player.getUUID(), totalXP);

        System.out.println("[CustomKI] Saved " + savedItems.size() + " items and " + totalXP + " XP for player " + player.getName().getString());
    }

    @SubscribeEvent
    public static void onPlayerDropItems(LivingDropsEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer)) return;
        if (!ModCommands.CUSTOM_KEEP_INVENTORY) return;

        // Remove all item drops on death so no duplication
        event.getDrops().clear();
        System.out.println("[CustomKI] Cleared all dropped items on death.");
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;
        if (!event.isWasDeath() || !ModCommands.CUSTOM_KEEP_INVENTORY) return;

        UUID uuid = newPlayer.getUUID();

        // Restore items
        List<ItemStack> restore = keepItemCache.remove(uuid);
        if (restore != null && !restore.isEmpty()) {
            newPlayer.getServer().execute(() -> {
                for (ItemStack item : restore) {
                    if (!newPlayer.getInventory().add(item)) {
                        newPlayer.drop(item, false);
                    }
                }
            });
        }

        // Restore 1/3 XP
        Integer oldXP = playerXPCache.remove(uuid);
        if (oldXP != null) {
            int newXP = oldXP / 3;
            setPlayerXP(newPlayer, newXP);
        }
    }

    private static int getPlayerTotalXP(ServerPlayer player) {
        int xp = 0;
        int level = player.experienceLevel;
        if (level > 0) {
            // XP for all levels below current
            xp += (int) (7.0 * level * (level + 1));
            // Add progress to next level
            xp += (int) (player.experienceProgress * player.getXpNeededForNextLevel());
        }
        return xp;
    }

    private static void setPlayerXP(ServerPlayer player, int totalXP) {
        player.totalExperience = totalXP;
        player.experienceLevel = 0;
        player.experienceProgress = 0f;

        while (totalXP > player.getXpNeededForNextLevel()) {
            totalXP -= player.getXpNeededForNextLevel();
            player.experienceLevel++;
        }

        player.experienceProgress = (float) totalXP / (float) player.getXpNeededForNextLevel();
        player.onUpdateAbilities();
    }
}
