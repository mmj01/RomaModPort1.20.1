package Roma.entity.custom;

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
    public void start() {
        if (target != null) {
            mob.getNavigation().moveTo(target, speed);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return target != null && target.isAlive() && mob.distanceToSqr(target) > 4;
    }
} // end ChargeatplayerGoal

