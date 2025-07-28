package Roma.magic;

import Roma.magic.config.ManaConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// CRITICAL FIX: Added Dist.CLIENT to make this client-only
@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ManaHUD {
    private static final int BAR_WIDTH = 82;
    private static final int BAR_HEIGHT = 5;
    private static final int BAR_BACKGROUND = 0xFF222222;
    private static final int BAR_BORDER = 0xFF000000;

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        // Use safe getter method instead of direct config access
        if (!ManaConfig.isShowManaHUD() || event.getOverlay() != VanillaGuiOverlay.FOOD_LEVEL.type()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                GuiGraphics graphics = event.getGuiGraphics();
                int screenWidth = event.getWindow().getGuiScaledWidth();
                int screenHeight = event.getWindow().getGuiScaledHeight();

                // Position with configurable offsets using safe getters
                int x = screenWidth / 2 - BAR_WIDTH / 2 + ManaConfig.getHudOffsetX();
                int y = screenHeight - 39 - BAR_HEIGHT - 2 + ManaConfig.getHudOffsetY();

                // Calculate mana bar fill
                float manaPercentage = mana.getManaPercentage();
                int fillWidth = (int) (BAR_WIDTH * manaPercentage);

                // Render background
                graphics.fill(x - 1, y - 1, x + BAR_WIDTH + 1, y + BAR_HEIGHT + 1, BAR_BORDER);
                graphics.fill(x, y, x + BAR_WIDTH, y + BAR_HEIGHT, BAR_BACKGROUND);

                // Render mana fill with configurable color using safe getter
                if (fillWidth > 0) {
                    graphics.fill(x, y, x + fillWidth, y + BAR_HEIGHT, ManaConfig.getManaBarColor());
                }

                // Render mana text
                String manaText = mana.getMana() + "/" + mana.getMaxMana();
                int textWidth = mc.font.width(manaText);
                graphics.drawString(mc.font, manaText, x + BAR_WIDTH / 2 - textWidth / 2, y - 10, 0xFFFFFF);
            });
        }
    }
}