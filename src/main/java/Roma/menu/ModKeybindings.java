package Roma.menu;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "rma", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModKeybindings {
    // Defines default key as 'K' inside a custom controls category
    public static final KeyMapping OPEN_MENU_KEY = new KeyMapping(
            "key.rma.open_menu",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_K,
            "key.categories.rma"
    );


    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_MENU_KEY);
    }
}