package Roma.entity.custom;

import Roma.entity.Modentities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AssassinlvoneRenderer extends MobRenderer<PersianAssassin, HumanoidModel<PersianAssassin>> {
    public AssassinlvoneRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Modentities.PERSIANASSASSIN.get(), AssassinlvoneRenderer::new);
    }

    @Override
    public ResourceLocation getTextureLocation(PersianAssassin entity) {
        return ResourceLocation.fromNamespaceAndPath("rma", "textures/entity/persianassassinlvlone.png");
    }
}
