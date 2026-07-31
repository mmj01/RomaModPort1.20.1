package Roma.item.spells;

import Roma.menu.skillmenu.SkillUtil;
import Roma.menu.stats.ModStats;
import Roma.magic.SpellUtil;
import Roma.magic.config.ManaConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

public class ChainLightningSpell extends Spell {
    private int maxChains = 35; // Capped safely to prevent lag
    private float damageMultiplier = 1.3f; // 30% increase per jump

    public ChainLightningSpell() {
        super("Chain Lightning", 50, 0, SpellType.OFFENSIVE);
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

            if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHit) {
                centerPos = blockHit.getBlockPos().above();
            } else {
                centerPos = player.blockPosition().relative(player.getDirection(), 4);
            }

            createInitialLightning(level, centerPos);
            createChainLightning(level, centerPos, player, maxChains);
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

    private void createInitialLightning(Level level, BlockPos centerPos) {
        LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
        if (lightning != null) {
            lightning.moveTo(centerPos.getX() + 0.5, centerPos.getY(), centerPos.getZ() + 0.5);
            level.addFreshEntity(lightning);
        }
    }

    private void createChainLightning(Level level, BlockPos origin, Player caster, int chains) {
        int baseDamage = SpellUtil.getPlayerMagicDamage(caster);
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(origin).inflate(8.0),
                entity -> entity != caster && entity.isAlive()
        );

        if (targets.isEmpty()) return;

        // FIXED: Corrected lambda comparison for distance sorting
        targets.sort((a, b) -> Double.compare(a.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()),
                b.distanceToSqr(origin.getX(), origin.getY(), origin.getZ())));

        float currentDamage = baseDamage;
        BlockPos lastPos = origin;

        for (int i = 0; i < Math.min(chains, targets.size()); i++) {
            LivingEntity target = targets.get(i);

            // Deal damage using capability stats or your custom magic damage helper
            target.hurt(level.damageSources().magic(), currentDamage);

            if (level instanceof ServerLevel serverLevel) {
                createChainEffect(serverLevel, lastPos, target.blockPosition());
            }

            level.playSound(null, target.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.HOSTILE, 0.4f, 1.2f + (float)Math.random() * 0.3f);

            lastPos = target.blockPosition();
            currentDamage *= damageMultiplier; // Properly scales downward
        }
    }

    private void createChainEffect(ServerLevel level, BlockPos start, BlockPos end) {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double dz = end.getZ() - start.getZ();
        double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);

        int particles = Math.max(5, (int)(distance * 3));
        for (int i = 0; i < particles; i++) {
            double progress = (double)i / particles;
            double x = start.getX() + 0.5 + dx * progress;
            double y = start.getY() + 0.5 + dy * progress;
            double z = start.getZ() + 0.5 + dz * progress;

            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 1, 0.05, 0.05, 0.05, 0.01);
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
    protected void consumeMana(Player player) {
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