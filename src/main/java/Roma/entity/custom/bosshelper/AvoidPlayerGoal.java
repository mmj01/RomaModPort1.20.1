package Roma.entity.custom.bosshelper;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AvoidPlayerGoal extends Goal {
    private final Mob mob;
    private final double speed;
    private final double safeDistance; // distance to keep from player

    public AvoidPlayerGoal(Mob mob, double speed, double safeDistance) {
        this.mob = mob;
        this.speed = speed;
        this.safeDistance = safeDistance;
    }

    @Override
    public boolean canUse() {
        List<Player> players = this.mob.level().getEntitiesOfClass(Player.class, mob.getBoundingBox().inflate(safeDistance));
        return !players.isEmpty();
    }

    @Override
    public void tick() {
        List<Player> players = this.mob.level().getEntitiesOfClass(Player.class, mob.getBoundingBox().inflate(safeDistance));
        if (players.isEmpty()) {
            return;
        }
        Player nearest = players.get(0);

        double distanceSq = mob.distanceToSqr(nearest);
        if (distanceSq < safeDistance * safeDistance) {
            Vec3 away = mob.position().subtract(nearest.position()).normalize().scale(speed);
            mob.getNavigation().moveTo(mob.getX() + away.x, mob.getY(), mob.getZ() + away.z, speed);
        } else {
            // Stop moving if at safe distance
            mob.getNavigation().stop();
        }
    }
}
