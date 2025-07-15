package Roma.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties WHEAT = new FoodProperties
            .Builder()
            .nutrition(4)
            .saturationMod(2)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 3),1.0f)
            .build();

    public static final FoodProperties BREAD = new FoodProperties
            .Builder()
            .nutrition(3)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 3),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 40, 4),1.0f)
            .build();
    public static final FoodProperties POTATO = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 3),1.0f)
            .build();
    public static final FoodProperties CARROT = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,6),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .build();






                            //fish section
    public static final FoodProperties COD = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300,1),1.0f)
            .build();

    public static final FoodProperties SALMON = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300,1),1.0f)

            .build();
    public static final FoodProperties CARP = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 300,3),1.0f)
            .build();
    public static final FoodProperties TROUT = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300,2),1.0f)
            .build();
    public static final FoodProperties BLUEGILL = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,8),1.0f)
            .build();
    public static final FoodProperties GUPPY = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300,4),1.0f)
            .build();
    public static final FoodProperties CATFISH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 300,6),1.0f)
            .build();
    public static final FoodProperties BASS = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300,3),1.0f)
            .build();
    public static final FoodProperties MARLIN = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 300,12),1.0f)
            .build();
    public static final FoodProperties SHARK = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,16),1.0f)
            .build();

}
