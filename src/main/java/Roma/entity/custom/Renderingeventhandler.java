package Roma.entity.custom;

import Roma.entity.Modentities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Renderingeventhandler {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Modentities.PERSIANASSASSIN.get(), AssassinlvoneRenderer::new);
        event.registerEntityRenderer(Modentities.PERSIANASSASSINLVLTWO.get(), AssassinlvtwoRenderer::new);
        event.registerEntityRenderer(Modentities.PERSIANASSASSINLVLTHREE.get(), AssassinlvthreeRenderer::new);
        event.registerEntityRenderer(Modentities.PERSIANASSASSINLVLFOUR.get(), AssassinlvfourRenderer::new);
    }
}
