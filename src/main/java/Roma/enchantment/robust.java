package Roma.enchantment;

import Roma.item.custom.ReachItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class robust extends Enchantment {
    public robust() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[] {
                EquipmentSlot.MAINHAND,
                EquipmentSlot.OFFHAND,
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        });
    }


    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        // Only swords
        return stack.isDamageableItem();
    }
    @Override
    public boolean isDiscoverable() {
        return true; // Makes it show up in enchanting table and /enchant
    }

    @Override
    public boolean isTradeable() {
        return true; // Makes it available through villager trades
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true; // Allows it to appear on enchanted books
    }



    @Override
    public int getMinCost(int level) {
        // Increase base cost and scale with level
        return 100 + (level - 1) * 20;  // starts at 20, +15 per level
    }

    @Override
    public int getMaxCost(int level) {
        return this.getMinCost(level) + 100000; // max cost is min cost + 10
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
