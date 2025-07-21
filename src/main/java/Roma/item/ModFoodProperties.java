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
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200,1),1.0f)
            .build();
    public static final FoodProperties STEAK = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEAL, 1,2),1.0f)
            .build();
    public static final FoodProperties CHICKEN = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.SATURATION, 300,1),1.0f)
            .build();
    public static final FoodProperties HAM = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.JUMP, 300,1),1.0f)
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

    //non-base foods


    public static final FoodProperties ROASTEDSTEAK = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEAL, 1,5),1.0f)
            .build();
    public static final FoodProperties GRILLEDCHICKEN = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.SATURATION, 300,3),1.0f)
            .build();
    public static final FoodProperties SMOKEDHAM = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.JUMP, 600,3),1.0f)
            .build();






    public static final FoodProperties GRILLEDCOD = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600,1),1.0f)
            .build();

    public static final FoodProperties GRILLEDSALMON = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600,1),1.0f)

            .build();
    public static final FoodProperties GRILLEDCARP = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600,5),1.0f)
            .build();
    public static final FoodProperties GRILLEDTROUT = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600,2),1.0f)
            .build();
    public static final FoodProperties GRILLEDBLUEGILL = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,10),1.0f)
            .build();
    public static final FoodProperties GRILLEDGUPPY = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600,6),1.0f)
            .build();
    public static final FoodProperties GRILLEDCATFISH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600,8),1.0f)
            .build();
    public static final FoodProperties GRILLEDBASS = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600,3),1.0f)
            .build();
    public static final FoodProperties GRILLEDMARLIN = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 600,14),1.0f)
            .build();
    public static final FoodProperties GRILLEDSHARK = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,18),1.0f)
            .build();
    public static final FoodProperties BAKEDPOTATO = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 5),1.0f)
            .build();
    public static final FoodProperties BOILEDCARROT = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,8),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 2400,1),1.0f)
            .build();
    public static final FoodProperties SANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();

    public static final FoodProperties CODSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();

    public static final FoodProperties SALMONSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)

            .build();
    public static final FoodProperties CARPSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 300,5),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties TROUTSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600,3),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties BLUEGILLSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,16),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties GUPPYSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600,8),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties CATFISHSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 600,8),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties BASSSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600,4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties MARLINSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 600,18),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300,7),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .build();
    public static final FoodProperties SHARKSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,25),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();

    public static final FoodProperties STEAKSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.HEAL, 3,5),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,25),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties HAMSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.JUMP, 1200,3),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,25),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();
    public static final FoodProperties CHICKENSANDWICH = new FoodProperties
            .Builder()
            .nutrition(1)
            .saturationMod(1)
            .alwaysEat()
            .effect(new MobEffectInstance(MobEffects.SATURATION, 300,6),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600,25),1.0f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600,1),1.0f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 1),1.0f)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 4),1.0f)
            .effect(new MobEffectInstance(MobEffects.DIG_SPEED, 200, 2),1.0f)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, 20, 3),1.0f)
            .build();


}
