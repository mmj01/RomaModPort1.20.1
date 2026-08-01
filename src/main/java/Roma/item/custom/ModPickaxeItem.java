package Roma.item.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class ModPickaxeItem extends PickaxeItem {
    public ModPickaxeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        // Get the base durability from your ModToolTiers (e.g., 30000 for Superalloy)
        int baseDurability = super.getMaxDamage(stack);

        // Multiply it by our Utility scale (+25%, +15%, etc.)
        return (int) (baseDurability * Roma.menu.skillmenu.StatTrackEvents.getUtilityMultiplier(stack));
    }
}
