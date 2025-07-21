package Roma.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class fleet extends Enchantment {
    public fleet() {
        super(Rarity.UNCOMMON, EnchantmentCategory.DIGGER, new EquipmentSlot[]{
                EquipmentSlot.MAINHAND
        });
    }

    @Override
    public int getMaxLevel() {
        return 4; // like Efficiency
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
