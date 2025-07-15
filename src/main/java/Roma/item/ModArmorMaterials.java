package Roma.item;

import Roma.roma;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    IRONARMOR("ironarmormaterial", 500,
            makeMap(5, 7, 9, 5), 5,
            SoundEvents.ARMOR_EQUIP_IRON, 0.1f, 0.1f,
            () -> Ingredient.of(Moditems.IRONINGOT.get())),

    BRASSARMOR("brassarmormaterial", 625,
            makeMap(6, 8, 10, 6), 4,
            SoundEvents.ARMOR_EQUIP_CHAIN, 0.1f, 0.2f,
            () -> Ingredient.of(Moditems.BRASSINGOT.get())),

    COPPERARMOR("copperarmormaterial", 375,
            makeMap(4, 6, 8, 4), 3,
            SoundEvents.ARMOR_EQUIP_CHAIN, 0.0f, 0.0f,
            () -> Ingredient.of(Moditems.COPPERINGOT.get())),

    BRONZEARMOR("bronzearmormaterial", 750,
            makeMap(7, 10, 12, 7), 6,
            SoundEvents.ARMOR_EQUIP_IRON, 0.2f, 0.3f,
            () -> Ingredient.of(Moditems.BRONZEINGOT.get())),

    LSTEELARMOR("lsteelarmormaterial", 875,
            makeMap(9, 12, 14, 9), 8,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 0.4f, 0.4f,
            () -> Ingredient.of(Moditems.LSTEELINGOT.get())),

    HSTEELARMOR("hsteelarmormaterial", 1000,
            makeMap(10, 14, 17, 10), 9,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 0.5f, 0.5f,
            () -> Ingredient.of(Moditems.HSTEELINGOT.get())),

    SUPERALLOYARMOR("superalloymaterial", 1850,
            makeMap(18, 23, 26, 18), 13,
            SoundEvents.ARMOR_EQUIP_NETHERITE, 0.7f, 0.8f,
            () -> Ingredient.of(Moditems.SUPERALLOYINGOT.get()));

    // ==== Fields ====

    private static final int[] BASE_DURABILITY = {16, 18, 19, 14}; // boots, leggings, chestplate, helmet

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionValues;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    // ==== Constructor ====

    ModArmorMaterials(String name, int durabilityMultiplier,
                      EnumMap<ArmorItem.Type, Integer> protectionValues,
                      int enchantmentValue, SoundEvent equipSound,
                      float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {

        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionValues = protectionValues;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    // ==== Implementation ====

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionValues.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return roma.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    private static EnumMap<ArmorItem.Type, Integer> makeMap(int boots, int leggings, int chestplate, int helmet) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        map.put(ArmorItem.Type.BOOTS, boots);
        map.put(ArmorItem.Type.LEGGINGS, leggings);
        map.put(ArmorItem.Type.CHESTPLATE, chestplate);
        map.put(ArmorItem.Type.HELMET, helmet);
        return map;
    }
}
