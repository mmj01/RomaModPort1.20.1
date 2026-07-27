package Roma.item.custom;

import Roma.magic.ManaCapability;
import Roma.magic.ManaSyncPacket;
import Roma.magic.config.NetworkHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaResetCookie extends Item {

    public ManaResetCookie(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
            pPlayer.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                mana.setMaxMana(100);
                mana.setMana(100);
                mana.setManaRegenRate(1);
                mana.setManaRegenTime(40);
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(mana.getMana(), mana.getMaxMana()), serverPlayer);
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(mana.getManaRegenTime(), mana.getManaRegenRate()), serverPlayer);
                }
            });

            // Feedback and item consumption
            pPlayer.displayClientMessage(Component.literal("Mana Values Reset!"), true);
            pPlayer.playSound(SoundEvents.ANVIL_BREAK, 0.5f, 1.0f);

            if (!pPlayer.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide());
    }
}

    

