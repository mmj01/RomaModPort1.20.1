package Roma.magic;

import Roma.magic.config.ManaConfig;
import net.minecraft.nbt.CompoundTag;

public class ManaImplementation implements IMana {

    // Track the BONUSES earned from items separately from the config
    private int bonusMaxMana = 0;
    private int bonusManaRegenRate = 0;
    private int bonusManaRegenTime = 0; // Usually negative (since cookies reduce delay)
    private int bonusMagicDamage = 0;

    private int skillBonusMaxMana = 0;

    // Current mana is still tracked normally
    private int mana;

    public ManaImplementation() {
        this.mana = getMaxMana();
    }

    // --- GETTERS (Base Config + Player's Earned Bonus) ---

    @Override
    public int getMaxMana() {
        return ManaConfig.getDefaultMaxMana() + this.bonusMaxMana;
    }

    @Override
    public int getManaRegenRate() {
        return Math.max(1, ManaConfig.getManaRegenRate() + this.bonusManaRegenRate);
    }

    @Override
    public int getManaRegenTime() {
        // Delay cannot drop below 1 tick
        return Math.max(1, ManaConfig.getManaRegenDelay() + this.bonusManaRegenTime);
    }

    @Override
    public int getMagicDamage() {
        return Math.max(1, ManaConfig.getMagicDamage() + this.bonusMagicDamage);
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    // --- SETTERS (Calculate the difference to find the new Bonus) ---

    @Override
    public void setMaxMana(int newMaxMana) {
        // If a cookie sets max mana to 120, and base is 100, the bonus becomes 20.
        this.bonusMaxMana = newMaxMana - ManaConfig.getDefaultMaxMana();
        // Clamp current mana so it doesn't exceed the new max
        this.mana = Math.min(this.mana, getMaxMana());
    }

    @Override
    public void setManaRegenRate(int newRate) {
        this.bonusManaRegenRate = newRate - ManaConfig.getManaRegenRate();
    }

    @Override
    public void setManaRegenTime(int newTime) {
        this.bonusManaRegenTime = newTime - ManaConfig.getManaRegenDelay();
    }

    @Override
    public void setMagicDamage(int newDamage) {
        this.bonusMagicDamage = newDamage - ManaConfig.getMagicDamage();
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, getMaxMana()));
    }

    // --- MANA UTILITIES ---

    @Override
    public void addMana(int amount) {
        setMana(this.mana + amount);
    }

    @Override
    public void consumeMana(int amount) {
        setMana(this.mana - amount);
    }

    @Override
    public boolean canConsumeMana(int amount) {
        return this.mana >= amount;
    }

    @Override
    public void regenerateMana(int amount) {
        this.addMana(amount);
    }

    @Override
    public float getManaPercentage() {
        int max = getMaxMana();
        return max > 0 ? (float) this.mana / max : 0f;
    }

    // NEW SETTER FOR THE SKILL BONUS
    @Override
    public void setSkillBonusMaxMana(int bonus) {
        this.skillBonusMaxMana = bonus;
    }

    // --- NBT SAVE / LOAD (Only saving the bonuses!) ---

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("mana", this.mana);
        nbt.putInt("bonusMaxMana", this.bonusMaxMana);
        nbt.putInt("bonusManaRegenRate", this.bonusManaRegenRate);
        nbt.putInt("bonusManaRegenTime", this.bonusManaRegenTime);
        nbt.putInt("bonusMagicDamage", this.bonusMagicDamage);
    }

    public void loadNBTData(CompoundTag nbt) {
        if (nbt.contains("bonusMaxMana")) {
            this.bonusMaxMana = nbt.getInt("bonusMaxMana");
        }
        if (nbt.contains("bonusManaRegenRate")) {
            this.bonusManaRegenRate = nbt.getInt("bonusManaRegenRate");
        }
        if (nbt.contains("bonusManaRegenTime")) {
            this.bonusManaRegenTime = nbt.getInt("bonusManaRegenTime");
        }
        if (nbt.contains("bonusMagicDamage")) {
            this.bonusMagicDamage = nbt.getInt("bonusMagicDamage");
        }

        // Load current mana last, falling back to max if it's missing
        if (nbt.contains("mana")) {
            this.mana = nbt.getInt("mana");
        } else {
            this.mana = getMaxMana();
        }

        // Final safety clamp
        if (this.mana > getMaxMana()) {
            this.mana = getMaxMana();
        }
    }

    // --- CLONING (Death/Dimension Travel) ---

    public void copyFrom(ManaImplementation source) {
        // Only copy the bonuses over.
        // We do NOT copy the base values, because getters already read the live config!
        this.bonusMaxMana = source.bonusMaxMana;
        this.bonusManaRegenRate = source.bonusManaRegenRate;
        this.bonusManaRegenTime = source.bonusManaRegenTime;
        this.bonusMagicDamage = source.bonusMagicDamage;

        // Decide if players respawn with full mana or the mana they died with:
        // (Currently, this copies the exact mana they had when they died)
        this.mana = source.mana;
    }
}