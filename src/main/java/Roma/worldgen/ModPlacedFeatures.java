package Roma.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {

    // Ores
    public static final ResourceKey<PlacedFeature> ALUMINUM_ORE_PLACED = createKey("aluminum_ore_placed");
    public static final ResourceKey<PlacedFeature> CHROMIUM_ORE_PLACED = createKey("chromium_ore_placed");
    public static final ResourceKey<PlacedFeature> COAL_ORE_PLACED = createKey("coal_ore_placed");
    public static final ResourceKey<PlacedFeature> COBALT_ORE_PLACED = createKey("cobalt_ore_placed");
    public static final ResourceKey<PlacedFeature> COPPER_ORE_PLACED = createKey("copper_ore_placed");
    public static final ResourceKey<PlacedFeature> GOLD_ORE_PLACED = createKey("gold_ore_placed");
    public static final ResourceKey<PlacedFeature> IRON_ORE_PLACED = createKey("iron_ore_placed");
    public static final ResourceKey<PlacedFeature> NICKEL_ORE_PLACED = createKey("nickel_ore_placed");
    public static final ResourceKey<PlacedFeature> PLATINUM_ORE_PLACED = createKey("platinum_ore_placed");
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED = createKey("silver_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED = createKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> ZINC_ORE_PLACED = createKey("zinc_ore_placed");

    // Trees
    public static final ResourceKey<PlacedFeature> STONE_PINE_PLACED = createKey("stone_pine_placed");

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath("roma", name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // Ores
        register(context, ALUMINUM_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.ALUMINUM_ORE),
                commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, CHROMIUM_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.CHROMIUM_ORE),
                commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, COAL_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.COAL_ORE),
                commonOrePlacement(30, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(192))));

        register(context, COBALT_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.COBALT_ORE),
                commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, COPPER_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.COPPER_ORE),
                commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-16), VerticalAnchor.absolute(112))));

        register(context, GOLD_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.GOLD_ORE),
                commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-64), VerticalAnchor.aboveBottom(32))));

        register(context, IRON_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.IRON_ORE),
                commonOrePlacement(90, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));

        register(context, NICKEL_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.NICKEL_ORE),
                commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, PLATINUM_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PLATINUM_ORE),
                commonOrePlacement(3, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(30))));

        register(context, SILVER_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.SILVER_ORE),
                commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, TIN_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.TIN_ORE),
                commonOrePlacement(12, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        register(context, ZINC_ORE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.ZINC_ORE),
                commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));

        // Stone Pine Trees - Custom placement for Mediterranean feel
        register(context, STONE_PINE_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.STONE_PINE),
                List.of(
                        CountPlacement.of(3), // 3 attempts per chunk
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(0),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(
                                Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)) // Custom survival check
                ));
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return List.of(
                CountPlacement.of(count),
                InSquarePlacement.spread(),
                heightRange,
                BiomeFilter.biome()
        );
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key,
                                 net.minecraft.core.Holder<net.minecraft.world.level.levelgen.feature.ConfiguredFeature<?, ?>> feature,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(feature, modifiers));
    }
}