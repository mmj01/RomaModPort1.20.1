package Roma.worldgen.Biome.surface;


import Roma.block.ModBlocks;
import Roma.worldgen.Biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(ModBlocks.DIRT.get());
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(ModBlocks.GRASS.get());
    private static final SurfaceRules.RuleSource STONE = makeStateRule(ModBlocks.ROCK.get());



    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.ROMA_BIOME),
                                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, STONE)),
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE)),


                // Default to a grass and dirt surface
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, STONE)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}