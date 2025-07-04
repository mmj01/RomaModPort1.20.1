package Roma.worldgen;

import Roma.roma;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomeModifiers {
    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(roma.MOD_ID, name));
    }

    public static final ResourceKey<BiomeModifier> ADD_COPPER_ORE = registerKey("add_copper_ore");
    public static final ResourceKey<BiomeModifier> ADD_ALUMINUM_ORE = registerKey("add_aluminum_ore");
    public static final ResourceKey<BiomeModifier> ADD_IRON_ORE = registerKey("add_iron_ore");
    public static final ResourceKey<BiomeModifier> ADD_COBALT_ORE = registerKey("add_cobalt_ore");
    public static final ResourceKey<BiomeModifier> ADD_CHROMIUM_ORE = registerKey("add_chromium_ore");
    public static final ResourceKey<BiomeModifier> ADD_PLATINUM_ORE = registerKey("add_platinum_ore");
    public static final ResourceKey<BiomeModifier> ADD_GOLD_ORE = registerKey("add_gold_ore");
    public static final ResourceKey<BiomeModifier> ADD_TIN_ORE = registerKey("add_tin_ore");
    public static final ResourceKey<BiomeModifier> ADD_SILVER_ORE = registerKey("add_silver_ore");
    public static final ResourceKey<BiomeModifier> ADD_ZINC_ORE = registerKey("add_zinc_ore");
    public static final ResourceKey<BiomeModifier> ADD_COAL_ORE = registerKey("add_coal_ore");
    public static final ResourceKey<BiomeModifier> ADD_NICKEL_ORE = registerKey("add_nickel_ore");
    public static final ResourceKey<BiomeModifier> ADD_CYPRESS = registerKey("add_cypress");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placed = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(ADD_CYPRESS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.PLAINS)),
                HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.CYPRESS)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_COPPER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(


                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.COPPER)), GenerationStep.Decoration.UNDERGROUND_ORES));


        context.register(ADD_ALUMINUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(


                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.ALUMINUM)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_IRON_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.COAL)), GenerationStep.Decoration.UNDERGROUND_ORES));
                context.register(ADD_COBALT_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.COBALT)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_CHROMIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.CHROMIUM)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_PLATINUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.PLATINUM)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_GOLD_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.GOLD)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_TIN_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.TIN)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_SILVER_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.SILVER)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_ZINC_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.ZINC)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_NICKEL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.NICKEL)), GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(ADD_COAL_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_OVERWORLD), HolderSet.direct(placed.getOrThrow(ModPlacedFeatures.COAL)), GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}