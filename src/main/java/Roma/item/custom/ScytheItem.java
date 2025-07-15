package Roma.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
public class ScytheItem extends Item {
    public ScytheItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
public InteractionResult useOn(UseOnContext context) {
    Level level = context.getLevel();
    BlockPos pos = context.getClickedPos();
    Player player = context.getPlayer();

    if (level.isClientSide || !(level instanceof ServerLevel serverLevel)) return InteractionResult.SUCCESS;

    BlockState state = level.getBlockState(pos);
    Block block = state.getBlock();

    if (block instanceof CropBlock crop && crop.isMaxAge(state)) {
        // Get the replant item (seeds or root crop)
        Item replantItem = crop.getCloneItemStack(serverLevel, pos, state).getItem();

        // Check if player has the correct item
        if (player.getInventory().hasAnyMatching(stack -> stack.getItem()==replantItem)) {
            System.out.println("Trying to replant with: " + replantItem);
            // Drop loot
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, level.getBlockEntity(pos));
            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            // Consume 1 replant item
            removeItemFromInventory(player, replantItem);

            // Replant crop at age 0
            level.setBlock(pos, crop.getStateForAge(0), 2);

            return InteractionResult.SUCCESS;
        } else {
            // No seed/root item — just harvest
            List<ItemStack> drops = Block.getDrops(state, serverLevel, pos, level.getBlockEntity(pos));
            for (ItemStack drop : drops) {
                Block.popResource(level, pos, drop);
            }

            // Remove the crop
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

            return InteractionResult.SUCCESS;
        }
    }

    return InteractionResult.PASS;
}

private void removeItemFromInventory(Player player, Item itemToRemove){
    for (int i=0;i< player.getInventory().getContainerSize();i++){
        ItemStack stack = player.getInventory().getItem(i);
        if (!stack.isEmpty() && stack.getItem()==itemToRemove){
            stack.shrink(1);
            if (stack.isEmpty()){
                player.getInventory().setItem(i,ItemStack.EMPTY);
            }
            return;
        }

    }

}
}
