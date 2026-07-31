package Roma.entity.custom.renderer;

import Roma.entity.custom.boss.PersianShawman;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class PersianShawmanRenderer extends MobRenderer<PersianShawman, HumanoidModel<PersianShawman>> {

    public PersianShawmanRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    protected void scale(PersianShawman entity, PoseStack poseStack, float partialTickTime) {
        float scaleFactor = 4.0f; // 4x size
        poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
        super.scale(entity, poseStack, partialTickTime);
    }


    @Override
    public ResourceLocation getTextureLocation(PersianShawman entity) {
        return ResourceLocation.fromNamespaceAndPath("rma", "textures/entity/persianshawman.png");
    }
}
