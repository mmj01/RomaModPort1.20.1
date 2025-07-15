package Roma.villager;


import Roma.item.Moditems;
import Roma.roma;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.List;


@Mod.EventBusSubscriber(modid = roma.MOD_ID)
public class VillagerEvents {

        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event) {
            if(event.getType() == VillagerProfession.FLETCHER) {
                event.getTrades().get(1).clear();
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();



                event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOIN.get(), 6),
                            new ItemStack(Moditems.SILVERCOIN.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOIN.get(), 6),
                            new ItemStack(Moditems.GOLDCOIN.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.GOLDCOIN.get(), 6),
                            new ItemStack(Moditems.PLATINUMCOIN.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOIN.get(), 10),
                            new ItemStack(Moditems.WHEATSEEDS.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOIN.get(), 30),
                            new ItemStack(Moditems.WHEAT.get()),
                            1000000, 0, 0.0F
                    ));event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOINS.get(), 7),
                            new ItemStack(Moditems.BREAD.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 30),
                            new ItemStack(Moditems.BRASSINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 60),
                            new ItemStack(Moditems.BRONZEINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.GOLDCOINS.get(), 60),
                            new ItemStack(Moditems.LSTEELINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.GOLDCOINS.get(), 24),
                            new ItemStack(Moditems.HSTEELINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.PLATINUMCOINS.get(), 16),
                            new ItemStack(Moditems.SUPERALLOYINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 3),
                            new ItemStack(Moditems.TININGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 3),
                            new ItemStack(Moditems.ZINCINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 6),
                            new ItemStack(Moditems.ALUMINUMINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 9),
                            new ItemStack(Moditems.COBALTINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 12),
                            new ItemStack(Moditems.NICKELINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.PLATINUMCOINS.get(), 64),
                            new ItemStack(Moditems.JUMPSKILL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.PLATINUMCOINS.get(), 64),
                            new ItemStack(Moditems.STRENGTHSKILL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.PLATINUMCOINS.get(), 64),
                            new ItemStack(Moditems.REGENSKILL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.PLATINUMCOINS.get(), 64),
                            new ItemStack(Moditems.HASTESKILL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.SILVERCOINS.get(), 3),
                            new ItemStack(Moditems.IRONINGOT.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOIN.get(), 4),
                            new ItemStack(Moditems.COAL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOINS.get(), 4),
                            new ItemStack(Moditems.COMPRESSEDCOAL.get()),
                            1000000, 0, 0.0F
                    ));
            event.getTrades().get(1).add((entity, random) ->
                    new MerchantOffer(
                            new ItemStack(Moditems.COPPERCOINS.get(), 36),
                            new ItemStack(Moditems.ULTRADENSECOAL.get()),
                            1000000, 0, 0.0F
                    ));



        }
    }
}
