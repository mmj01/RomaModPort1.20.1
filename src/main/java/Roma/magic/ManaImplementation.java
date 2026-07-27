package Roma.magic;

import Roma.magic.config.ManaConfig;
import net.minecraft.nbt.CompoundTag;

public class ManaImplementation implements IMana {
    private int maxMana = ManaConfig.getDefaultMaxMana();
    private int mana = ManaConfig.getDefaultMaxMana();
    private int manaRegenRate = ManaConfig.getManaRegenRate();
    private int manaRegenTime = ManaConfig.getManaRegenDelay();

    public ManaImplementation() {
        // Use the safe getter method instead of direct config access
        this.maxMana = ManaConfig.getDefaultMaxMana();
        this.mana = this.maxMana;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public int getMaxMana() {
        return this.maxMana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, this.maxMana));
    }

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
    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;

    }

    @Override
    public void regenerateMana(int amount) {
        this.addMana(amount);
    }

    @Override
    public float getManaPercentage() {
        return maxMana > 0 ? (float) mana / maxMana : 0f;
    }

    @Override
    public int getManaRegenRate() {
        return this.manaRegenRate;
    }

    @Override
    public void setManaRegenRate(int rate) {

        this.manaRegenRate = Math.max(1, rate);

    }

    @Override
    public int getManaRegenTime() {
        return this.manaRegenTime;
    }

    @Override
    public void setManaRegenTime(int time) {
        this.manaRegenTime = Math.max(1,time);
    }

    // NBT serialization methods
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("mana", mana);
        nbt.putInt("maxMana", maxMana);
        nbt.putInt("manaRegenRate", manaRegenRate);
        nbt.putInt("manaRegenTime", manaRegenTime);
    }

    public void loadNBTData(CompoundTag nbt) {
        mana = nbt.getInt("mana");
        maxMana = nbt.getInt("maxMana");

        // Ensure maxMana has a reasonable default if not found
        if (maxMana <= 0) {
            maxMana = ManaConfig.getDefaultMaxMana();
        }

        if (nbt.contains("manaRegenRate")) {
            manaRegenRate = nbt.getInt("manaRegenRate");
        } else {
            manaRegenRate = ManaConfig.getManaRegenRate();
        }

        if (nbt.contains("manaRegenTime")) {
            manaRegenTime = nbt.getInt("manaRegenTime");
        } else {
            manaRegenTime = ManaConfig.getManaRegenDelay();
        }

        // Ensure mana doesn't exceed maxMana
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void copyFrom(ManaImplementation source) {
        this.mana = source.mana;
        this.maxMana = source.maxMana;
        this.manaRegenRate = source.manaRegenRate;
        this.manaRegenTime = source.manaRegenTime;
    }
}