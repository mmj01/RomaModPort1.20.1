package Roma.item.spells;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma")
public class SpellEvents {

    @SubscribeEvent
    public static void onEnderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        // Check if the entity being teleported is a ServerPlayer
        if (event.getEntity() instanceof ServerPlayer player) {

            // Get the pearl that caused the event
            ThrownEnderpearl pearl = event.getPearlEntity();

            // Check if our custom NBT tag exists on this specific pearl
            if (pearl.getPersistentData().getBoolean("is_travel_spell")) {

                // 1. Set the pearl teleportation damage to 0 (Forge 1.20.1 feature)
                event.setAttackDamage(0.0F);
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 30, 1,false, false ));

                // 2. Reset fall distance so high-altitude casts don't kill them on landing
                player.fallDistance = 0.0F;
            }
        }
    }
}