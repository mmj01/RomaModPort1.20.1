package Roma.block.custom;

import Roma.item.Moditems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ModCropBlock extends CropBlock {
    public ModCropBlock(Properties properties) {
        super(properties.copy(Blocks.WHEAT).noCollission().instabreak().sound(SoundType.CROP));
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
        return Moditems.WHEATSEEDS.get(); // Replace with your custom seed item
    }

    @Override
    public int getMaxAge() {
        return 7; // default for crops like wheat
    }


}
