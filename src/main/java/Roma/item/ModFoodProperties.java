package Roma.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties WHEAT = new FoodProperties
            .Builder()
            .nutrition(4)
            .saturationMod(4)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 3),1.0f)
            .build();

    public static final FoodProperties BREAD = new FoodProperties
            .Builder()
            .nutrition(6)
            .saturationMod(4)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 3),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 40, 4),1.0f)
            .build();
}
