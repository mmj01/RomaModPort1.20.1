package Roma.item.custom;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import Roma.menu.skillmenu.StatTrackEvents;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        // Fetch the base durability for this armor piece
        int baseDurability = super.getMaxDamage(stack);

        // Multiply it by our Utility scale based on Quality NBT
        return (int) (baseDurability * StatTrackEvents.getUtilityMultiplier(stack));
    }
}