package Roma.entity.custom;

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
    private int cooldown = 0;

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
                && this.mob.distanceToSqr(this.target) < 256.0D; // 16 blocks squared (16 * 16 = 256)
    }


    @Override
    public void start() {
        this.cooldown = 0;
    }


    @Override
    public void stop() {
        this.target = null;
        this.cooldown = 0;
    }


    @Override
    public void tick() {
        if (this.target == null) return;

        if (this.mob.tickCount % 20 == 0) {
            this.mob.getLookControl().setLookAt(this.target, 360.0F, 360.0F);
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        if (this.cooldown > 0) {
            this.cooldown--;
        }


        if (this.cooldown <= 0) {
            teleport(this.target);
            this.cooldown = 60;
        }
    }

    private void teleport(Player target) {
        Vec3 playerLook = target.getViewVector(1.0F);
        Vec3 horizontalLook = new Vec3(playerLook.x, 0.0D, playerLook.z).normalize();
        Vec3 targetPos = target.position().subtract(horizontalLook.scale(2.0D));

        this.mob.teleportTo(targetPos.x, target.getY(), targetPos.z);
        this.mob.getLookControl().setLookAt(target, 180.0F, 180.0F);
        this.mob.playSound(SoundEvents.AMETHYST_CLUSTER_PLACE, 2.0F, 0.2F);
    }
}