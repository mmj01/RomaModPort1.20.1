package Roma.entity.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class ChargeatplayerGoal extends Goal {
    private  PathfinderMob mob;
    private double speed;
    private Player target;

    public ChargeatplayerGoal(PathfinderMob mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        this.target = mob.level().getNearestPlayer(mob, 16);
        return target != null && target.isAlive();
    }
    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive() && !mob.getNavigation().isDone();
    }

    @Override
    public void start() {
        if (target != null) {
            mob.getNavigation().moveTo(target, speed);
        }
    }
    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target != null) {
            mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (mob.distanceToSqr(target) > 4.0D) {
                mob.getNavigation().moveTo(target, speed);
            }
        }
    }


} // end ChargeatplayerGoal

