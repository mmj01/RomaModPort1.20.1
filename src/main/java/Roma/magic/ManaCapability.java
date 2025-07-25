package Roma.magic;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManaCapability {

    // Don't initialize during static loading - use lazy initialization instead
    private static Capability<IMana> MANA_CAPABILITY_INSTANCE;

    public static Capability<IMana> MANA_CAPABILITY() {
        if (MANA_CAPABILITY_INSTANCE == null) {
            MANA_CAPABILITY_INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});
        }
        return MANA_CAPABILITY_INSTANCE;
    }

    // Keep this for backwards compatibility if you have existing code
    public static Capability<IMana> MANA_CAPABILITY = null;

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IMana.class);
        // Initialize the capability after registration
        MANA_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    }
}