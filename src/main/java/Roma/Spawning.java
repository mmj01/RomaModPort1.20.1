package Roma;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(modid = "rma")

public class Spawning {


    private static final String FIRST_JOIN_TAG = "rma_FirstJoin";

    private static final Set<ResourceKey<Level>> DIMENSIONS_TO_KILL = Set.of(
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("minecraft", "overworld")),
            ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("minecraft", "nether")));
    private static final ResourceKey<Level> CUSTOM_DIM = ResourceKey.create(
            Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath("rma", "roma_dim")
    );



    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        // Only do this on the server
        if (event.getEntity().level().isClientSide()) return;

        ServerPlayer player = (ServerPlayer) event.getEntity();
        ServerLevel customLevel = player.server.getLevel(CUSTOM_DIM);

        // Do not override bed/anchor respawns unless you want to


        if (customLevel != null) {
            BlockPos spawn = new BlockPos(15, 270, 16); // Use your custom spawn point
            player.teleportTo(customLevel, spawn.getX(), spawn.getY(), spawn.getZ(), player.getYRot(), player.getXRot());
        }
    }

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;
        if (serverLevel.dimension().location().equals(CUSTOM_DIM)){
            BlockPos customSpawn = new BlockPos(15, 270, 16); // Set your spawn location here
            serverLevel.setDefaultSpawnPos(customSpawn, 0.0f); // 0.0f is spawn angle
        }

        if (!serverLevel.dimension().location().equals(CUSTOM_DIM)) {



            if (!(event.getLevel() instanceof ServerLevel level)) return;

            if (DIMENSIONS_TO_KILL.contains(level.dimension())) {
                for (ServerPlayer player : level.players()) {
                    player.kill(); // instantly kill all players in this dimension
                }
            }
        }
        }
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        CompoundTag persistentData = player.getPersistentData();
        CompoundTag data = persistentData.getCompound(Player.PERSISTED_NBT_TAG);

        if (!data.getBoolean(FIRST_JOIN_TAG)) {
            // Player has never joined before — run your commands
            MinecraftServer server = player.server;
            String name = player.getName().getString();

            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:conqueror");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:vagrant");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:mythic");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:legendary");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:hero");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:fabled");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:godlike");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:god_of_healing");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:god_of_war");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:god_of_speed");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:legend_of_axes");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:legend_of_swords");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:master_of_swords");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:master_of_axes");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:legend_of_swords");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:legend_of_axes");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:soldior");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:the_one_above_all");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:the_honored_one");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:mining");
            runServerCommand(server, "puffish_skills category lock "+ name+" puffish_skills:combat");





            // Set the flag so it only runs once
            data.putBoolean(FIRST_JOIN_TAG, true);
            persistentData.put(Player.PERSISTED_NBT_TAG, data);
        }


        // Check if player is NOT in the custom dimension
        if (!player.level().dimension().equals(CUSTOM_DIM)) {
            player.kill(); // kill the player immediately
        }
    }

    private static void runServerCommand(MinecraftServer server, String command) {
        server.getCommands().performPrefixedCommand(
                server.createCommandSourceStack(),
                command
        );
    }
}

