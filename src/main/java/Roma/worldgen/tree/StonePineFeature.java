package Roma.worldgen.tree;

import Roma.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StonePineFeature extends Feature<NoneFeatureConfiguration> {

    public StonePineFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource random = context.random();

        // Check if we can place on ground
        if (!level.getBlockState(pos.below()).is(ModBlocks.GRASS.get()) &&
                !level.getBlockState(pos.below()).is(ModBlocks.DIRT.get())) {
            return false;
        }

        // Stone Pine characteristics: tall trunk (15-25 blocks), umbrella canopy at top
        int height = 15 + random.nextInt(10); // 15-25 blocks tall
        int canopyStart = (int)(height * 0.7f); // Canopy starts at 70% of height

        // Build trunk
        for (int i = 0; i < height; i++) {
            BlockPos trunkPos = pos.above(i);
            if (level.isStateAtPosition(trunkPos, state -> state.isAir())) {
                level.setBlock(trunkPos, Blocks.DARK_OAK_LOG.defaultBlockState(), 2);
            }
        }

        // Build umbrella canopy - characteristic of stone pines
        for (int y = canopyStart; y < height + 5; y++) {
            int radius = Math.min(6, (y - canopyStart + 3)); // Expanding radius
            if (y >= height - 2) radius = 7; // Top is widest

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    double distance = Math.sqrt(x*x + z*z);
                    if (distance <= radius && random.nextFloat() < 0.8f) { // 80% chance for leaves
                        BlockPos leafPos = pos.above(y).offset(x, 0, z);
                        if (level.isStateAtPosition(leafPos, state -> state.isAir())) {
                            level.setBlock(leafPos, Blocks.DARK_OAK_LEAVES.defaultBlockState(), 2);
                        }
                    }
                }
            }
        }

        return true;
    }
}