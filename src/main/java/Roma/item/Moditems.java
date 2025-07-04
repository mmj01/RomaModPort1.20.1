package Roma.item;

import Roma.block.ModBlocks;
import Roma.item.custom.FuelItem;
import Roma.item.custom.ReachItem;
import Roma.item.custom.chisel;
import Roma.roma;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class Moditems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, roma.MOD_ID);



    public static final RegistryObject<Item> IRONINGOT = ITEMS.register("ironingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COBALTINGOT = ITEMS.register("cobaltingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUMINGOT = ITEMS.register("aluminumingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHROMIUMINGOT = ITEMS.register("chromiumingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPERINGOT = ITEMS.register("copperingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZEINGOT = ITEMS.register("bronzeingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASSINGOT = ITEMS.register("brassingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HSTEELINGOT = ITEMS.register("hsteelingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LSTEELINGOT = ITEMS.register("lsteelingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TININGOT = ITEMS.register("tiningot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZINCINGOT = ITEMS.register("zincingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLDINGOT = ITEMS.register("goldingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MARBLEVENEER = ITEMS.register("marbleveneer",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILVERINGOT = ITEMS.register("silveringot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYINGOT = ITEMS.register("superalloyingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NICKELINGOT = ITEMS.register("nickelingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATINUMINGOT = ITEMS.register("platinumingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWPLATINUM = ITEMS.register("rawplatinum",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWIRON = ITEMS.register("rawiron",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWCOPPER = ITEMS.register("rawcopper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWZINC = ITEMS.register("rawzinc",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWGOLD = ITEMS.register("rawgold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWNICKEL = ITEMS.register("rawnickel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWSILVER = ITEMS.register("rawsilver",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWTIN = ITEMS.register("rawtin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWCHROMIUM = ITEMS.register("rawchromium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWCOBALT = ITEMS.register("rawcobalt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAWALUMINUM = ITEMS.register("rawaluminum",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel",
            () -> new chisel(new Item.Properties().durability(400)));
    public static final RegistryObject<Item> COAL = ITEMS.register("coal",
            () -> new FuelItem(new Item.Properties(), 50));
    public static final RegistryObject<Item> WHEATSEEDS = ITEMS.register("wheatseeds",
            () ->  new ItemNameBlockItem(ModBlocks.WHEATCROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHEAT = ITEMS.register("wheat",
            () -> new Item(new Item.Properties().food(ModFoodProperties.WHEAT)));
    public static final RegistryObject<Item> BREAD = ITEMS.register("bread",
            () -> new Item(new Item.Properties().food(ModFoodProperties.BREAD)));

    public static final RegistryObject<Item> IRONBLADE = ITEMS.register("ironblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOODBLADE = ITEMS.register("woodblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONEBLADE = ITEMS.register("stoneblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPERBLADE = ITEMS.register("copperblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASSBLADE = ITEMS.register("brassblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZEBLADE = ITEMS.register("bronzeblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LSTEELBLADE = ITEMS.register("lsteelblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HSTEELBLADE = ITEMS.register("hsteelblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYBLADE = ITEMS.register("superalloyblade",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COMPRESSEDCOAL = ITEMS.register("compressedcoal",
            () -> new FuelItem(new Item.Properties(), 450));
    public static final RegistryObject<Item> ULTRADENSECOAL = ITEMS.register("ultradensecoal",
            () -> new FuelItem(new Item.Properties(), 4050));
    public static final RegistryObject<Item> ALLOYMIXER = ITEMS.register("alloymixer",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZEALLOYAMALGAM = ITEMS.register("bronzealloyamalgam",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASSALLOYAMALGAM = ITEMS.register("brassalloyamalgam",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TREATEDIRONINGOT = ITEMS.register("treatedironingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> IRONPLATE = ITEMS.register("ironplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPERPLATE = ITEMS.register("copperplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRASSPLATE = ITEMS.register("brassplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BRONZEPLATE = ITEMS.register("bronzeplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LSTEELPLATE = ITEMS.register("lsteelplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HSTEELPLATE = ITEMS.register("hsteelplate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYPLATE = ITEMS.register("superalloyplate",
            () -> new Item(new Item.Properties()));





    public static final RegistryObject<Item> IRONAXE = ITEMS.register("ironaxe",
            () -> new AxeItem(ModToolTiers.IRON,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> STONEAXE = ITEMS.register("stoneaxe",
            () -> new AxeItem(ModToolTiers.STONE,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> WOODAXE = ITEMS.register("woodaxe",
            () -> new AxeItem(ModToolTiers.WOOD,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> BRASSAXE = ITEMS.register("brassaxe",
            () -> new AxeItem(ModToolTiers.BRASS,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> HSTEELAXE = ITEMS.register("hsteelaxe",
            () -> new AxeItem(ModToolTiers.HSTEEL,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYAXE = ITEMS.register("superalloyaxe",
            () -> new AxeItem(ModToolTiers.SUPERALLOY,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> BRONZEAXE = ITEMS.register("bronzeaxe",
            () -> new AxeItem(ModToolTiers.IRON,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> LSTEELAXE = ITEMS.register("lsteelaxe",
            () -> new AxeItem(ModToolTiers.STONE,6.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> COPPERAXE = ITEMS.register("copperaxe",
            () -> new AxeItem(ModToolTiers.WOOD,6.0f,-3.1f,new Item.Properties()));

    public static final RegistryObject<Item> IRONSHOVEL = ITEMS.register("ironshovel",
            () -> new ShovelItem(ModToolTiers.IRON,3.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> STONESHOVEL = ITEMS.register("stoneshovel",
            () -> new ShovelItem(ModToolTiers.STONE,3.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> WOODSHOVEL = ITEMS.register("woodshovel",
            () -> new ShovelItem(ModToolTiers.WOOD,3.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> BRASSSHOVEL = ITEMS.register("brassshovel",
            () -> new ShovelItem(ModToolTiers.BRASS,3.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> HSTEELSHOVEL = ITEMS.register("hsteelshovel",
            () -> new ShovelItem(ModToolTiers.HSTEEL,3.0f,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYSHOVEL = ITEMS.register("superalloyshovel",
            () -> new ShovelItem(ModToolTiers.SUPERALLOY,3.0f,-3.1f,new Item.Properties()));

    public static final RegistryObject<Item> IRONHOE = ITEMS.register("ironhoe",
            () -> new HoeItem(ModToolTiers.IRON,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> STONEHOE = ITEMS.register("stonehoe",
            () -> new HoeItem(ModToolTiers.STONE,3,-3.1f,new Item.Properties()));

    public static final RegistryObject<Item> IRONPICKAXE = ITEMS.register("ironpickaxe",
            () -> new PickaxeItem(ModToolTiers.IRON,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> COPPERPICKAXE = ITEMS.register("copperpickaxe",
            () -> new PickaxeItem(ModToolTiers.COPPER,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> STONEPICKAXE = ITEMS.register("stonepickaxe",
            () -> new PickaxeItem(ModToolTiers.STONE,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> WOODPICKAXE = ITEMS.register("woodpickaxe",
            () -> new PickaxeItem(ModToolTiers.WOOD,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> BRASSPICKAXE = ITEMS.register("brasspickaxe",
            () -> new PickaxeItem(ModToolTiers.BRASS,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> BRONZEPICKAXE = ITEMS.register("bronzepickaxe",
            () -> new PickaxeItem(ModToolTiers.BRONZE,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> HSTEELPICKAXE = ITEMS.register("hsteelpickaxe",
            () -> new PickaxeItem(ModToolTiers.HSTEEL,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> LSTEELPICKAXE = ITEMS.register("lsteelpickaxe",
            () -> new PickaxeItem(ModToolTiers.LSTEEL,3,-3.1f,new Item.Properties()));
    public static final RegistryObject<Item> SUPERALLOYPICKAXE = ITEMS.register("superalloypickaxe",
            () -> new PickaxeItem(ModToolTiers.SUPERALLOY,3,-3.1f,new Item.Properties()));





    public static final RegistryObject<Item> IRONGREATSWORD = ITEMS.register("irongreatsword",
            () -> new ReachItem(ModToolTiers.IRON,4,-3,4.5,2.2,new Item.Properties()));

    public static final RegistryObject<Item> COPPERGREATSWORD = ITEMS.register("coppergreatsword",
            () -> new ReachItem(ModToolTiers.COPPER, 3,-3, 4.5, 2.2, new Item.Properties()));

    public static final RegistryObject<Item> STONEGREATSWORD = ITEMS.register("stonegreatsword",
            () -> new ReachItem(ModToolTiers.STONE,2,-3,4.5,2.4,new Item.Properties()));

    public static final RegistryObject<Item> WOODGREATSWORD = ITEMS.register("woodgreatsword",
            () -> new ReachItem(ModToolTiers.WOOD, 1,-2, 4.5, 2.0, new Item.Properties()));

    public static final RegistryObject<Item> BRONZEGREATSWORD = ITEMS.register("bronzegreatsword",
            () -> new ReachItem(ModToolTiers.BRONZE,6,-3,4.5,2.4,new Item.Properties()));

    public static final RegistryObject<Item> BRASSGREATSWORD = ITEMS.register("brassgreatsword",
            () -> new ReachItem(ModToolTiers.BRASS, 5,-3, 4.5, 2.4, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELEGREATSWORD = ITEMS.register("lsteelgreatsword",
            () -> new ReachItem(ModToolTiers.LSTEEL,7,-3,4.5,2.6,new Item.Properties()));

    public static final RegistryObject<Item> HSTEELGREATSWORD = ITEMS.register("hsteelgreatsword",
            () -> new ReachItem(ModToolTiers.HSTEEL, 8,-3, 4.5, 2.6, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYGREATSWORD = ITEMS.register("superalloygreatsword",
            () -> new ReachItem(ModToolTiers.SUPERALLOY, 10,-3, 5, 3, new Item.Properties()));


    public static final RegistryObject<Item> IRONBATTLEAXE = ITEMS.register("ironbattleaxe",
            () -> new ReachItem(ModToolTiers.IRON,0,-2,3.5,2.2,new Item.Properties()));

    public static final RegistryObject<Item> COPPERBATTLEAXE = ITEMS.register("copperbattleaxe",
            () -> new ReachItem(ModToolTiers.COPPER, 0,-2, 3.5, 2.2, new Item.Properties()));

    public static final RegistryObject<Item> STONEBATTLEAXE = ITEMS.register("stonebattleaxe",
            () -> new ReachItem(ModToolTiers.STONE,0,-2,3.5,2.4,new Item.Properties()));

    public static final RegistryObject<Item> WOODBATTLEAXE = ITEMS.register("woodbattleaxe",
            () -> new ReachItem(ModToolTiers.WOOD, 0,-1, 3.5, 2.0, new Item.Properties()));

    public static final RegistryObject<Item> BRONZEBATTLEAXE = ITEMS.register("bronzebattleaxe",
            () -> new ReachItem(ModToolTiers.BRONZE,2,-2,3.5,2.4,new Item.Properties()));

    public static final RegistryObject<Item> BRASSBATTLEAXE = ITEMS.register("brassbattleaxe",
            () -> new ReachItem(ModToolTiers.BRASS, 1,-2, 3.5, 2.4, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELEBATTLEAXE = ITEMS.register("lsteelbattleaxe",
            () -> new ReachItem(ModToolTiers.LSTEEL,3,-2,3.5,2.6,new Item.Properties()));

    public static final RegistryObject<Item> HSTEELBATTLEAXE = ITEMS.register("hsteelbattleaxe",
            () -> new ReachItem(ModToolTiers.HSTEEL, 4,-2, 3.5, 2.6, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYBATTLEAXE = ITEMS.register("superalloybattleaxe",
            () -> new ReachItem(ModToolTiers.SUPERALLOY, 6,-2, 4, 3, new Item.Properties()));

    public static final RegistryObject<Item> COPPERHELMET = ITEMS.register("copperhelmet",
            () -> new ArmorItem(ModArmorMaterials.COPPERARMOR, ArmorItem.Type.HELMET, new Item.Properties()));


    public static final RegistryObject<Item> COPPERCHESTPLATE = ITEMS.register("copperchestplate",
            () -> new ArmorItem(ModArmorMaterials.COPPERARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> COPPERLEGGINGS = ITEMS.register("copperleggings",
            () -> new ArmorItem(ModArmorMaterials.COPPERARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> COPPERBOOTS = ITEMS.register("copperboots",
            () -> new ArmorItem(ModArmorMaterials.COPPERARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> IRONHELMET = ITEMS.register("ironhelmet",
            () -> new ArmorItem(ModArmorMaterials.IRONARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> IRONCHESTPLATE = ITEMS.register("ironchestplate",
            () -> new ArmorItem(ModArmorMaterials.IRONARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> IRONLEGGINGS = ITEMS.register("ironleggings",
            () -> new ArmorItem(ModArmorMaterials.IRONARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> IRONBOOTS = ITEMS.register("ironboots",
            () -> new ArmorItem(ModArmorMaterials.IRONARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BRASSHELMET = ITEMS.register("brasshelmet",
            () -> new ArmorItem(ModArmorMaterials.BRASSARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> BRASSCHESTPLATE = ITEMS.register("brasschestplate",
            () -> new ArmorItem(ModArmorMaterials.BRASSARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> BRASSLEGGINGS = ITEMS.register("brassleggings",
            () -> new ArmorItem(ModArmorMaterials.BRASSARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> BRASSBOOTS = ITEMS.register("brassboots",
            () -> new ArmorItem(ModArmorMaterials.BRASSARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BRONZEHELMET = ITEMS.register("bronzehelmet",
            () -> new ArmorItem(ModArmorMaterials.BRONZEARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> BRONZECHESTPLATE = ITEMS.register("bronzechestplate",
            () -> new ArmorItem(ModArmorMaterials.BRONZEARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> BRONZELEGGINGS = ITEMS.register("bronzeleggings",
            () -> new ArmorItem(ModArmorMaterials.BRONZEARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> BRONZEBOOTS = ITEMS.register("bronzeboots",
            () -> new ArmorItem(ModArmorMaterials.BRONZEARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELHELMET = ITEMS.register("lsteelhelmet",
            () -> new ArmorItem(ModArmorMaterials.LSTEELARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELCHESTPLATE = ITEMS.register("lsteelchestplate",
            () -> new ArmorItem(ModArmorMaterials.LSTEELARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELLEGGINGS = ITEMS.register("lsteelleggings",
            () -> new ArmorItem(ModArmorMaterials.LSTEELARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> LSTEELBOOTS = ITEMS.register("lsteelboots",
            () -> new ArmorItem(ModArmorMaterials.LSTEELARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> HSTEELHELMET = ITEMS.register("hsteelhelmet",
            () -> new ArmorItem(ModArmorMaterials.HSTEELARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> HSTEELCHESTPLATE = ITEMS.register("hsteelchestplate",
            () -> new ArmorItem(ModArmorMaterials.HSTEELARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> HSTEELLEGGINGS = ITEMS.register("hsteelleggings",
            () -> new ArmorItem(ModArmorMaterials.HSTEELARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> HSTEELBOOTS = ITEMS.register("hsteelboots",
            () -> new ArmorItem(ModArmorMaterials.HSTEELARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYHELMET = ITEMS.register("superalloyhelmet",
            () -> new ArmorItem(ModArmorMaterials.SUPERALLOYARMOR, ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYCHESTPLATE = ITEMS.register("superalloychestplate",
            () -> new ArmorItem(ModArmorMaterials.SUPERALLOYARMOR, ArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYLEGGINGS = ITEMS.register("superalloyleggings",
            () -> new ArmorItem(ModArmorMaterials.SUPERALLOYARMOR, ArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> SUPERALLOYBOOTS = ITEMS.register("superalloyboots",
            () -> new ArmorItem(ModArmorMaterials.SUPERALLOYARMOR, ArmorItem.Type.BOOTS, new Item.Properties()));







    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
