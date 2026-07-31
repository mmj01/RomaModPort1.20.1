package Roma.entity.custom.goals;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ComboAttackAndJumpGoal extends Goal {
    private final PathfinderMob mob;
    private final double speedModifier;
    private Player target;
    private int attackCount = 0;
    private int attackCooldown = 0;
    private boolean isJumping = false;
    private int jumpTicks = 0;
    private int goalCooldown = 0;

    public ComboAttackAndJumpGoal(PathfinderMob mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        // Lock out movement, looking, and jumping from other AI goals while this combo runs
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.goalCooldown > 0) {
            this.goalCooldown--;
            return false;
        }
        this.target = this.mob.level().getNearestPlayer(this.mob, 16.0D);
        return this.target != null && this.target.isAlive() && this.mob.canAttack(this.target);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.target == null || !this.target.isAlive() || this.target.isSpectator()) {
            return false;
        }
        // If the mob is mid-leap, keep the goal active until it physically lands on the ground
        if (this.isJumping) {
            // Give it a 10-tick grace period so onGround() doesn't abort the goal the instant it jumps
            return this.jumpTicks < 10 || !this.mob.onGround();
        }
        return true;
    }

    @Override
    public void start() {
        this.attackCount = 0;
        this.attackCooldown = 0;
        this.isJumping = false;
        this.jumpTicks = 0;
        this.mob.getNavigation().moveTo(this.target, this.speedModifier);
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.target = null;
        // Put the entire combo on a 3-second cooldown
        this.goalCooldown = 60;
    }

    @Override
    public void tick() {
        if (this.target == null) return;

        // Smoothly track the player with the mob's head/eyes
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        // Phase 3: Airborne Leap
        if (this.isJumping) {
            this.jumpTicks++;
            return;
        }

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        double distSqr = this.mob.distanceToSqr(this.target);
        double reachSqr = this.getAttackReachSqr(this.target);

        // Phase 1: Closing the Distance
        if (distSqr > reachSqr) {
            if (this.mob.getNavigation().isDone()) {
                this.mob.getNavigation().moveTo(this.target, this.speedModifier);
            }
        }
        // Phase 2: The 3-Hit Melee Combo
        else {
            this.mob.getNavigation().stop();

            if (this.attackCooldown <= 0) {
                this.mob.doHurtTarget(this.target);
                this.mob.swing(InteractionHand.MAIN_HAND);
                this.attackCount++;
                this.attackCooldown = 5; // 0.75-second delay between strikes

                // Once the 3rd hit lands, instantly launch over the player
                if (this.attackCount >= 3) {
                    jumpOverPlayer();
                }
            }
        }
    }

    private void jumpOverPlayer() {
        this.isJumping = true;
        this.jumpTicks = 0;

        // Get the horizontal trajectory pointing straight from the mob through the player
        Vec3 dir = this.target.position().subtract(this.mob.position()).normalize();

        // Launch forward (1.8D speed) and upward (0.8D height) to easily clear a 1.8-block tall player
        this.mob.setDeltaMovement(dir.x * 1.8D, 0.8D, dir.z * 1.8D);

        // Crucial: Tell the server to broadcast a movement packet so the client sees the jump cleanly
        this.mob.hasImpulse = true;
    }

    private double getAttackReachSqr(LivingEntity target) {
        return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + target.getBbWidth());
    }
}