package Roma.entity.custom.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class PlayerOnlyFireball extends LargeFireball {

    private static final float EXPLOSION_RADIUS = 8.0F; // Explosion radius
    private static final float DIRECT_HIT_DAMAGE = 10.0F; // Direct hit damage
    private static final float KNOCKBACK_STRENGTH = 3.0F;


    public PlayerOnlyFireball(Level level, LivingEntity owner, double x, double y, double z) {
        super(level, owner, x, y, z, (int) EXPLOSION_RADIUS); // Explosion power
    }
    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source.is(DamageTypeTags.IS_EXPLOSION) || super.isInvulnerableTo(source);
    }

    @Override
    public void push(Entity entity) {
        // Cancel all pushing logic
    }
    private double speedMultiplier = 1.0; // Starts at normal speed
    private final double accelerationRate = 0.03; // Increase per tick

    @Override
    public void tick() {
        super.tick();
        this.setSecondsOnFire(100); // Makes the fireball appear burning for 5 seconds


        if (!this.level().isClientSide) {
            // Accelerate speed multiplier
            speedMultiplier += accelerationRate;

            // Get current motion and normalize it
            Vec3 motion = this.getDeltaMovement();
            if (motion.lengthSqr() > 0.0001) {
                Vec3 direction = motion.normalize();

                // Apply increased speed
                this.setDeltaMovement(direction.scale(speedMultiplier));
            }

            // Optional: Steering toward the player
            if (this.getOwner() instanceof LivingEntity owner) {
                Player target = this.level().getNearestPlayer(this, 64);
                if (target != null && !target.isDeadOrDying()) {
                    Vec3 toTarget = new Vec3(
                            target.getX() - this.getX(),
                            target.getY(0.5) - this.getY(),
                            target.getZ() - this.getZ()
                    ).normalize();

                    // Gently steer toward target
                    double steerStrength = 0.5;
                    Vec3 newMotion = this.getDeltaMovement().scale(1.0 - steerStrength).add(toTarget.scale(steerStrength));
                    this.setDeltaMovement(newMotion.normalize().scale(speedMultiplier));
                }
            }
        }
    }





    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();

        if (target instanceof Player player) {
            // Deal fireball damage
            player.hurt(this.damageSources().fireball(this, this.getOwner()), 6.0F);
            // Set player on fire
            player.setSecondsOnFire(5);
        }

        if (target instanceof Player player) {
            DamageSource source = this.damageSources().fireball(this, this.getOwner());
            player.hurt(source, DIRECT_HIT_DAMAGE);

            // Apply manual knockback
            double dx = player.getX() - this.getX();
            double dz = player.getZ() - this.getZ();
            double distance = Math.max(0.001, Math.sqrt(dx * dx + dz * dz));
            double knockbackX = dx / distance * KNOCKBACK_STRENGTH;
            double knockbackZ = dz / distance * KNOCKBACK_STRENGTH;
            player.push(knockbackX, 0.5, knockbackZ);
        }

        this.explode();
    }

    @Override
    protected void onHit(HitResult hit) {
        if (!this.level().isClientSide) {
            BlockPos pos = BlockPos.containing(this.getX(), this.getY(), this.getZ());

            // Try to set fire to the block hit
            if (this.level().getBlockState(pos).isAir() && this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                this.level().setBlockAndUpdate(pos, Blocks.FIRE.defaultBlockState());
            }

            this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 1.0F);
            ((ServerLevel) this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 0, 0, 0, 0.1);
        }
        this.explode();
    }
    private boolean isDestructible(BlockPos pos) {
        BlockState state = this.level().getBlockState(pos);
        Block block = state.getBlock();

        float resistance = block.getExplosionResistance();
        return resistance <= 7.0F; // only destroy weak blocks like glass, wool, leaves, etc.
    }

    private void explode() {
        if (!this.level().isClientSide) {
            BlockPos origin = this.blockPosition();

            int radius = (int) Math.ceil(EXPLOSION_RADIUS);

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        BlockPos pos = origin.offset(dx, dy, dz);
                        double distanceSq = origin.distSqr(pos);

                        if (distanceSq <= EXPLOSION_RADIUS * EXPLOSION_RADIUS) {
                            // Only affect certain blocks
                            if (isDestructible(pos)) {
                                this.level().destroyBlock(pos, false); // Drops block items
                            }
                        }
                    }
                }
            }

            // Play sound and particles
            this.level().playSound(null, this.blockPosition(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 1.0F);

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 8, 0.5, 0.5, 0.5, 0.1);
            }
        }

        this.discard();
    }


    @Override
    public boolean isPickable() {
        return false; // Prevents being picked by fishing rods or arrows
    }

    @Override
    public boolean isPushable() {
        return false; // Prevents entity pushing
    }

    @Override
    public boolean canBeCollidedWith() {
        return false; // Prevents physical collision
    }

    @Override
    protected boolean canHitEntity(Entity entity) {
        return entity instanceof Player;
    }
}
