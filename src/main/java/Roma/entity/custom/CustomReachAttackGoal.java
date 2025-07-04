package Roma.entity.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.Mob;

public class CustomReachAttackGoal extends MeleeAttackGoal {
    private final double reachDistance;

    public CustomReachAttackGoal(Mob mob, double speedModifier, boolean pauseWhenMobIdle, float reachDistance) {
        super((PathfinderMob) mob, speedModifier, pauseWhenMobIdle);
        this.reachDistance = reachDistance;
    }

    protected double getReachDistance(LivingEntity attackTarget) {
        // Override this to provide your custom fixed reach
        return reachDistance * reachDistance;
    }
}
