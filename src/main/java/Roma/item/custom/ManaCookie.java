package Roma.item.custom;

import Roma.magic.ManaCapability;
import Roma.magic.ManaSyncPacket;
import Roma.magic.config.ManaConfig;
import Roma.magic.config.NetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.config.ModConfig;

public class ManaCookie extends Item {

    private static final int INCREASE = 20;




    public ManaCookie(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemInHand = pPlayer.getItemInHand(pUsedHand);

        if (!pLevel.isClientSide()) {
            pPlayer.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                // 1. Calculate the new values
                int newMax = mana.getMaxMana() + INCREASE;
                int newCurrent = mana.getMana() + INCREASE;

                // 2. Update the server-side capability
                mana.setMaxMana(newMax);
                mana.setMana(newCurrent);

                // 3. Send network packet to update client-side capability and HUD
                if (pPlayer instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(new ManaSyncPacket(newCurrent, newMax), serverPlayer);
                }
            });

            // Feedback and item consumption
            pPlayer.displayClientMessage(Component.literal("+20 Max Mana!"), true);
            pPlayer.playSound(SoundEvents.EXPERIENCE_ORB_PICKUP, 0.5f, 1.0f);

            if (!pPlayer.getAbilities().instabuild) {
                itemInHand.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemInHand, pLevel.isClientSide());
    }
}

    

