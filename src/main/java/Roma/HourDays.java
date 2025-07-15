package Roma;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

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
    private static int tickCounter = 0;
    private static final int RUN_INTERVAL = 72000; // ~1 Minecraft day

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        tickCounter++;
        if (tickCounter >= RUN_INTERVAL) {
            tickCounter = 0;

            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                // First command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run kill @e[type=villager]"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 179 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // First command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 180 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 182 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 183 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 185 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 186 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 188 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // First command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 189 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 191 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 192 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 194 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 195 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run summon villager 72 116 196 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );




                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 179 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // First command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 180 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 183 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 184 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 186 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 187 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 189 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // First command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 190 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Second command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 192 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );

                // Third command
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 193 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 195 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 196 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack(),
                        "summon villager 73 116 197 {Invulnerable:1b,CustomNameVisible:0b,VillagerData:{profession:\"minecraft:fletcher\"}}"
                );
                server.getCommands().performPrefixedCommand(
                        server.createCommandSourceStack().withSuppressedOutput(),
                        "execute in rma:roma_dim run say Traders Reset"
                );
            }
        }
    }

}
