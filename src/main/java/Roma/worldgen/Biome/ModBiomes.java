package Roma.worldgen.Biome;

import Roma.roma;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;

import net.minecraft.world.level.biome.*;



public class ModBiomes {
    public static final ResourceKey<Biome> ROMA_BIOME = ResourceKey.create(Registries.BIOME,
             ResourceLocation.fromNamespaceAndPath(roma.MOD_ID,"roma_biome"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(ROMA_BIOME, romaBiome(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);

    }

    public static Biome romaBiome(BootstapContext<Biome> context) {




        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();




        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);







        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.9f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .backgroundMusic(Musics.createGameMusic(SoundEvents.MUSIC_GAME)).build())
                .build();
    }
}