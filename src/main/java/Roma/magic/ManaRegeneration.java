package Roma.magic;

import Roma.magic.config.ManaConfig;
import Roma.magic.config.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma")
public class ManaRegeneration {

    // Track when each player last used mana for delay functionality
    private static final java.util.Map<java.util.UUID, Long> lastManaUse = new java.util.HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;

            // Check if regeneration is enabled and if it's time to regenerate
            if (ManaConfig.isEnableManaRegen() &&
                    player.tickCount % ManaConfig.getRegenIntervalTicks() == 0) {

                player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                    if (mana.getMana() < mana.getMaxMana()) {
                        // Check if enough time has passed since last mana use
                        long currentTime = player.level().getGameTime();
                        long lastUse = lastManaUse.getOrDefault(player.getUUID(), 0L);

                        // Use config delay (convert ticks to game time)
                        if (currentTime - lastUse >= ManaConfig.getManaRegenDelay()) {
                            int oldMana = mana.getMana();
                            mana.addMana(ManaConfig.getManaRegenRate());

                            // Sync to client if mana changed
                            if (mana.getMana() != oldMana && player instanceof ServerPlayer serverPlayer) {
                                NetworkHandler.sendToPlayer(
                                        new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                                        serverPlayer
                                );
                            }
                        }
                    }
                });
            }
        }
    }

    // Call this method when mana is consumed to track usage time
    public static void onManaUsed(Player player) {
        lastManaUse.put(player.getUUID(), player.level().getGameTime());
    }

    // Clean up tracking when player leaves
    public static void cleanupPlayer(java.util.UUID playerUUID) {
        lastManaUse.remove(playerUUID);
    }

    @SubscribeEvent
    public static void onPlayerLogout(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent event) {
        // Automatically remove the player's UUID from the RAM map when they leave
        cleanupPlayer(event.getEntity().getUUID());
    }
}