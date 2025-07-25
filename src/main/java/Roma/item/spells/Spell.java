package Roma.item.spells;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class Spell {
    protected final String name;
    protected final int manaCost;
    protected final int cooldown; // ticks
    protected final SpellType type;

    public Spell(String name, int manaCost, int cooldown, SpellType type) {
        this.name = name;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.type = type;
    }

    // Abstract method that each spell must implement
    public abstract boolean cast(Level level, Player player, InteractionHand hand);

    // Check if player can cast this spell
    public boolean canCast(Player player) {
        // Check mana (you'll need your own mana system)
        if (!hasSufficientMana(player)) return false;

        // Check cooldown
        if (isOnCooldown(player)) return false;

        return true;
    }

    // Helper methods
    protected abstract boolean hasSufficientMana(Player player);
    protected abstract boolean isOnCooldown(Player player);
    protected abstract void consumeMana(Player player);
    protected abstract void applyCooldown(Player player);

    // Getters
    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public int getCooldown() { return cooldown; }
    public SpellType getType() { return type; }
}

