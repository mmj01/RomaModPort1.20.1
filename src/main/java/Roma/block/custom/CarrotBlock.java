package Roma.block.custom;

import Roma.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CarrotBlock extends ModCropBlock {
    public CarrotBlock(Properties properties) {
        super(properties.copy(Blocks.CARROTS).noCollission().instabreak().sound(SoundType.CROP));
    }



    @Override
    public int getAge(BlockState pState) {
        return super.getAge(pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected Item getBaseSeedId() {
        return Moditems.CARROT.get(); // Replace with your custom seed item
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(Moditems.CARROT.get());
    }

    @Override
    public int getMaxAge() {
        return 7; // default for crops like wheat
    }


}
