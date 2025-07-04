package Roma;

import Roma.item.Moditems;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FuelChecker {

    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(Moditems.COMPRESSEDCOAL.get())) {
            event.setBurnTime(450); // Burn time in ticks
        }
        if (event.getItemStack().is(Moditems.COAL.get())) {
            event.setBurnTime(50); // Burn time in ticks
        }
        if (event.getItemStack().is(Moditems.ULTRADENSECOAL.get())) {
            event.setBurnTime(4050); // Burn time in ticks
        }
    }
}
