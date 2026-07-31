package Roma.item.custom;

import Roma.entity.Modentities;
import Roma.entity.custom.boss.PersianShawman;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class shawmansummoner extends Item {
    public shawmansummoner(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            BlockPos playerPos = player.blockPosition();
            int chunkX = playerPos.getX() >> 4; // divide by 16
            int chunkZ = playerPos.getZ() >> 4;

            // Only spawn if both chunk X and Z are negative
            if (chunkX < 0 && chunkZ < 0) {
                PersianShawman boss = Modentities.PERSIANSHAWMAN.get().create(level);
                if (boss != null) {
                    BlockPos spawnPos = playerPos.above();
                    boss.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, level.random.nextFloat() * 360F, 0);
                    level.addFreshEntity(boss);
                    level.playSound(null, spawnPos, SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 1.0F);
                    stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
                }
            } else {
                // Optional: feedback if conditions not met
                player.displayClientMessage(Component.literal("The power of the §kShawman§r can only be summoned in cursed lands..."), true);

            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
