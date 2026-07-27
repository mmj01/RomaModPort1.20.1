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

    public static final RegistryObject<CreativeModeTab> ROMA_TOOLS = CREATIVE_MODE_TABS.register("roma_tools",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.IRONBATTLEAXE.get()))
                    .title(Component.translatable("Roma Tools"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // --- TOOLS & WEAPONS ---
                        output.accept(Moditems.CHISEL.get());
                        output.accept(Moditems.SCYTHE.get());

                        // Wood Tier
                        output.accept(Moditems.WOODPICKAXE.get());
                        output.accept(Moditems.DENSEWOODPICKAXE.get());
                        output.accept(Moditems.WOODAXE.get());
                        output.accept(Moditems.WOODSHOVEL.get());
                        output.accept(Moditems.WOODBATTLEAXE.get());
                        output.accept(Moditems.WOODGREATSWORD.get());
                        output.accept(Moditems.WOODFISHINGROD.get());

                        // Stone Tier
                        output.accept(Moditems.STONEPICKAXE.get());
                        output.accept(Moditems.DENSESTONEPICKAXE.get());
                        output.accept(Moditems.STONEAXE.get());
                        output.accept(Moditems.STONESHOVEL.get());
                        output.accept(Moditems.STONEHOE.get());
                        output.accept(Moditems.STONEBATTLEAXE.get());
                        output.accept(Moditems.STONEGREATSWORD.get());

                        // Copper Tier
                        output.accept(Moditems.COPPERPICKAXE.get());
                        output.accept(Moditems.DENSECOPPERPICKAXE.get());
                        output.accept(Moditems.COPPERAXE.get());
                        output.accept(Moditems.COPPERBATTLEAXE.get());
                        output.accept(Moditems.COPPERGREATSWORD.get());

                        // Iron Tier
                        output.accept(Moditems.IRONPICKAXE.get());
                        output.accept(Moditems.DENSEIRONPICKAXE.get());
                        output.accept(Moditems.IRONAXE.get());
                        output.accept(Moditems.IRONSHOVEL.get());
                        output.accept(Moditems.IRONHOE.get());
                        output.accept(Moditems.IRONBATTLEAXE.get());
                        output.accept(Moditems.IRONGREATSWORD.get());
                        output.accept(Moditems.IRONFISHINGROD.get());

                        // Brass Tier
                        output.accept(Moditems.BRASSPICKAXE.get());
                        output.accept(Moditems.DENSEBRASSPICKAXE.get());
                        output.accept(Moditems.BRASSAXE.get());
                        output.accept(Moditems.BRASSSHOVEL.get());
                        output.accept(Moditems.BRASSBATTLEAXE.get());
                        output.accept(Moditems.BRASSGREATSWORD.get());
                        output.accept(Moditems.BRASSFISHINGROD.get());

                        // Bronze Tier
                        output.accept(Moditems.BRONZEPICKAXE.get());
                        output.accept(Moditems.DENSEBRONZEPICKAXE.get());
                        output.accept(Moditems.BRONZEAXE.get());
                        output.accept(Moditems.BRONZEBATTLEAXE.get());
                        output.accept(Moditems.BRONZEGREATSWORD.get());

                        // Low Steel Tier
                        output.accept(Moditems.LSTEELPICKAXE.get());
                        output.accept(Moditems.DENSELSTEELPICKAXE.get());
                        output.accept(Moditems.LSTEELAXE.get());
                        output.accept(Moditems.LSTEELEBATTLEAXE.get());
                        output.accept(Moditems.LSTEELEGREATSWORD.get());

                        // High Steel Tier
                        output.accept(Moditems.HSTEELPICKAXE.get());
                        output.accept(Moditems.DENSEHSTEELPICKAXE.get());
                        output.accept(Moditems.HSTEELAXE.get());
                        output.accept(Moditems.HSTEELSHOVEL.get());
                        output.accept(Moditems.HSTEELBATTLEAXE.get());
                        output.accept(Moditems.HSTEELGREATSWORD.get());
                        output.accept(Moditems.HSTEELFISHINGROD.get());

                        // Superalloy Tier
                        output.accept(Moditems.SUPERALLOYPICKAXE.get());
                        output.accept(Moditems.DENSESUPERALLOYPICKAXE.get());
                        output.accept(Moditems.SUPERALLOYAXE.get());
                        output.accept(Moditems.SUPERALLOYSHOVEL.get());
                        output.accept(Moditems.SUPERALLOYBATTLEAXE.get());
                        output.accept(Moditems.SUPERALLOYGREATSWORD.get());
                        output.accept(Moditems.SUPERALLOYFISHINGROD.get());

                        // --- ARMOR SETS ---
                        output.accept(Moditems.COPPERHELMET.get());
                        output.accept(Moditems.COPPERCHESTPLATE.get());
                        output.accept(Moditems.COPPERLEGGINGS.get());
                        output.accept(Moditems.COPPERBOOTS.get());

                        output.accept(Moditems.IRONHELMET.get());
                        output.accept(Moditems.IRONCHESTPLATE.get());
                        output.accept(Moditems.IRONLEGGINGS.get());
                        output.accept(Moditems.IRONBOOTS.get());

                        output.accept(Moditems.BRASSHELMET.get());
                        output.accept(Moditems.BRASSCHESTPLATE.get());
                        output.accept(Moditems.BRASSLEGGINGS.get());
                        output.accept(Moditems.BRASSBOOTS.get());

                        output.accept(Moditems.BRONZEHELMET.get());
                        output.accept(Moditems.BRONZECHESTPLATE.get());
                        output.accept(Moditems.BRONZELEGGINGS.get());
                        output.accept(Moditems.BRONZEBOOTS.get());

                        output.accept(Moditems.LSTEELHELMET.get());
                        output.accept(Moditems.LSTEELCHESTPLATE.get());
                        output.accept(Moditems.LSTEELLEGGINGS.get());
                        output.accept(Moditems.LSTEELBOOTS.get());

                        output.accept(Moditems.HSTEELHELMET.get());
                        output.accept(Moditems.HSTEELCHESTPLATE.get());
                        output.accept(Moditems.HSTEELLEGGINGS.get());
                        output.accept(Moditems.HSTEELBOOTS.get());

                        output.accept(Moditems.SUPERALLOYHELMET.get());
                        output.accept(Moditems.SUPERALLOYCHESTPLATE.get());
                        output.accept(Moditems.SUPERALLOYLEGGINGS.get());
                        output.accept(Moditems.SUPERALLOYBOOTS.get());


                    }).build());

    public static final RegistryObject<CreativeModeTab> ROMA_FOOD = CREATIVE_MODE_TABS.register("roma_food",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.BREAD.get()))
                    .title(Component.translatable("Roma Food"))
                    .displayItems((itemDisplayParameters, output) -> {


                        // --- CROPS & BASIC FOODS ---
                        output.accept(Moditems.WHEATSEEDS.get());
                        output.accept(Moditems.WHEAT.get());
                        output.accept(Moditems.POTATO.get());
                        output.accept(Moditems.CARROT.get());
                        output.accept(Moditems.STRING.get());
                        output.accept(Moditems.BREAD.get());
                        output.accept(Moditems.BAKEDPOTATO.get());
                        output.accept(Moditems.BOILEDCARROT.get());
                        output.accept(Moditems.SMOKEDHAM.get());
                        output.accept(Moditems.ROASTEDSTEAK.get());
                        output.accept(Moditems.GRILLEDCHICKEN.get());

                        // --- RAW & COOKED FISH ---
                        output.accept(Moditems.COD.get());
                        output.accept(Moditems.GRILLEDCOD.get());
                        output.accept(Moditems.SALMON.get());
                        output.accept(Moditems.GRILLEDSALMON.get());
                        output.accept(Moditems.CARP.get());
                        output.accept(Moditems.GRILLEDCARP.get());
                        output.accept(Moditems.TROUT.get());
                        output.accept(Moditems.GRILLEDTROUT.get());
                        output.accept(Moditems.GUPPY.get());
                        output.accept(Moditems.GRILLEDGUPPY.get());
                        output.accept(Moditems.BLUEGILL.get());
                        output.accept(Moditems.GRILLEDBLUEGILL.get());
                        output.accept(Moditems.CATFISH.get());
                        output.accept(Moditems.GRILLEDCATFISH.get());
                        output.accept(Moditems.BASS.get());
                        output.accept(Moditems.GRILLEDBASS.get());
                        output.accept(Moditems.MARLIN.get());
                        output.accept(Moditems.GRILLEDMARLIN.get());
                        output.accept(Moditems.SHARK.get());
                        output.accept(Moditems.GRILLEDSHARK.get());

                        // --- SANDWICHES ---
                        output.accept(Moditems.SANDWICH.get());
                        output.accept(Moditems.CODSANDWICH.get());
                        output.accept(Moditems.SALMONSANDWICH.get());
                        output.accept(Moditems.CARPSANDWICH.get());
                        output.accept(Moditems.TROUTSANDWICH.get());
                        output.accept(Moditems.GUPPYSANDWICH.get());
                        output.accept(Moditems.BLUEGILLSANDWICH.get());
                        output.accept(Moditems.CATFISHSANDWICH.get());
                        output.accept(Moditems.BASSSANDWICH.get());
                        output.accept(Moditems.MARLINSANDWICH.get());
                        output.accept(Moditems.SHARKSANDWICH.get());
                        output.accept(Moditems.HAMSANDWICH.get());
                        output.accept(Moditems.STEAKSANDWICH.get());
                        output.accept(Moditems.CHICKENSANDWICH.get());

                    }).build());

    public static final RegistryObject<CreativeModeTab> ROMA_ITEMS = CREATIVE_MODE_TABS.register("roma_items",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.SILVERINGOT.get()))
                    .title(Component.translatable("Roma Items"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // --- RAW ORES & FUELS ---
                        output.accept(Moditems.RAWIRON.get());
                        output.accept(Moditems.RAWCOPPER.get());
                        output.accept(Moditems.RAWGOLD.get());
                        output.accept(Moditems.RAWALUMINUM.get());
                        output.accept(Moditems.RAWCHROMIUM.get());
                        output.accept(Moditems.RAWCOBALT.get());
                        output.accept(Moditems.RAWNICKEL.get());
                        output.accept(Moditems.RAWZINC.get());
                        output.accept(Moditems.RAWTIN.get());
                        output.accept(Moditems.RAWSILVER.get());
                        output.accept(Moditems.RAWPLATINUM.get());
                        output.accept(Moditems.COAL.get());
                        output.accept(Moditems.COMPRESSEDCOAL.get());
                        output.accept(Moditems.ULTRADENSECOAL.get());

                        // --- INGOTS, ALLOYS & AMALGAMS ---
                        output.accept(Moditems.IRONINGOT.get());
                        output.accept(Moditems.TREATEDIRONINGOT.get());
                        output.accept(Moditems.COPPERINGOT.get());
                        output.accept(Moditems.GOLDINGOT.get());
                        output.accept(Moditems.ALUMINUMINGOT.get());
                        output.accept(Moditems.CHROMIUMINGOT.get());
                        output.accept(Moditems.COBALTINGOT.get());
                        output.accept(Moditems.NICKELINGOT.get());
                        output.accept(Moditems.ZINCINGOT.get());
                        output.accept(Moditems.TININGOT.get());
                        output.accept(Moditems.SILVERINGOT.get());
                        output.accept(Moditems.PLATINUMINGOT.get());
                        output.accept(Moditems.BRONZEINGOT.get());
                        output.accept(Moditems.BRASSINGOT.get());
                        output.accept(Moditems.LSTEELINGOT.get());
                        output.accept(Moditems.HSTEELINGOT.get());
                        output.accept(Moditems.SUPERALLOYINGOT.get());
                        output.accept(Moditems.ALLOYMIXER.get());
                        output.accept(Moditems.BRONZEALLOYAMALGAM.get());
                        output.accept(Moditems.BRASSALLOYAMALGAM.get());
                        output.accept(Moditems.SUPERALLOYAMALGAM.get());
                        output.accept(Moditems.MARBLEVENEER.get());

                        // --- PLATES ---
                        output.accept(Moditems.COPPERPLATE.get());
                        output.accept(Moditems.IRONPLATE.get());
                        output.accept(Moditems.BRASSPLATE.get());
                        output.accept(Moditems.BRONZEPLATE.get());
                        output.accept(Moditems.LSTEELPLATE.get());
                        output.accept(Moditems.HSTEELPLATE.get());
                        output.accept(Moditems.SUPERALLOYPLATE.get());

                        // --- BLADES ---
                        output.accept(Moditems.WOODBLADE.get());
                        output.accept(Moditems.STONEBLADE.get());
                        output.accept(Moditems.COPPERBLADE.get());
                        output.accept(Moditems.IRONBLADE.get());
                        output.accept(Moditems.BRASSBLADE.get());
                        output.accept(Moditems.BRONZEBLADE.get());
                        output.accept(Moditems.LSTEELBLADE.get());
                        output.accept(Moditems.HSTEELBLADE.get());
                        output.accept(Moditems.SUPERALLOYBLADE.get());

                        // --- COINS & ECONOMY ---
                        output.accept(Moditems.COPPERCOIN.get());
                        output.accept(Moditems.COPPERCOINS.get());
                        output.accept(Moditems.SILVERCOIN.get());
                        output.accept(Moditems.SILVERCOINS.get());
                        output.accept(Moditems.GOLDCOIN.get());
                        output.accept(Moditems.GOLDCOINS.get());
                        output.accept(Moditems.PLATINUMCOIN.get());
                        output.accept(Moditems.PLATINUMCOINS.get());



                    }).build());

    public static final RegistryObject<CreativeModeTab> ROMA_BLOCKS = CREATIVE_MODE_TABS.register("roma_blocks",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.ROCK.get()))
                    .title(Component.translatable("Roma Blocks"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // --- WOOD & TREES ---
                        output.accept(ModBlocks.CYPRESSLOG.get());
                        output.accept(ModBlocks.CYPRESSWOOD.get());
                        output.accept(ModBlocks.STRIPPEDCYPRESSLOG.get());
                        output.accept(ModBlocks.STRIPPEDCYPRESSWOOD.get());
                        output.accept(ModBlocks.CYPRESSPLANKS.get());
                        output.accept(ModBlocks.CYPRESSSAPLING.get());
                        output.accept(ModBlocks.CYPRESSLEAVES.get());

                        // --- STONES & BUILDING BLOCKS ---
                        output.accept(ModBlocks.DIRT.get());
                        output.accept(ModBlocks.SAND.get());
                        output.accept(ModBlocks.ROCK.get());
                        output.accept(ModBlocks.GRANITE.get());
                        output.accept(ModBlocks.LIMESTONE.get());
                        output.accept(ModBlocks.BASALT.get());
                        output.accept(ModBlocks.ALABASTER.get());
                        output.accept(ModBlocks.MARBLE.get());
                        output.accept(ModBlocks.SUPERMARBLE.get());

                        // --- ORE BLOCKS ---
                        output.accept(ModBlocks.COALORE.get());
                        output.accept(ModBlocks.IRONORE.get());
                        output.accept(ModBlocks.COPPERORE.get());
                        output.accept(ModBlocks.GOLDORE.get());
                        output.accept(ModBlocks.ALUMINUMORE.get());
                        output.accept(ModBlocks.CHROMIUMORE.get());
                        output.accept(ModBlocks.COBALTORE.get());
                        output.accept(ModBlocks.NICKELORE.get());
                        output.accept(ModBlocks.ZINCORE.get());
                        output.accept(ModBlocks.TINORE.get());
                        output.accept(ModBlocks.SILVERORE.get());
                        output.accept(ModBlocks.PLATINUMORE.get());

                        // --- TECHNICAL/SPECIAL BLOCKS ---
                        output.accept(Moditems.BARRIER_ITEM.get());

                    }).build());

    public static final RegistryObject<CreativeModeTab> ROMA_MAGIC = CREATIVE_MODE_TABS.register("roma_magic",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Moditems.ICEPRISONSPELL.get()))
                    .title(Component.translatable("Roma Magic"))
                    .displayItems((itemDisplayParameters, output) -> {

                        // --- BOSS SUMMONS ---
                        output.accept(Moditems.SHAWMANSUMMON.get());

                        // --- SPELL SCROLLS ---
                        output.accept(Moditems.LIGHTNINGSPELL.get());
                        output.accept(Moditems.CHAINLIGHTINGSPELL.get());
                        output.accept(Moditems.ICEPRISONSPELL.get());
                        output.accept(Moditems.GROWTHSPELL.get());
                        output.accept(Moditems.MENDINGSPELL.get());
                        output.accept(Moditems.RETURNSPELL.get());
                        output.accept(Moditems.TRAVELSPELL.get());

                        // --- MAGIC COOKIES ---
                        output.accept(Moditems.MANACOOKIE.get());
                        output.accept(Moditems.MANARESETCOOKIE.get());
                        output.accept(Moditems.MANAREGENCOOKIE.get());

                        // --- SKILLS ---
                        output.accept(Moditems.JUMPSKILL.get());
                        output.accept(Moditems.REGENSKILL.get());
                        output.accept(Moditems.STRENGTHSKILL.get());
                        output.accept(Moditems.HASTESKILL.get());

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}