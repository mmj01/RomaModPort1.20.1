package Roma.entity;

import Roma.entity.custom.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.function.Predicate;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = "rma")
public class EntitySpawnHandler {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;

        for (ServerLevel level : server.getAllLevels()) {
            // Check all 4 assassin tiers sequentially using clean logical expressions (&&, ||)
            trySpawnAssassin(level, Modentities.PERSIANASSASSIN,
                    pos -> pos.getZ() < 0 || pos.getY() < 0 || pos.getX() < 0);

            trySpawnAssassin(level, Modentities.PERSIANASSASSINLVLTWO,
                    pos -> pos.getZ() < 0 && pos.getY() < 0);

            trySpawnAssassin(level, Modentities.PERSIANASSASSINLVLTHREE,
                    pos -> pos.getZ() < 0 && pos.getX() < 0);

            trySpawnAssassin(level, Modentities.PERSIANASSASSINLVLFOUR,
                    pos -> pos.getZ() < 0 && pos.getX() < 0 && pos.getY() < 0);
        }
    }

    /**
     * Single universal spawn engine to replace the 4 copy-pasted loops.
     */
    private static void trySpawnAssassin(ServerLevel level, Supplier<? extends EntityType<? extends Mob>> entitySupplier, Predicate<BlockPos> condition) {
        for (ServerPlayer player : level.players()) {
            if (condition.test(player.blockPosition())) {
                if (level.random.nextFloat() < 0.0004F) { // ~5% chance per tick cycle / ~2 min average
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos) && level.isEmptyBlock(targetPos.above())) {

                        Mob entity = entitySupplier.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D, level.random.nextFloat() * 360.0F, 0.0F);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }
}