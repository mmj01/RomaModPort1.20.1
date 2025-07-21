package Roma.enchantment;

import Roma.roma;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = roma.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class enchantmentevents {

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntity().getMainHandItem();
        int level = stack.getEnchantmentLevel(ModEnchantments.FLEET.get());

        if (level > 0) {
            float originalSpeed = event.getNewSpeed();
            float boostedSpeed = originalSpeed * (1.0f + (level * 0.25f));
            event.setNewSpeed(boostedSpeed);
        }
    }


    @Mod.EventBusSubscriber(modid = roma.MOD_ID)
    public class DamageShieldEvents {

        @SubscribeEvent
        public static void onLivingHurt(LivingHurtEvent event) {
            if (!(event.getEntity() instanceof LivingEntity)) return;
            LivingEntity target = (LivingEntity) event.getEntity();



            float reduction = 0;

            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (!slot.isArmor()) continue;
                ItemStack armor = target.getItemBySlot(slot);

                int level = armor.getEnchantmentLevel(ModEnchantments.HEAVY.get());
                if (level > 0) {
                    reduction += 0.05F * level; // 4% per level like vanilla
                }
            }

            if (reduction > 0) {
                float original = event.getAmount();
                float reduced = original * (1.0F - reduction);
                event.setAmount(reduced);
            }
        }
    }


    private static boolean shouldSkipDamage(ItemStack stack, LivingEntity entity) {
        if (!stack.isDamageableItem()) return false;
        int level = stack.getEnchantmentLevel(ModEnchantments.ROBUST.get());
        if (level > 0) {
            return entity.getRandom().nextInt(5) != 0; // 4/5 chance to skip
        }
        return false;
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (shouldSkipDamage(stack, player)) {
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
        }
    }
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (!slot.isArmor()) continue;

            ItemStack armor = entity.getItemBySlot(slot);
            if (shouldSkipDamage(armor, entity)) {
                armor.setDamageValue(Math.max(0, armor.getDamageValue() - 1));
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerUse(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
            ItemStack stack = event.getInventory().getItem(i);
            if (shouldSkipDamage(stack, player)) {
                stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
            }
        }
    }




    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();

        Entity source = event.getSource().getEntity();
        if (!(source instanceof LivingEntity attacker)) return;

        ItemStack weapon = attacker.getMainHandItem();
        int level = weapon.getEnchantmentLevel(ModEnchantments.SHARP.get());

        if (!(event.getSource().getEntity() instanceof Player player)) return;




        ItemStack stack = player.getMainHandItem();
        if (shouldSkipDamage(stack, player)) {
            stack.setDamageValue(Math.max(0, stack.getDamageValue() - 1));
        }

        if (level > 0) {
            float baseDamage = event.getAmount();
            float extraDamage = 1.0F + 1.5F * level; // Your custom extra damage formula
            event.setAmount(baseDamage + extraDamage);


            if (level >= 4) {
                target.addEffect(new MobEffectInstance(
                        MobEffects.WEAKNESS,
                        30,
                        2,
                        false,
                        true

                ));
                target.addEffect(new MobEffectInstance(
                        MobEffects.WITHER,
                        10,
                        2,
                        false,
                        true

                ));
                target.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        10,
                        4,
                        false,
                        true

                ));
            }
        }
    }
}
