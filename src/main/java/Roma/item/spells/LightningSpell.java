// Basic Enhanced Lightning Spell with Direct Damage
package Roma.item.spells;

import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class LightningSpell extends Spell {
    private float additionalDamage = 15.0f; // Extra magic damage
    private int burnDuration = 5; // Seconds on fire

    public LightningSpell() {
        super("Enhanced Lightning", 2, 0, SpellType.OFFENSIVE); // Higher mana cost and cooldown
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        if (!SpellUtil.tryCastSpell(player, manaCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + manaCost + ", have " + currentMana));
            return false;
        }

        try {
            BlockHitResult result = (BlockHitResult) player.pick(20, 0, false);
            BlockPos centerPos = result.getBlockPos().above();

            // Create 3x3 lightning strikes
            createLightningPattern(level, centerPos);

            // Deal additional damage to nearby entities
            damageNearbyEntities(level, centerPos, player);

            // Add visual effects
            if (level instanceof ServerLevel serverLevel) {
                addVisualEffects(serverLevel, centerPos);
            }

            applyCooldown(player);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createLightningPattern(Level level, BlockPos centerPos) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos strikePos = centerPos.offset(x, 0, z);
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                if (lightning != null) {
                    lightning.moveTo(strikePos.getX() + 0.5, strikePos.getY(), strikePos.getZ() + 0.5);
                    level.addFreshEntity(lightning);
                }
            }
        }
    }

    private void damageNearbyEntities(Level level, BlockPos centerPos, Player caster) {
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(centerPos).inflate(3.0), // 3 block radius
                entity -> entity != caster && entity.isAlive()
        );

        for (LivingEntity entity : nearbyEntities) {
            // Deal additional magic damage
            entity.hurt(level.damageSources().magic(), additionalDamage);

            // Set on fire
            entity.setSecondsOnFire(burnDuration);

            // Add status effects
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1)); // Slowness II for 3 seconds
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0)); // Weakness I for 5 seconds
        }
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        // Electric spark particles around the area
        for (int i = 0; i < 20; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * 6;
            double y = centerPos.getY() + Math.random() * 3;
            double z = centerPos.getZ() + (Math.random() - 0.5) * 6;

            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 3, 0.2, 0.2, 0.2, 0.1);
        }
    }

    // Standard spell methods...
    @Override
    protected boolean hasSufficientMana(Player player) {
        return SpellUtil.hasEnoughMana(player, manaCost);
    }

    @Override
    protected boolean isOnCooldown(Player player) {
        return getCooldownFromPlayer(player, this) > 0;
    }

    @Override
    protected void consumeMana(Player player) {
        // Handled by SpellUtil.tryCastSpell()
    }

    @Override
    protected void applyCooldown(Player player) {
        setCooldownForPlayer(player, this, cooldown);
    }

    private int getCooldownFromPlayer(Player player, Spell spell) {
        return player.getPersistentData().getInt("cooldown_" + spell.getName());
    }

    private void setCooldownForPlayer(Player player, Spell spell, int ticks) {
        player.getPersistentData().putInt("cooldown_" + spell.getName(), ticks);
    }
}