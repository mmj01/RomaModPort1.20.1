package Roma.item.custom;

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

public class strengthskill extends Item {




    public strengthskill(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if(!pLevel.isClientSide && !pPlayer.getCooldowns().isOnCooldown(this))  {

            pPlayer.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,8,false,false));

            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS,1.0f,1.0f);

            pPlayer.getCooldowns().addCooldown(this, 1200);

        }
        return InteractionResultHolder.sidedSuccess(stack, pLevel.isClientSide());
    }

    
}
