package Roma.magic;

import Roma.magic.config.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SpellUtil {

    /**
     * Attempts to cast a spell, consuming the required mana if available
     * @param player The player casting the spell
     * @param manaCost The mana cost of the spell
     * @return true if the spell was successfully cast, false if not enough mana
     */
    public static boolean tryCastSpell(Player player, int manaCost) {
        return player.getCapability(ManaCapability.MANA_CAPABILITY).map(mana -> {
            if (mana.canConsumeMana(manaCost)) {
                mana.consumeMana(manaCost);

                // Track mana usage for regeneration delay
                ManaRegeneration.onManaUsed(player);

                // Sync to client if on server
                if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(
                            new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                            serverPlayer
                    );
                }
                return true;
            }
            return false;
        }).orElse(false);
    }

    /**
     * Gets the player's current mana
     */
    public static int getPlayerMana(Player player) {
        return player.getCapability(ManaCapability.MANA_CAPABILITY)
                .map(IMana::getMana)
                .orElse(0);
    }

    /**
     * Gets the player's max mana
     */
    public static int getPlayerMaxMana(Player player) {
        return player.getCapability(ManaCapability.MANA_CAPABILITY)
                .map(IMana::getMaxMana)
                .orElse(0);
    }

    /**
     * Gets the player's magic damage
     */
    public static int getPlayerMagicDamage(Player player) {
        return player.getCapability(ManaCapability.MANA_CAPABILITY)
                .map(IMana::getMagicDamage)
                .orElse(0);
    }

    /**
     * Checks if player has enough mana for a spell
     */
    public static boolean hasEnoughMana(Player player, int manaCost) {
        return player.getCapability(ManaCapability.MANA_CAPABILITY)
                .map(mana -> mana.canConsumeMana(manaCost))
                .orElse(false);
    }

    /**
     * Restores mana to a player
     */
    public static void restoreMana(Player player, int amount) {
        player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
            mana.addMana(amount);

            // Sync to client if on server
            if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                NetworkHandler.sendToPlayer(
                        new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                        serverPlayer
                );
            }
        });
    }

    /**
     * Sets a player's max mana and syncs to client
     */
    public static void setPlayerMaxMana(Player player, int maxMana) {
        player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
            mana.setMaxMana(maxMana);

            // Sync to client if on server
            if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
                NetworkHandler.sendToPlayer(
                        new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                        serverPlayer
                );
            }
        });
    }

    /**
     * Forces a mana sync to client (useful after loading from NBT)
     */
    public static void syncManaToClient(Player player) {
        if (!player.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                NetworkHandler.sendToPlayer(
                        new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                        serverPlayer
                );
                NetworkHandler.sendToPlayer(
                        new MagicDamageSyncPacket(mana.getMagicDamage()),
                        serverPlayer
                );
            });
        }
    }
}