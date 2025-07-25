package Roma.magic;

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
        event.getOriginal().getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(oldStore -> {
            event.getEntity().getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(newStore -> {
                newStore.setMaxMana(oldStore.getMaxMana());
                newStore.setMana(oldStore.getMana());
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Sync mana to client when player logs in
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpellUtil.syncManaToClient(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // Sync mana when changing dimensions
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            SpellUtil.syncManaToClient(serverPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // Clean up mana regeneration tracking
        ManaRegeneration.cleanupPlayer(event.getEntity().getUUID());
    }
}