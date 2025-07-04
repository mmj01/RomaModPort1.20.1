package Roma.worldgen;

import Roma.block.ModBlocks;
import Roma.roma;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;

import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {


    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    public static final ResourceKey<PlacedFeature> COPPER = registerKey("copper_ore_placed");
    public static final ResourceKey<PlacedFeature> ALUMINUM = registerKey("aluminum_ore_placed");
    public static final ResourceKey<PlacedFeature> IRON = registerKey("iron_ore_placed");
    public static final ResourceKey<PlacedFeature> COBALT = registerKey("cobalt_ore_placed");
    public static final ResourceKey<PlacedFeature> CHROMIUM = registerKey("chromium_ore_placed");
    public static final ResourceKey<PlacedFeature> PLATINUM = registerKey("platinum_ore_placed");
    public static final ResourceKey<PlacedFeature> GOLD = registerKey("gold_ore_placed");
    public static final ResourceKey<PlacedFeature> TIN = registerKey("tin_ore_placed");
    public static final ResourceKey<PlacedFeature> SILVER = registerKey("silver_ore_placed");
    public static final ResourceKey<PlacedFeature> ZINC = registerKey("zinc_ore_placed");
    public static final ResourceKey<PlacedFeature> NICKEL = registerKey("nickel_ore_placed");
    public static final ResourceKey<PlacedFeature> COAL = registerKey("coal_ore_placed");
    public static final ResourceKey<PlacedFeature> CYPRESS = registerKey("cypress_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        var configured = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, CYPRESS, configured.getOrThrow(ModConfiguredFeatures.CYPRESS),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(64, 1.0f, 16),
                        ModBlocks.CYPRESSSAPLING.get()));


        register(context, COAL, configured.getOrThrow(ModConfiguredFeatures.COAL),
                ModOrePlacement.commonOrePlacement(32, HeightRangePlacement.triangle(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, COPPER, configured.getOrThrow(ModConfiguredFeatures.COPPER),
                ModOrePlacement.commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, ALUMINUM, configured.getOrThrow(ModConfiguredFeatures.ALUMINUM),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, IRON, configured.getOrThrow(ModConfiguredFeatures.IRON),
                ModOrePlacement.commonOrePlacement(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, COBALT, configured.getOrThrow(ModConfiguredFeatures.COBALT),
                ModOrePlacement.commonOrePlacement(6, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, CHROMIUM, configured.getOrThrow(ModConfiguredFeatures.CHROMIUM),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, PLATINUM, configured.getOrThrow(ModConfiguredFeatures.PLATINUM),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, GOLD, configured.getOrThrow(ModConfiguredFeatures.GOLD),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, TIN, configured.getOrThrow(ModConfiguredFeatures.TIN),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, SILVER, configured.getOrThrow(ModConfiguredFeatures.SILVER),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, ZINC, configured.getOrThrow(ModConfiguredFeatures.ZINC),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
        register(context, NICKEL, configured.getOrThrow(ModConfiguredFeatures.NICKEL),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-192), VerticalAnchor.absolute(0))));
    }
    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(roma.MOD_ID, name));
    }
}