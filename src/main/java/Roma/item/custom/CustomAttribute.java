package Roma.item.custom;

import Roma.roma;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class CustomAttribute {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, roma.MOD_ID);

    //ATTRIBUTES


    public static final RegistryObject<Attribute> CATTACK_REACH = ATTRIBUTES.register("cattack_reach", () ->  new RangedAttribute("attribute.mod.attack_reach", 5.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> CATTACK_KNOCKBACK = ATTRIBUTES.register("cattack_knockback", () ->  new RangedAttribute("attribute.mod.attack_knockback", 5.0D, 0.0D, 1024.0D).setSyncable(true));

    //MODIFIERS
    public static final UUID CATTACH_REACH_MODIFIER = UUID.fromString("7cc9a781-fdde-4e1b-85fe-acb912fc0430");
    public static final UUID CATTACH_KNOCKBACK_MODIFIER = UUID.fromString("7cc9a781-fdde-4e1b-88fe-acb912fc0430");


    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}


