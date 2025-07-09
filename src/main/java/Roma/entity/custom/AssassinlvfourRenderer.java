package Roma.entity.custom;

import Roma.entity.Modentities;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AssassinlvfourRenderer extends MobRenderer<PersianAssassinlvlfour, HumanoidModel<PersianAssassinlvlfour>> {
    public AssassinlvfourRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));

    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Modentities.PERSIANASSASSINLVLFOUR.get(), AssassinlvfourRenderer::new);

    }

    @Override
    public ResourceLocation getTextureLocation(PersianAssassinlvlfour entity) {
        return ResourceLocation.fromNamespaceAndPath("rma", "textures/entity/persianassassinlvlfour.png");
    }
}
