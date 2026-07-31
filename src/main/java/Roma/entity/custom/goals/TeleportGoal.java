package Roma.entity.custom.goals;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class TeleportGoal extends Goal {
    private final PathfinderMob mob;
    private final double speed;
    private Player target;
    private int cooldown = 60; // Defaults to 3 seconds (20 ticks * 3)

    public TeleportGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        this.target = this.mob.level().getNearestPlayer(this.mob, 16.0D);
        return this.target != null && this.target.isAlive() && this.mob.canAttack(this.target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null
                && this.target.isAlive()
                && !this.target.isSpectator()
                && this.mob.distanceToSqr(this.target) < 256.0D; // 16 blocks squared
    }

    @Override
    public void start() {
        // Start with a full 3-second delay when the goal activates
        this.cooldown = 60;
    }

    @Override
    public void stop() {
        this.target = null;
        // Do not reset cooldown to 0 here to prevent instant-teleport spam on target re-acquisition
    }

    @Override
    public void tick() {
        if (this.target == null) return;

        // Smoothly look at the target while waiting to teleport
        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        if (this.cooldown > 0) {
            this.cooldown--;
        }

        if (this.cooldown <= 0) {
            teleport(this.target);
            this.cooldown = 60; // Reset timer for the next 3-second cycle
        }
    }

    private void teleport(Player target) {
        Vec3 playerLook = target.getViewVector(1.0F);
        Vec3 horizontalLook = new Vec3(playerLook.x, 0.0D, playerLook.z).normalize();
        Vec3 targetPos = target.position().subtract(horizontalLook.scale(2.0D));

        // Stop current pathfinding so the mob doesn't try to walk back to its old path
        this.mob.getNavigation().stop();

        this.mob.teleportTo(targetPos.x, target.getY(), targetPos.z);
        this.mob.getLookControl().setLookAt(target, 180.0F, 180.0F);
        this.mob.playSound(SoundEvents.AMETHYST_CLUSTER_PLACE, 2.0F, 0.2F);
    }
}