package Roma.client;

import Roma.menu.stats.ModStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.stats.Stats;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = "rma", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientStatsInjector {

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        // Check if the opened screen is Minecraft's native StatsScreen
        if (event.getScreen() instanceof StatsScreen statsScreen) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && mc.player.getStats() != null) {

                // 1. Fetch all four custom stat values from the player's local counter
                int magicUsedValue = mc.player.getStats().getValue(Stats.CUSTOM.get(ModStats.MAGIC_USED.get()));
                int mobsKilledValue = mc.player.getStats().getValue(Stats.CUSTOM.get(ModStats.CUSTOM_MOBS_KILLED.get()));
                int plantsBrokenValue = mc.player.getStats().getValue(Stats.CUSTOM.get(ModStats.CUSTOM_PLANTS_BROKEN.get()));
                int xpMinedValue = mc.player.getStats().getValue(Stats.CUSTOM.get(ModStats.XP_MINED.get()));

                // 2. Log all four stats to console for debugging/verification
                System.out.println("=== Roma Mod Custom Stats ===");
                System.out.println("Magic Used: " + magicUsedValue);
                System.out.println("Custom Mobs Killed: " + mobsKilledValue);
                System.out.println("Custom Plants Broken: " + plantsBrokenValue);
                System.out.println("XP Mined: " + xpMinedValue);
                System.out.println("=============================");

                // Note: To cleanly render custom rows inside the vanilla StatsScreen list without rewriting
                // the entire inner GeneralStatsList class, many modders append custom entries
                // or opt to use a standalone custom GUI (like your SkillMenu) which reads
                // player.getStats() directly—giving you 100% control over the UI design!
            }
        }
    }
}