package Roma.enchantment;

import Roma.item.custom.ReachItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class sharp extends Enchantment {
    public sharp() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        // Only swords
        return stack.getItem() instanceof ReachItem;
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
        return 4;
    }


}
