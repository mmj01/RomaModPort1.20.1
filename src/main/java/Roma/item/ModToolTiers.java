package Roma.item;

import Roma.block.ModBlocks;
import Roma.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;



public class ModToolTiers {
    public static final Tier STONE = new ForgeTier(
            1,300,4f,3,2,ModTags.Blocks.NEEDSSTONETOOL,()-> Ingredient.of(ModBlocks.ROCK.get(),ModBlocks.LIMESTONE.get(),ModBlocks.BASALT.get()));
    public static final Tier WOOD = new ForgeTier(
            0,120,3f,2,2,ModTags.Blocks.NEEDSWOODENTOOL,()-> Ingredient.EMPTY);

    public static final Tier COPPER = new ForgeTier(
            2,400,5f,4,2,ModTags.Blocks.NEEDSCOPPERTOOL,()-> Ingredient.of(Moditems.COPPERINGOT.get()));
    public static final Tier IRON = new ForgeTier(
            3,600,6f,5,2,ModTags.Blocks.NEEDSIRONTOOL,()-> Ingredient.of(Moditems.IRONINGOT.get()));

    public static final Tier BRASS = new ForgeTier(
            4,900,7f,6,2,ModTags.Blocks.NEEDSBRASSTOOL,()-> Ingredient.of(Moditems.BRASSINGOT.get()));
    public static final Tier BRONZE = new ForgeTier(
            5,1200,8f,7,2,ModTags.Blocks.NEEDSBRONZETOOL,()-> Ingredient.of(Moditems.BRONZEINGOT.get()));

    public static final Tier LSTEEL = new ForgeTier(
            6,1500,9f,8,2,ModTags.Blocks.NEEDSLSTEELTOOL,()-> Ingredient.of(Moditems.LSTEELINGOT.get()));
    public static final Tier HSTEEL = new ForgeTier(
            7,2000,11f,9,2,ModTags.Blocks.NEEDSHSTEELTOOL,() -> Ingredient.of(Moditems.HSTEELINGOT.get()));

    public static final Tier SUPERALLOY = new ForgeTier(
            7,30000,14f,12,2,ModTags.Blocks.NEEDSSUPERALLOYTOOL,() -> Ingredient.of(Moditems.COPPERINGOT.get()));


    public static final Tier DENSESTONE = new ForgeTier(
            1,1800,10f,3,2,ModTags.Blocks.NEEDSSTONETOOL,()-> Ingredient.of(ModBlocks.ROCK.get(),ModBlocks.LIMESTONE.get(),ModBlocks.BASALT.get()));
    public static final Tier DENSEWOOD = new ForgeTier(
            0,600,9f,2,2,ModTags.Blocks.NEEDSWOODENTOOL,()-> Ingredient.EMPTY);

    public static final Tier DENSECOPPER = new ForgeTier(
            2,2400,11f,4,2,ModTags.Blocks.NEEDSCOPPERTOOL,()-> Ingredient.of(Moditems.COPPERINGOT.get()));
    public static final Tier DENSEIRON = new ForgeTier(
            3,3600,12f,5,2,ModTags.Blocks.NEEDSIRONTOOL,()-> Ingredient.of(Moditems.IRONINGOT.get()));

    public static final Tier DENSEBRASS = new ForgeTier(
            4,5400,13f,6,2,ModTags.Blocks.NEEDSBRASSTOOL,()-> Ingredient.of(Moditems.BRASSINGOT.get()));
    public static final Tier DENSEBRONZE = new ForgeTier(
            5,7200,14f,7,2,ModTags.Blocks.NEEDSBRONZETOOL,()-> Ingredient.of(Moditems.BRONZEINGOT.get()));

    public static final Tier DENSELSTEEL = new ForgeTier(
            6,9000,15f,8,2,ModTags.Blocks.NEEDSLSTEELTOOL,()-> Ingredient.of(Moditems.LSTEELINGOT.get()));
    public static final Tier DENSEHSTEEL = new ForgeTier(
            7,12000,17f,9,2,ModTags.Blocks.NEEDSHSTEELTOOL,() -> Ingredient.of(Moditems.HSTEELINGOT.get()));

    public static final Tier DENSESUPERALLOY = new ForgeTier(
            7,40000,20f,12,2,ModTags.Blocks.NEEDSSUPERALLOYTOOL,() -> Ingredient.of(Moditems.SUPERALLOYINGOT.get()));



}
