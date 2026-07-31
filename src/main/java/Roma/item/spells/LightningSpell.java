package Roma.item.spells;

import Roma.menu.skillmenu.SkillUtil;
import Roma.menu.stats.ModStats;
import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class LightningSpell extends Spell {
    private int burnDuration = 5;

    public LightningSpell() {
        super("Lightning", 10, 0, SpellType.OFFENSIVE);
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
            HitResult hitResult = player.pick(20, 0, false);
            BlockPos centerPos;

            // FIX: Safely checks if they hit a block or if they aimed at the sky
            if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHit) {
                centerPos = blockHit.getBlockPos().above();
            } else {
                centerPos = player.blockPosition().relative(player.getDirection(), 10);
            }

            createLightningPattern(level, centerPos);
            damageNearbyEntities(level, centerPos, player);

            if (level instanceof ServerLevel serverLevel) {
                addVisualEffects(serverLevel, centerPos);
            }
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                // Awards XP equal to the mana spent!
                serverPlayer.awardStat(ModStats.MAGIC_USED.get(), this.manaCost);
                SkillUtil.syncMagicMana(serverPlayer);
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
        int MagicDamage = SpellUtil.getPlayerMagicDamage(caster);
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(centerPos).inflate(3.0),
                entity -> entity != caster && entity.isAlive()
        );

        for (LivingEntity entity : nearbyEntities) {
            entity.hurt(level.damageSources().magic(), MagicDamage);
            entity.setSecondsOnFire(burnDuration);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0));
        }
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        for (int i = 0; i < 20; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * 6;
            double y = centerPos.getY() + Math.random() * 3;
            double z = centerPos.getZ() + (Math.random() - 0.5) * 6;
            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 3, 0.2, 0.2, 0.2, 0.1);
        }
    }

    @Override
    protected boolean hasSufficientMana(Player player) {
        return SpellUtil.hasEnoughMana(player, manaCost);
    }
    @Override
    protected boolean isOnCooldown(Player player) {
        return getCooldownFromPlayer(player, this) > 0;
    }
    @Override
    protected void consumeMana(Player player) {}
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