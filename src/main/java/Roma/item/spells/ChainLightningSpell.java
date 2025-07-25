

// Chain Lightning Spell - Jumps between enemies
package Roma.item.spells;

import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
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

import java.util.List;

public class ChainLightningSpell extends Spell {
    private int maxChains = 50;
    private float baseDamage = 15.0f;
    private float damageReduction = 1.2f; // Each chain does 120% of previous damage

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
            BlockHitResult result = (BlockHitResult) player.pick(20, 0, false);
            BlockPos centerPos = result.getBlockPos().above();

            // Initial lightning strike
            createInitialLightning(level, centerPos);

            // Create chain lightning effect
            createChainLightning(level, centerPos, player, maxChains);

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
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(origin).inflate(8.0),
                entity -> entity != caster && entity.isAlive()
        );

        if (targets.isEmpty()) return;

        // Sort by distance - chain to closest enemies first
        targets.sort((a, b) -> Double.compare(
                a.distanceToSqr(origin.getX(), origin.getY(), origin.getZ()),
                b.distanceToSqr(origin.getX(), origin.getY(), origin.getZ())
        ));

        float currentDamage = baseDamage;

        for (int i = 0; i < Math.min(chains, targets.size()); i++) {
            LivingEntity target = targets.get(i);

            // Deal damage
            target.hurt(level.damageSources().magic(), currentDamage);

            // Create lightning at target location
            LightningBolt chainLightning = EntityType.LIGHTNING_BOLT.create(level);
            if (chainLightning != null) {
                chainLightning.moveTo(target.getX(), target.getY(), target.getZ());
                level.addFreshEntity(chainLightning);
            }

            // Add chain visual effect
            if (level instanceof ServerLevel serverLevel) {
                createChainEffect(serverLevel, origin, target.blockPosition());
            }

            // Play sound
            level.playSound(null, target.blockPosition(), SoundEvents.LIGHTNING_BOLT_THUNDER,
                    SoundSource.HOSTILE, 0.5f, 1.0f + (float)Math.random() * 0.4f);

            currentDamage *= damageReduction; // Reduce damage for next chain
        }
    }

    private void createChainEffect(ServerLevel level, BlockPos start, BlockPos end) {
        // Create particle trail between lightning strikes
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        double dz = end.getZ() - start.getZ();
        double distance = Math.sqrt(dx*dx + dy*dy + dz*dz);

        int particles = (int)(distance * 2);
        for (int i = 0; i < particles; i++) {
            double progress = (double)i / particles;
            double x = start.getX() + dx * progress;
            double y = start.getY() + dy * progress + 1;
            double z = start.getZ() + dz * progress;

            level.sendParticles(ParticleTypes.ELECTRIC_SPARK, x, y, z, 1, 0.1, 0.1, 0.1, 0.05);
        }
    }

    // Standard methods (same as enhanced spell)...
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