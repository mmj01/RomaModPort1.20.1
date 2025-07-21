package Roma.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.Enchantments;

public class heavy extends Enchantment {
    public heavy() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        });
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    public boolean checkCompatibility(Enchantment other) {
        // Optional: prevent stacking with vanilla protection
        return super.checkCompatibility(other) && other != Enchantments.ALL_DAMAGE_PROTECTION;
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
}
