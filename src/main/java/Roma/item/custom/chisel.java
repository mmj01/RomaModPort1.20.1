package Roma.item.custom;

import Roma.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class chisel extends Item {
    private static final Map<Block,Block> CHISELMAP=
            Map.of(
                    ModBlocks.SUPERMARBLE.get(), ModBlocks.SUPERMARBLETYONE.get(),
                    ModBlocks.SUPERMARBLETYONE.get(), ModBlocks.SUPERMARBLETYTWO.get(),
                    ModBlocks.SUPERMARBLETYTWO.get(), ModBlocks.SUPERMARBLETYTHREE.get(),
                    ModBlocks.SUPERMARBLETYTHREE.get(), ModBlocks.SUPERMARBLETYFOUR.get(),
                    ModBlocks.SUPERMARBLETYFOUR.get(), ModBlocks.SUPERMARBLETYFIVE.get(),
                    ModBlocks.SUPERMARBLETYFIVE.get(), ModBlocks.SUPERMARBLE.get()
            );


    public chisel(Properties pProperties) {


        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level=pContext.getLevel();
        Block clickedBlock =level.getBlockState(pContext.getClickedPos()).getBlock();

        if(CHISELMAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()){
                level.setBlockAndUpdate(pContext.getClickedPos(), CHISELMAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(1, (ServerPlayer) pContext.getPlayer(), (player) -> {
                    player.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                });


                level.playSound(null,pContext.getClickedPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS);

            }

        }

        return InteractionResult.SUCCESS;



    }
}
