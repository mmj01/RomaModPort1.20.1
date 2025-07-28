package Roma.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import java.util.Random;

public class FlippableCoin extends Item {

    public FlippableCoin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW; // or UseAnim.NONE if you don't want animation
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) { // Only run on server side
            Random random = new Random();
            int result = random.nextInt(2); // Generates 0 or 1

            ServerLevel serverLevel = (ServerLevel) pLevel;
            double x = pPlayer.getX();
            double y = pPlayer.getY() + 1.5; // Above player's head
            double z = pPlayer.getZ();

            if (result == 0) {
                // Heads - add a copy of the coin and show green particles
                pPlayer.sendSystemMessage(Component.literal("You won!"));
                ItemStack newCoin = new ItemStack(this, 1); // Create a copy of this coin
                pPlayer.getInventory().add(newCoin); // Add it to player's inventory

                // Spawn green particles (happy_villager gives green particles)
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                        x, y, z, 10, 0.5, 0.5, 0.5, 0.1);

            } else {
                // Tails - show red particles and consume the coin
                pPlayer.sendSystemMessage(Component.literal("You lost!"));

                // Spawn red particles (angry_villager gives red particles)
                serverLevel.sendParticles(ParticleTypes.ANGRY_VILLAGER,
                        x, y, z, 10, 0.5, 0.5, 0.5, 0.1);

                // Remove one coin from the stack
                itemStack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }
}