package Roma.menu;


import Roma.menu.skillmenu.SkillMenu;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "rma", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KeyInputHandler {



    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            // Check if our key was pressed while playing the game
            while (ModKeybindings.OPEN_MENU_KEY.consumeClick()) {
                Minecraft mc = Minecraft.getInstance();
                if (mc.player != null && mc.screen == null) {

                    // 1. Tell vanilla Minecraft: "Push the latest server stat file to my client screen now!"
                    mc.getConnection().send(new net.minecraft.network.protocol.game.ServerboundClientCommandPacket(
                            net.minecraft.network.protocol.game.ServerboundClientCommandPacket.Action.REQUEST_STATS
                    ));

                    // 2. Open your GUI
                    mc.setScreen(new SkillMenu());
                }else{
                    mc.setScreen(null);
                }
            }
        }
    }
}