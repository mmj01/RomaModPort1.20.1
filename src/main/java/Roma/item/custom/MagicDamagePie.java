package Roma.item.custom;

import Roma.magic.MagicDamageSyncPacket;
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

public class MagicDamagePie extends Item {

    private static final int INCREASE = 10;




    public MagicDamagePie(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
            pPlayer.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                // 1. Calculate the new values
                int newMax = mana.getMagicDamage() + INCREASE;


                // 2. Update the server-side capability
                mana.setMagicDamage(newMax);


                // 3. Send network packet to update client-side capability and HUD
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(new MagicDamageSyncPacket(mana.getMagicDamage()), serverPlayer);
                }
            });

            // Feedback and item consumption
            pPlayer.displayClientMessage(Component.literal("+10 Magic Damage!"), true);
            pPlayer.playSound(SoundEvents.WARDEN_HEARTBEAT, 0.5f, 1.0f);


            if (!pPlayer.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide());
    }

}

    

