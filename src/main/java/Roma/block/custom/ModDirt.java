package Roma.block.custom;

import Roma.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class ModDirt extends GrassBlock {
    public ModDirt(Properties properties) {
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
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable.getPlantType(level, pos.above()) == PlantType.PLAINS;
    }
}
