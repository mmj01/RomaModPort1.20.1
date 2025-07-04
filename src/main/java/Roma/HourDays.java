package Roma;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "rma")
public class HourDays {
    private static final ResourceKey<Level> CUSTOM_DIM = ResourceKey.create(
            Registries.DIMENSION,
             ResourceLocation.fromNamespaceAndPath("rma", "roma_dim")
    );

    private static final int TIME_SCALE = 3;

    // Track tick count and time per dimension
    private static final Map<ServerLevel, Integer> tickCounters = new HashMap<>();
    private static final Map<ServerLevel, Long> trackedTime = new HashMap<>();

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;

        if (level.dimension().equals(CUSTOM_DIM)) {
            level.getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(false, level.getServer());

            long current = level.getDayTime();
            trackedTime.put(level, current);
            tickCounters.put(level, 0);


        }
    }

    @SubscribeEvent
    public static void onWorldTick(LevelTickEvent event) {
        if (event.level.isClientSide()) return;

        ServerLevel level = (ServerLevel) event.level;
        if (!level.dimension().equals(CUSTOM_DIM)) return;

        int ticks = tickCounters.getOrDefault(level, 0) + 1;
        if (ticks >= TIME_SCALE) {
            ticks = 0;

            long currentTime = trackedTime.getOrDefault(level, level.getDayTime()) + 1;
            trackedTime.put(level, currentTime);
            level.setDayTime(currentTime);


        }

        tickCounters.put(level, ticks);
    }
}
