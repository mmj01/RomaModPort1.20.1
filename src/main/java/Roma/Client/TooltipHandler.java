package Roma.Client;

import Roma.item.Moditems;
import Roma.roma;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;


@Mod.EventBusSubscriber(modid = roma.MOD_ID, value = Dist.CLIENT)
public class TooltipHandler {

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        List<Component> tooltip = event.getToolTip();

        // Remove all attribute lines by filtering out those that start with "In Main Hand" or contain attribute info
        // Adjust these checks if your language or formatting differs
        tooltip.removeIf(line -> {
            String text = line.getString();

            // Usually attribute lines start with "In Main Hand", but you can adjust or add more filters
            return text.startsWith("When in Main Hand")
                    || text.contains("Attack Damage")
                    || text.contains("Attack Speed")
                    || text.contains("Reach")
                    || text.contains("Entity")
                    || text.contains("Attack");

        });

        if (stack.is(Moditems.IRONGREATSWORD.get())) {
            event.getToolTip().add(Component.literal("A heavy blade with extended reach"));
        } else if (stack.is(Moditems.IRONBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.COPPERGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.COPPERBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.WOODGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.WOODBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.BRONZEGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.BRONZEBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.BRASSGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.BRASSBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.LSTEELEGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.LSTEELEBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.HSTEELGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.HSTEELBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.SUPERALLOYGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.SUPERALLOYBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.STONEGREATSWORD.get())) {
            event.getToolTip().add(Component.literal(""));
        } else if (stack.is(Moditems.STONEBATTLEAXE.get())) {
            event.getToolTip().add(Component.literal(""));

        }


    }
}
