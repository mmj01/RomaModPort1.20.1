package Roma.entity;

import Roma.entity.custom.PersianAssassin;
import Roma.entity.custom.PersianAssassinlvlfour;
import Roma.entity.custom.PersianAssassinlvlthree;
import Roma.entity.custom.PersianAssassinlvltwo;
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

    public static final RegistryObject<EntityType<PersianAssassinlvltwo>> PERSIANASSASSINLVLTWO =
            ENTITIES.register("persianassassinlvltwo",
                    () -> EntityType.Builder.of(PersianAssassinlvltwo::new, MobCategory.MONSTER)
                            .sized(0.7f, 2.05f) // width, height
                            .clientTrackingRange(8)
                            .build("persianassassinlvltwo"));
    public static final RegistryObject<EntityType<PersianAssassinlvlthree>> PERSIANASSASSINLVLTHREE =
            ENTITIES.register("persianassassinlvlthree",
                    () -> EntityType.Builder.of(PersianAssassinlvlthree::new, MobCategory.MONSTER)
                            .fireImmune()
                            .sized(0.9f, 2.45f) // width, height
                            .clientTrackingRange(8)
                            .build("persianassassinlvlthree"));

    public static final RegistryObject<EntityType<PersianAssassinlvlfour>> PERSIANASSASSINLVLFOUR =
            ENTITIES.register("persianassassinlvlfour",
                    () -> EntityType.Builder.of(PersianAssassinlvlfour::new, MobCategory.MONSTER)
                            .fireImmune()
                            .sized(1.3f, 3.2f) // width, height
                            .clientTrackingRange(8)
                            .build("persianassassinlvlfour"));


    @SubscribeEvent
    public static void onEntityAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Modentities.PERSIANASSASSIN.get(), PersianAssassin.createAttributes().build());
        event.put(Modentities.PERSIANASSASSINLVLTWO.get(), PersianAssassinlvltwo.createAttributes().build());
        event.put(Modentities.PERSIANASSASSINLVLTHREE.get(), PersianAssassinlvlthree.createAttributes().build());
        event.put(Modentities.PERSIANASSASSINLVLFOUR.get(), PersianAssassinlvlfour.createAttributes().build());
    }


}
