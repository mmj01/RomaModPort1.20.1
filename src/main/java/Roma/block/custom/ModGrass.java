package Roma.block.custom;

import Roma.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.core.particles.DustParticleOptions;
import org.joml.Vector3f;

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
                }

                return InteractionResult.sidedSuccess(pLevel.isClientSide);
            }
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        if (entity instanceof Player) {
            double radius = 0.6D;


            // 1. Draw the outer boundary circle
            for (int i = 0; i < 60; i++) {
                double angle = i * Math.PI * 2 / 60;
                double x = pos.getX() + 0.5D + Math.cos(angle) * radius;
                double y = pos.getY() + 1.0D;
                double z = pos.getZ() + 0.5D + Math.sin(angle) * radius;
                level.sendParticles(ParticleTypes.FALLING_LAVA, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
        return true;
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(level, pos.above()) == PlantType.PLAINS;
    }
}