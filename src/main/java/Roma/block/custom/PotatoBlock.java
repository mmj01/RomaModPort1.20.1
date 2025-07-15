package Roma.block.custom;

import Roma.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class PotatoBlock extends ModCropBlock {
    public PotatoBlock(Properties properties) {
        super(properties.copy(Blocks.POTATOES).noCollission().instabreak().sound(SoundType.CROP));
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
        return Moditems.POTATO.get(); // Replace with your custom seed item
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        return new ItemStack(Moditems.POTATO.get());
    }

    @Override
    public int getMaxAge() {
        return 7; // default for crops like wheat
    }


}
