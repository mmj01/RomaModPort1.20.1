package Roma.enchantment;

import Roma.roma;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(Registries.ENCHANTMENT, roma.MOD_ID);

    public static final RegistryObject<Enchantment> SHARP =
            ENCHANTMENTS.register("sharp", sharp::new);
    public static final RegistryObject<Enchantment> ROBUST =
            ENCHANTMENTS.register("robust", robust::new);
    public static final RegistryObject<Enchantment> HEAVY =
            ENCHANTMENTS.register("heavy", heavy::new);
    public static final RegistryObject<Enchantment> FLEET =
            ENCHANTMENTS.register("fleet", fleet::new);

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }
}
