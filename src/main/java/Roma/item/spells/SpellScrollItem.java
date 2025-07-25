package Roma.item.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SpellScrollItem extends Item {
    private final Spell spell;

    public SpellScrollItem(Spell spell, Properties properties) {
        super(properties.stacksTo(16));
        this.spell = spell;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            if (spell.cast(level, player, hand)) {
                player.sendSystemMessage(Component.literal("§6Used " + spell.getName() + " scroll!"));

            } else {
                player.sendSystemMessage(Component.literal("§cCannot cast " + spell.getName()));
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§9Spell: §f" + spell.getName()));
        tooltip.add(Component.literal("§7Mana Cost: " + spell.getManaCost()));
        tooltip.add(Component.literal("§7Type: " + spell.getType()));
        tooltip.add(Component.literal("§8Reuseable"));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true; // Always glowing
    }
}