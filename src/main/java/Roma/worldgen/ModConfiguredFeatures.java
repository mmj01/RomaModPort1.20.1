package Roma.worldgen;

import Roma.block.ModBlocks;
import Roma.roma;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(roma.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static final ResourceKey<ConfiguredFeature<?, ?>> ALUMINUM = registerKey("aluminum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRON = registerKey("iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COBALT = registerKey("cobalt_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHROMIUM = registerKey("chromium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PLATINUM = registerKey("platinum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD = registerKey("gold_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIN = registerKey("tin_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER = registerKey("silver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZINC = registerKey("zinc_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NICKEL = registerKey("nickel_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COAL = registerKey("coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> COPPER = registerKey("copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS = registerKey("cypress");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> copperTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COPPERORE.get().defaultBlockState())
        );
        register(context, COPPER, Feature.ORE, new OreConfiguration(copperTargets, 64));

        List<OreConfiguration.TargetBlockState> aluminumTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ALUMINUMORE.get().defaultBlockState())
        );
        register(context, ALUMINUM, Feature.ORE, new OreConfiguration(aluminumTargets, 64));

        List<OreConfiguration.TargetBlockState> coalTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COALORE.get().defaultBlockState())
        );
        register(context, COAL, Feature.ORE, new OreConfiguration(coalTargets, 80));

        List<OreConfiguration.TargetBlockState> ironTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.IRONORE.get().defaultBlockState())
        );
        register(context, IRON, Feature.ORE, new OreConfiguration(ironTargets, 128));

        List<OreConfiguration.TargetBlockState> cobaltTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.COBALTORE.get().defaultBlockState())
        );
        register(context, COBALT, Feature.ORE, new OreConfiguration(cobaltTargets, 25));

        List<OreConfiguration.TargetBlockState> chromiumTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.CHROMIUMORE.get().defaultBlockState())
        );
        register(context, CHROMIUM, Feature.ORE, new OreConfiguration(chromiumTargets, 3));

        List<OreConfiguration.TargetBlockState> platinumTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.PLATINUMORE.get().defaultBlockState())
        );
        register(context, PLATINUM, Feature.ORE, new OreConfiguration(platinumTargets, 6));

        List<OreConfiguration.TargetBlockState> goldTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.GOLDORE.get().defaultBlockState())
        );
        register(context, GOLD, Feature.ORE, new OreConfiguration(goldTargets, 24));

        List<OreConfiguration.TargetBlockState> tinTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.TINORE.get().defaultBlockState())
        );
        register(context, TIN, Feature.ORE, new OreConfiguration(tinTargets, 80));

        List<OreConfiguration.TargetBlockState> silverTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.SILVERORE.get().defaultBlockState())
        );
        register(context, SILVER, Feature.ORE, new OreConfiguration(silverTargets, 32));

        List<OreConfiguration.TargetBlockState> zincTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ZINCORE.get().defaultBlockState())
        );
        register(context, ZINC, Feature.ORE, new OreConfiguration(zincTargets, 80));

        List<OreConfiguration.TargetBlockState> nickelTargets = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.NICKELORE.get().defaultBlockState())
        );
        register(context, NICKEL, Feature.ORE, new OreConfiguration(nickelTargets, 28));

        register(context, CYPRESS, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CYPRESSLOG.get()),
                new GiantTrunkPlacer(24, 16, 8),

                BlockStateProvider.simple(ModBlocks.CYPRESSLEAVES.get()),
                new MegaPineFoliagePlacer(
                        ConstantInt.of(0),
                        ConstantInt.of(7),
                        ConstantInt.of(24)
                ),

                new TwoLayersFeatureSize(1, 2, 10)).build());
    }

}
