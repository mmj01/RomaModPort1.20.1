// Repair Spell - Heals all damaged items in inventory
package Roma.item.spells;

import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RepairSpell extends Spell {
    private float repairPercentage = 0.15f; // 100% repair (full heal)
    private boolean repairAll = true; // Repair all items vs just held item

    public RepairSpell() {
        super("Mending Touch", 100, 0, SpellType.UTILITY); // Higher mana cost, longer cooldown
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        if (!SpellUtil.tryCastSpell(player, manaCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + manaCost + ", have " + currentMana));
            return false;
        }

        try {
            int itemsRepaired = repairPlayerItems(player);

            // Add visual effects
            if (level instanceof ServerLevel serverLevel) {
                addVisualEffects(serverLevel, player.blockPosition());
            }

            // Give feedback to player
            if (itemsRepaired > 0) {
                player.sendSystemMessage(Component.literal("§b✦ Repaired " + itemsRepaired + " items!"));
            } else {
                player.sendSystemMessage(Component.literal("§eNo damaged items found to repair."));
            }

            applyCooldown(player);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int repairPlayerItems(Player player) {
        int itemsRepaired = 0;

        // Repair items in main inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (repairItem(stack)) {
                itemsRepaired++;
            }
        }

        // Repair armor slots
        for (ItemStack armorStack : player.getInventory().armor) {
            if (repairItem(armorStack)) {
                itemsRepaired++;
            }
        }

        // Repair offhand
        ItemStack offhandStack = player.getInventory().offhand.get(0);
        if (repairItem(offhandStack)) {
            itemsRepaired++;
        }

        return itemsRepaired;
    }

    private boolean repairItem(ItemStack stack) {
        if (stack.isEmpty() || !stack.isDamaged()) {
            return false;
        }

        // Check if item is repairable
        if (!stack.isDamageableItem()) {
            return false;
        }

        int currentDamage = stack.getDamageValue();
        int maxDurability = stack.getMaxDamage();

        if (currentDamage <= 0) {
            return false; // Already at full durability
        }

        // Calculate repair amount
        int repairAmount;
        if (repairPercentage >= 1.0f) {
            // Full repair
            repairAmount = currentDamage;
        } else {
            // Partial repair
            repairAmount = Math.max(1, (int)(maxDurability * repairPercentage));
            repairAmount = Math.min(repairAmount, currentDamage);
        }

        // Apply the repair
        stack.setDamageValue(currentDamage - repairAmount);

        return true;
    }

    private void addVisualEffects(ServerLevel level, BlockPos playerPos) {
        // Golden repair sparkles around the player
        for (int i = 0; i < 25; i++) {
            double x = playerPos.getX() + 0.5 + (Math.random() - 0.5) * 3;
            double y = playerPos.getY() + 0.5 + Math.random() * 2;
            double z = playerPos.getZ() + 0.5 + (Math.random() - 0.5) * 3;

            // Mix of golden and enchantment particles
            if (Math.random() < 0.6) {
                level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 2, 0.2, 0.2, 0.2, 0.8);
            } else {
                level.sendParticles(ParticleTypes.CRIT, x, y, z, 1, 0.1, 0.1, 0.1, 0.3);
            }
        }

        // Upward flowing golden particles
        for (int i = 0; i < 15; i++) {
            double x = playerPos.getX() + 0.5 + (Math.random() - 0.5) * 1.5;
            double y = playerPos.getY() + 0.2;
            double z = playerPos.getZ() + 0.5 + (Math.random() - 0.5) * 1.5;

            level.sendParticles(ParticleTypes.INSTANT_EFFECT, x, y, z, 3, 0.1, 0.1, 0.1, 1.2);
        }

        // Ring of particles around player feet
        for (int i = 0; i < 12; i++) {
            double angle = (i / 12.0) * 2 * Math.PI;
            double x = playerPos.getX() + 0.5 + Math.cos(angle) * 1.5;
            double z = playerPos.getZ() + 0.5 + Math.sin(angle) * 1.5;
            double y = playerPos.getY() + 0.1;

            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0.3, 0, 0.05);
        }
    }

    // Variant methods for different repair types
    public void setRepairAmount(float percentage) {
        this.repairPercentage = Math.max(0.1f, Math.min(1.0f, percentage));
    }

    public void setRepairMode(boolean repairAllItems) {
        this.repairAll = repairAllItems;
    }

    // Alternative version that only repairs held item
    private int repairHeldItemOnly(Player player) {
        ItemStack heldItem = player.getMainHandItem();
        if (repairItem(heldItem)) {
            return 1;
        }

        // Try offhand if main hand wasn't repairable
        ItemStack offhandItem = player.getOffhandItem();
        if (repairItem(offhandItem)) {
            return 1;
        }

        return 0;
    }

    // Method to get repair cost based on damage
    private int calculateManaCost(Player player) {
        int totalDamage = 0;

        // Count total damage across all items
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.isDamaged()) {
                totalDamage += stack.getDamageValue();
            }
        }

        // Scale mana cost based on damage (optional advanced feature)
        return Math.max(manaCost, totalDamage / 100);
    }

    // Standard spell methods...
    @Override
    protected boolean hasSufficientMana(Player player) {
        return SpellUtil.hasEnoughMana(player, manaCost);
    }

    @Override
    protected boolean isOnCooldown(Player player) {
        return getCooldownFromPlayer(player, this) > 0;
    }

    @Override
    protected void consumeMana(Player player) {
        // Handled by SpellUtil.tryCastSpell()
    }

    @Override
    protected void applyCooldown(Player player) {
        setCooldownForPlayer(player, this, cooldown);
    }

    private int getCooldownFromPlayer(Player player, Spell spell) {
        return player.getPersistentData().getInt("cooldown_" + spell.getName());
    }

    private void setCooldownForPlayer(Player player, Spell spell, int ticks) {
        player.getPersistentData().putInt("cooldown_" + spell.getName(), ticks);
    }
}