package Roma.magic;

import Roma.magic.config.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma")
public class PlayerCapabilityEvent {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(ManaProvider.ID, new ManaProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // 1. CRITICAL FORGE FIX: Revive dead capabilities before attempting to read them!
        event.getOriginal().reviveCaps();

        event.getOriginal().getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(oldStore -> {
            event.getEntity().getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(newStore -> {
                newStore.setMaxMana(oldStore.getMaxMana());
                newStore.setMana(oldStore.getMana());
                newStore.setMagicDamage(oldStore.getMagicDamage());
                newStore.setManaRegenTime(oldStore.getManaRegenTime());
                newStore.setManaRegenRate(oldStore.getManaRegenRate());
            });
        });

        // 2. Invalidate them again after copying to prevent memory leaks
        event.getOriginal().invalidateCaps();
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpellUtil.syncManaToClient(serverPlayer);
        }
    }

    // 3. NEW EVENT: Sync stats to client when respawning after death!
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpellUtil.syncManaToClient(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpellUtil.syncManaToClient(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        ManaRegeneration.cleanupPlayer(event.getEntity().getUUID());
    }
}