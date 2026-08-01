package Roma.menu.stats.quality;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class QualityEffectsEvents {

    /**
     * Helper method to convert the Quality string into a math multiplier.
     */
    private static float getQualityMultiplier(String quality) {
        return switch (quality) {
            case "Masterwork" -> 4.00f;  // +300% boost
            case "Flawless" -> 2.80f;    // +180% boost
            case "Exceptional" -> 1.75f; // +75% boost
            case "Advanced" -> 1.20f;    // +20% boost
            default -> 1.0f;             // Standard (No boost)
        };
    }

    // ==========================================
    // 1. VISUALS: ITEM TOOLTIPS
    // ==========================================
    @SubscribeEvent
    public static void onItemHover(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.hasTag() && stack.getTag().contains("Quality")) {
            String quality = stack.getTag().getString("Quality");

            ChatFormatting color = switch (quality) {
                case "Masterwork" -> ChatFormatting.GOLD;
                case "Flawless" -> ChatFormatting.DARK_PURPLE;
                case "Exceptional" -> ChatFormatting.DARK_GREEN;
                case "Advanced" -> ChatFormatting.DARK_BLUE;
                default -> ChatFormatting.GRAY;
            };

            event.getToolTip().add(1, Component.literal("Quality: " + quality).withStyle(color));
        }
    }

    // ==========================================
    // 4. FISHING: ROD LOOT MULTIPLIER
    // ==========================================
    @SubscribeEvent
    public static void onFishCaught(ItemFishedEvent event) {
        Player player = event.getEntity();
        ItemStack rod = player.getMainHandItem();

        if (rod.hasTag() && rod.getTag().contains("Quality")) {
            String quality = rod.getTag().getString("Quality");
            float multiplier = getQualityMultiplier(quality);

            if (multiplier > 1.0f) {
                // Multiply the stack size of whatever they just caught
                for (ItemStack drop : event.getDrops()) {
                    int newAmount = (int) (drop.getCount() * multiplier);
                    drop.setCount(Math.max(1, newAmount));
                }
            }
        }
    }
}