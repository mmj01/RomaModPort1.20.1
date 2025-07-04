package Roma.entity;

import Roma.entity.custom.PersianAssassin;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.MOD)

public class Modentities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, "rma");

    public static final RegistryObject<EntityType<PersianAssassin>> PERSIANASSASSIN =
            ENTITIES.register("persianassassin",
                    () -> EntityType.Builder.of(PersianAssassin::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.95f) // width, height
                            .clientTrackingRange(8)
                            .build("persianassassin"));


    @SubscribeEvent
    public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Modentities.PERSIANASSASSIN.get(), PersianAssassin.createAttributes().build());
    }


}
