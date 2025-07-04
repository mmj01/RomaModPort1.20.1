package Roma.item;

import Roma.block.ModBlocks;
import Roma.roma;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, roma.MOD_ID);

public static final RegistryObject<CreativeModeTab> ROMA = CREATIVE_MODE_TABS.register("roma",
    () -> CreativeModeTab.builder().icon(()-> new ItemStack(ModBlocks.MARBLE.get()))
            .title(Component.translatable("ROMA"))
            .displayItems((itemDisplayParameters, output) ->{
                output.accept(ModBlocks.CYPRESSLOG.get());
                output.accept(ModBlocks.CYPRESSWOOD.get());
                output.accept(ModBlocks.STRIPPEDCYPRESSLOG.get());
                output.accept(ModBlocks.STRIPPEDCYPRESSWOOD.get());

                output.accept(ModBlocks.CYPRESSPLANKS.get());
                output.accept(ModBlocks.CYPRESSSAPLING.get());

                output.accept(ModBlocks.CYPRESSLEAVES.get());
                output.accept(Moditems.IRONINGOT.get());
                output.accept(Moditems.COBALTINGOT.get());
                output.accept(Moditems.ALUMINUMINGOT.get());
                output.accept(Moditems.CHROMIUMINGOT.get());
                output.accept(Moditems.COPPERINGOT.get());
                output.accept(Moditems.BRONZEINGOT.get());
                output.accept(Moditems.BRASSINGOT.get());
                output.accept(Moditems.HSTEELINGOT.get());
                output.accept(Moditems.TININGOT.get());
                output.accept(Moditems.ZINCINGOT.get());
                output.accept(Moditems.GOLDINGOT.get());
                output.accept(Moditems.LSTEELINGOT.get());
                output.accept(Moditems.MARBLEVENEER.get());
                output.accept(Moditems.SUPERALLOYINGOT.get());
                output.accept(Moditems.SILVERINGOT.get());
                output.accept(Moditems.NICKELINGOT.get());
                output.accept(Moditems.PLATINUMINGOT.get());
                output.accept(Moditems.RAWALUMINUM.get());
                output.accept(Moditems.RAWCHROMIUM.get());
                output.accept(Moditems.RAWCOBALT.get());
                output.accept(Moditems.RAWGOLD.get());
                output.accept(Moditems.RAWIRON.get());
                output.accept(Moditems.RAWNICKEL.get());
                output.accept(Moditems.RAWCOPPER.get());
                output.accept(Moditems.RAWZINC.get());
                output.accept(Moditems.RAWTIN.get());
                output.accept(Moditems.RAWPLATINUM.get());
                output.accept(Moditems.RAWSILVER.get());
                output.accept(Moditems.CHISEL.get());
                output.accept(Moditems.COAL.get());
                output.accept(Moditems.WHEAT.get());
                output.accept(Moditems.WHEATSEEDS.get());

                output.accept(Moditems.IRONSHOVEL.get());
                output.accept(Moditems.IRONBATTLEAXE.get());
                output.accept(Moditems.IRONAXE.get());
                output.accept(Moditems.IRONGREATSWORD.get());
                output.accept(Moditems.IRONPICKAXE.get());

                output.accept(Moditems.STONEAXE.get());
                output.accept(Moditems.STONEBATTLEAXE.get());
                output.accept(Moditems.STONEPICKAXE.get());
                output.accept(Moditems.STONEGREATSWORD.get());
                output.accept(Moditems.STONESHOVEL.get());

                output.accept(Moditems.WOODAXE.get());
                output.accept(Moditems.WOODBATTLEAXE.get());
                output.accept(Moditems.WOODGREATSWORD.get());
                output.accept(Moditems.WOODPICKAXE.get());
                output.accept(Moditems.WOODSHOVEL.get());

                output.accept(Moditems.COPPERBATTLEAXE.get());
                output.accept(Moditems.COPPERGREATSWORD.get());
                output.accept(Moditems.COPPERPICKAXE.get());

                output.accept(Moditems.BRASSAXE.get());
                output.accept(Moditems.BRASSBATTLEAXE.get());
                output.accept(Moditems.BRASSGREATSWORD.get());
                output.accept(Moditems.BRASSPICKAXE.get());
                output.accept(Moditems.BRASSSHOVEL.get());

                output.accept(Moditems.BRONZEBATTLEAXE.get());
                output.accept(Moditems.BRONZEGREATSWORD.get());
                output.accept(Moditems.BRONZEPICKAXE.get());

                output.accept(Moditems.LSTEELEBATTLEAXE.get());
                output.accept(Moditems.LSTEELEGREATSWORD.get());
                output.accept(Moditems.LSTEELPICKAXE.get());

                output.accept(Moditems.HSTEELAXE.get());
                output.accept(Moditems.HSTEELBATTLEAXE.get());
                output.accept(Moditems.HSTEELGREATSWORD.get());
                output.accept(Moditems.HSTEELPICKAXE.get());
                output.accept(Moditems.HSTEELSHOVEL.get());

                output.accept(Moditems.SUPERALLOYAXE.get());
                output.accept(Moditems.SUPERALLOYBATTLEAXE.get());
                output.accept(Moditems.SUPERALLOYGREATSWORD.get());
                output.accept(Moditems.SUPERALLOYPICKAXE.get());
                output.accept(Moditems.SUPERALLOYSHOVEL.get());
                output.accept(Moditems.BRASSBLADE.get());
                output.accept(Moditems.IRONBLADE.get());
                output.accept(Moditems.COPPERBLADE.get());
                output.accept(Moditems.STONEBLADE.get());
                output.accept(Moditems.HSTEELBLADE.get());
                output.accept(Moditems.LSTEELBLADE.get());
                output.accept(Moditems.BRONZEBLADE.get());
                output.accept(Moditems.SUPERALLOYBLADE.get());
                output.accept(Moditems.ULTRADENSECOAL.get());
                output.accept(Moditems.COMPRESSEDCOAL.get());
                output.accept(Moditems.WOODBLADE.get());
                output.accept(Moditems.COPPERAXE.get());
                output.accept(Moditems.BRONZEAXE.get());
                output.accept(Moditems.LSTEELAXE.get());
                output.accept(Moditems.IRONHOE.get());
                output.accept(Moditems.STONEHOE.get());
                output.accept(Moditems.IRONHELMET.get());
                output.accept(Moditems.IRONBOOTS.get());
                output.accept(Moditems.IRONLEGGINGS.get());
                output.accept(Moditems.IRONCHESTPLATE.get());
                output.accept(Moditems.COPPERBOOTS.get());
                output.accept(Moditems.COPPERHELMET.get());
                output.accept(Moditems.COPPERLEGGINGS.get());
                output.accept(Moditems.COPPERCHESTPLATE.get());
                output.accept(Moditems.BRASSBOOTS.get());
                output.accept(Moditems.BRASSLEGGINGS.get());
                output.accept(Moditems.BRASSCHESTPLATE.get());
                output.accept(Moditems.BRASSHELMET.get());
                output.accept(Moditems.BRONZEBOOTS.get());
                output.accept(Moditems.BRONZEHELMET.get());
                output.accept(Moditems.BRONZECHESTPLATE.get());
                output.accept(Moditems.BRONZELEGGINGS.get());
                output.accept(Moditems.LSTEELBOOTS.get());
                output.accept(Moditems.LSTEELHELMET.get());
                output.accept(Moditems.LSTEELCHESTPLATE.get());
                output.accept(Moditems.LSTEELLEGGINGS.get());
                output.accept(Moditems.HSTEELBOOTS.get());
                output.accept(Moditems.HSTEELCHESTPLATE.get());
                output.accept(Moditems.HSTEELLEGGINGS.get());
                output.accept(Moditems.HSTEELHELMET.get());
                output.accept(Moditems.SUPERALLOYBOOTS.get());
                output.accept(Moditems.SUPERALLOYLEGGINGS.get());
                output.accept(Moditems.SUPERALLOYHELMET.get());
                output.accept(Moditems.SUPERALLOYCHESTPLATE.get());
                output.accept(Moditems.IRONPLATE.get());
                output.accept(Moditems.COPPERPLATE.get());
                output.accept(Moditems.BRASSPLATE.get());
                output.accept(Moditems.BRONZEPLATE.get());
                output.accept(Moditems.LSTEELPLATE.get());
                output.accept(Moditems.HSTEELPLATE.get());
                output.accept(Moditems.SUPERALLOYPLATE.get());





                
                //BLOCKS ->


                output.accept(ModBlocks.MARBLE.get());
                output.accept(ModBlocks.ROCK.get());
                output.accept(ModBlocks.BASALT.get());
                output.accept(ModBlocks.ALABASTER.get());
                output.accept(ModBlocks.LIMESTONE.get());
                output.accept(ModBlocks.GRANITE.get());
                output.accept(ModBlocks.IRONORE.get());
                output.accept(ModBlocks.TINORE.get());
                output.accept(ModBlocks.COBALTORE.get());
                output.accept(ModBlocks.ALUMINUMORE.get());
                output.accept(ModBlocks.CHROMIUMORE.get());
                output.accept(ModBlocks.NICKELORE.get());
                output.accept(ModBlocks.PLATINUMORE.get());
                output.accept(ModBlocks.SILVERORE.get());
                output.accept(ModBlocks.ZINCORE.get());
                output.accept(ModBlocks.GOLDORE.get());
                output.accept(ModBlocks.COPPERORE.get());
                output.accept(ModBlocks.COBALTORE.get());
                output.accept(ModBlocks.SUPERMARBLE.get());
                output.accept(ModBlocks.COALORE.get());
                output.accept(ModBlocks.DIRT.get());
                output.accept(ModBlocks.SAND.get());






            }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
