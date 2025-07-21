package Roma.worldgen;

import Roma.block.ModBlocks;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> ALUMINUM_ORE = createKey("aluminum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHROMIUM_ORE = createKey("chromium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAL_ORE = createKey("coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COBALT_ORE = createKey("cobalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER_ORE = createKey("copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_ORE = createKey("gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRON_ORE = createKey("iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NICKEL_ORE = createKey("nickel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PLATINUM_ORE = createKey("platinum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE = createKey("silver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN_ORE = createKey("tin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZINC_ORE = createKey("zinc_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STONE_PINE = createKey("stone_pine");

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath("rma", name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        register(context, STONE_PINE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.DARK_OAK_LOG), // Trunk - dark oak for Mediterranean look
                new StraightTrunkPlacer(8, 4, 2), // Base height 8, random 0-4 extra, no branches
                BlockStateProvider.simple(Blocks.DARK_OAK_LEAVES), // Foliage - dark oak leaves
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 4), // Radius 3, no extra, height 4
                new TwoLayersFeatureSize(1, 0, 2) // Min clipped height, limit, lower size
        ).ignoreVines().build());


        // Register each configured feature - replace ModBlocks.YOUR_ORE with your actual ore blocks
        register(context, ALUMINUM_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ALUMINUMORE.get().defaultBlockState())
        ), 9)); // vein size

        register(context, CHROMIUM_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CHROMIUMORE.get().defaultBlockState())
        ), 7));

        register(context, COAL_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COALORE.get().defaultBlockState())
        ), 17));

        register(context, COBALT_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COBALTORE.get().defaultBlockState())
        ), 6));

        register(context, COPPER_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COPPERORE.get().defaultBlockState())
        ), 10));

        register(context, GOLD_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.GOLDORE.get().defaultBlockState())
        ), 9));

        register(context, IRON_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.IRONORE.get().defaultBlockState())
        ), 9));

        register(context, NICKEL_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.NICKELORE.get().defaultBlockState())
        ), 8));

        register(context, PLATINUM_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.PLATINUMORE.get().defaultBlockState())
        ), 5));

        register(context, SILVER_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SILVERORE.get().defaultBlockState())
        ), 9));

        register(context, TIN_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TINORE.get().defaultBlockState())

        ), 11));

        register(context, ZINC_ORE, Feature.ORE, new OreConfiguration(List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ZINCORE.get().defaultBlockState())
        ), 10));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}