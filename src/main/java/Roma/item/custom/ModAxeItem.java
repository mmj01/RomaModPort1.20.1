package Roma.item.custom;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import Roma.menu.skillmenu.StatTrackEvents;

public class ModAxeItem extends AxeItem {

    public ModAxeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        // Fetch the base durability from ModToolTiers
        int baseDurability = super.getMaxDamage(stack);

        // Multiply it by our Utility scale (+25%, +15%, etc.) based on Quality NBT
        return (int) (baseDurability * StatTrackEvents.getUtilityMultiplier(stack));
    }
}