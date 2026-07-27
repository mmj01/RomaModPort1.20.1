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

public class ManaRegenCookie extends Item {

    private static final int INCREASE = 1;




    public ManaRegenCookie(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
            pPlayer.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {

                int newRegenRate = mana.getManaRegenRate() + INCREASE;
                int newRegenTime = mana .getManaRegenTime() - INCREASE;

                if (mana.getManaRegenTime() != 1) {
                    mana.setManaRegenTime(newRegenTime);
                    pPlayer.displayClientMessage(Component.literal("+20% Mana Regen Speed!"), true);
                }else{
                    mana.setManaRegenRate(newRegenRate);
                    pPlayer.displayClientMessage(Component.literal("+1 Mana Regen!"), true);
                }

                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(mana.getMana(), mana.getMaxMana()), serverPlayer);
                }
            });

            // Feedback and item consumption

            pPlayer.playSound(SoundEvents.WARDEN_HEARTBEAT, 0.5f, 1.0f);

            if (!pPlayer.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide());
    }
}

    

