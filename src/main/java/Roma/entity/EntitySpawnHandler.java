package Roma.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = "rma")
public class EntitySpawnHandler {

    public static boolean lvloneassassin(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        // Example: anywhere within X/Z 100–300 range
        return  pos.getZ() < 0 || pos.getY() <0 || pos.getX() <0;
    }
    public static boolean lvltwoassassin(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        // Example: anywhere within X/Z 100–300 range
        return  pos.getZ() < 0 & pos.getY() <0;
    }
    public static boolean lvltwoassassinalso(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        // Example: anywhere within X/Z 100–300 range
        return  pos.getX() < 0 & pos.getY() <0;
    }
    public static boolean lvlthreeassassin(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        // Example: anywhere within X/Z 100–300 range
        return  pos.getZ() < 0 & pos.getX() <0;
    }
    public static boolean lvlfourassassin(ServerPlayer player) {
        BlockPos pos = player.blockPosition();
        // Example: anywhere within X/Z 100–300 range
        return  pos.getZ() < 0 & pos.getX() <0 & pos.getY() <0;
    }

    public static void trySpawnlvloneassassin(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (lvloneassassin(player)) {
                if (level.random.nextFloat() < 0.03f) { // 5% chance per tick cycle
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos)) {

                        var entity = Modentities.PERSIANASSASSIN.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }

    public static void trySpawnlvltwoassassin(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (lvltwoassassin(player)) {
                if (level.random.nextFloat() < 0.04f) { // 5% chance per tick cycle
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos)) {

                        var entity = Modentities.PERSIANASSASSINLVLTWO.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }
    public static void trySpawnlvltwoassassinalso(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (lvltwoassassinalso(player)) {
                if (level.random.nextFloat() < 0.04f) { // 5% chance per tick cycle
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos)) {

                        var entity = Modentities.PERSIANASSASSINLVLTWO.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }
    public static void trySpawnlvlthreeassassin(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (lvlthreeassassin(player)) {
                if (level.random.nextFloat() < 0.05f) { // 5% chance per tick cycle
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos)) {

                        var entity = Modentities.PERSIANASSASSINLVLTHREE.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }
    public static void trySpawnlvlfourassassin(ServerLevel level) {
        for (ServerPlayer player : level.players()) {
            if (lvlfourassassin(player)) {
                if (level.random.nextFloat() < 0.07f) { // 5% chance per tick cycle
                    BlockPos targetPos = player.blockPosition().offset(
                            level.random.nextInt(20) - 10, 0, level.random.nextInt(20) - 10
                    );
                    BlockPos blockBelow = targetPos.below();

                    if (level.getBlockState(blockBelow).isSolidRender(level, blockBelow) &&
                            level.isEmptyBlock(targetPos)) {

                        var entity = Modentities.PERSIANASSASSINLVLFOUR.get().create(level);
                        if (entity != null) {
                            entity.moveTo(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                            level.addFreshEntity(entity);
                        }
                    }
                }
            }
        }
    }




    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            for (ServerLevel level : server.getAllLevels()) {

                trySpawnlvloneassassin(level); // call our logic
                trySpawnlvltwoassassin(level);
                trySpawnlvltwoassassinalso(level);
                trySpawnlvlthreeassassin(level);
                trySpawnlvlfourassassin(level);
            }
        }
    }


}
