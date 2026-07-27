package Roma.block.custom;

import Roma.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ModGrass extends GrassBlock {
    public ModGrass(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemInHand = pPlayer.getItemInHand(pHand);

        if (itemInHand.getItem() instanceof HoeItem) {
            BlockPos above = pPos.above();
            if (pLevel.getBlockState(above).isAir()) {
                pLevel.setBlock(pPos, ModBlocks.FARMLAND.get().defaultBlockState(), 3);
                pPlayer.playSound(SoundEvents.HOE_TILL);

                if (!pLevel.isClientSide) {
                    itemInHand.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pHand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND));
                    // ✅ correct for 1.20+
                }

                return InteractionResult.sidedSuccess(pLevel.isClientSide);

            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit); // fallback to default
    }


    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        super.animateTick(pState, pLevel, pPos, pRandom);


        if (pRandom.nextInt(5) == 0) {
            double x = pPos.getX() + 0.5D;
            double y = pPos.getY() + 1.0D;
            double z = pPos.getZ() + 0.5D;

            pLevel.addParticle(ParticleTypes.COMPOSTER, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        // Send particles from the server to all tracking clients in the area
        level.sendParticles(
                ParticleTypes.COMPOSTER,
                pos.getX() + 0.5D,
                pos.getY() + 1.0D,
                pos.getZ() + 0.5D,
                numberOfParticles, // Amount of particles based on fall distance
                0.2D, 0.1D, 0.2D,  // X, Y, Z spread offset
                0.05D              // Particle speed
        );

        // Return TRUE to cancel vanilla block-break landing dust, or FALSE to spawn both
        return true;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(level, pos.above()) == PlantType.PLAINS;
    }
}
