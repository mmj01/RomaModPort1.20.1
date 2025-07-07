package Roma.entity.custom;

import Roma.entity.Modentities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AssassinlvtwoRenderer extends MobRenderer<PersianAssassinlvltwo, HumanoidModel<PersianAssassinlvltwo>> {
    public AssassinlvtwoRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Modentities.PERSIANASSASSINLVLTWO.get(), AssassinlvtwoRenderer::new);
    }

    @Override
    public ResourceLocation getTextureLocation(PersianAssassinlvltwo entity) {
        return ResourceLocation.fromNamespaceAndPath("rma", "textures/entity/persianassassinlvltwo.png");
    }
}
