package Roma.block;

import Roma.block.custom.*;
import Roma.block.custom.CarrotBlock;
import Roma.block.custom.PotatoBlock;
import Roma.item.Moditems;
import Roma.roma;
import Roma.worldgen.tree.ModTreeGrower;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, roma.MOD_ID);


    public static final RegistryObject<Block> MARBLE = registerBlock("marble",
            ()-> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(75f).explosionResistance(16f).requiresCorrectToolForDrops(),UniformInt.of(50,60)));


    public static final RegistryObject<Block> ROCKBRICKS = registerBlock("rockbricks",
            ()-> new Block(BlockBehaviour.Properties
                    .copy(Blocks.STONE)
                    .strength(15f)
                    .explosionResistance(8f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CYPRESSSAPLING = registerBlock("cypresssapling",
            () -> new SaplingBlock(new ModTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));



    public static final RegistryObject<Block> CYPRESSLOG = registerBlock("cypresslog",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> CYPRESSWOOD = registerBlock("cypresswood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD).strength(3f)));
    public static final RegistryObject<Block> STRIPPEDCYPRESSLOG = registerBlock("strippedcypresslog",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG).strength(3f)));
    public static final RegistryObject<Block> STRIPPEDCYPRESSWOOD = registerBlock("strippedcypresswood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD).strength(3f)));



    public static final RegistryObject<Block> CYPRESSPLANKS = registerBlock("cypressplanks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> CYPRESSLEAVES = registerBlock("cypressleaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).randomTicks().noOcclusion()){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    public static final RegistryObject<Block> GRASS = BLOCKS.register("grass",
            () -> new ModGrass(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));

    public static final RegistryObject<Block> SAND = registerBlock("sand",
            ()-> new FallingBlock( BlockBehaviour.Properties.copy(Blocks.SAND)
                    .strength(2f).explosionResistance(1f)) {
                @Override
                protected void falling(FallingBlockEntity pEntity) {
                    super.falling(pEntity);
                }
            });
    public static final RegistryObject<Block> DIRT = registerBlock("dirt",
            ()-> new ModDirt(BlockBehaviour.Properties.copy(Blocks.DIRT)));

    public static final RegistryObject<Block> FARMLAND = BLOCKS.register("farmland",
            () -> new ModFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND)));



    public static final RegistryObject<Block> ROCK = registerBlock("rock",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(10f).explosionResistance(8f).requiresCorrectToolForDrops(),UniformInt.of(1,2)));
    public static final RegistryObject<Block> GRANITE = registerBlock("granite",
            ()-> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(55f).explosionResistance(15f).requiresCorrectToolForDrops(),UniformInt.of(5,10)));
    public static final RegistryObject<Block> ALABASTER = registerBlock("alabaster",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(10f).requiresCorrectToolForDrops(),UniformInt.of(20,30)));
    public static final RegistryObject<Block> LIMESTONE = registerBlock("limestone",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(25f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> BASALT = registerBlock("basalt",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> IRONORE = registerBlock("ironore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> GOLDORE = registerBlock("goldore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> ALUMINUMORE = registerBlock("aluminumore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(25f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> COBALTORE = registerBlock("cobaltore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(55f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> CHROMIUMORE = registerBlock("chromiumore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(85f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> TINORE = registerBlock("tinore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> ZINCORE = registerBlock("zincore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> COPPERORE = registerBlock("copperore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(25f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SILVERORE = registerBlock("silverore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(40f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> PLATINUMORE = registerBlock("platinumore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(85f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> NICKELORE = registerBlock("nickelore",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLE = registerBlock("supermarble",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLETYONE = registerBlock("supermarbletyone",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLETYTWO = registerBlock("supermarbletytwo",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLETYTHREE = registerBlock("supermarbletythree",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLETYFOUR = registerBlock("supermarbletyfour",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> SUPERMARBLETYFIVE = registerBlock("supermarbletyfive",
            ()-> new DropExperienceBlock( BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(70f).explosionResistance(5f).requiresCorrectToolForDrops(),UniformInt.of(3,4)));
    public static final RegistryObject<Block> COALORE = registerBlock("coalore",
            ()-> new DropExperienceBlock(
                    BlockBehaviour.Properties.copy(Blocks.STONE)
                    .strength(10f)
                            .explosionResistance(5f)
                            .requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> WHEATCROP = BLOCKS.register("wheatcrop",
            () -> new ModCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> POTATOCROP = BLOCKS.register("potatocrop",
            () -> new PotatoBlock(BlockBehaviour.Properties.copy(Blocks.POTATOES)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<Block> CARROTCROP = BLOCKS.register("carrotcrop",
            () -> new CarrotBlock(BlockBehaviour.Properties.copy(Blocks.CARROTS)
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .offsetType(BlockBehaviour.OffsetType.XZ)));






    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        Moditems.ITEMS.register(name, ()-> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
