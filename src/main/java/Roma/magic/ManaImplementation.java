package Roma.magic;

import Roma.magic.config.ManaConfig;
import net.minecraft.nbt.CompoundTag;

public class ManaImplementation implements IMana {
    private int mana;
    private int maxMana;

    public ManaImplementation() {
        // Use the safe getter method instead of direct config access
        this.maxMana = ManaConfig.getDefaultMaxMana();
        this.mana = this.maxMana;
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public int getMaxMana() {
        return maxMana;
    }

    @Override
    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, maxMana));
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
        this.maxMana = Math.max(1, maxMana);
        // Adjust current mana if it exceeds new max
        if (this.mana > this.maxMana) {
            this.mana = this.maxMana;
        }
    }

    @Override
    public void regenerateMana(int amount) {
        addMana(amount);
    }

    @Override
    public float getManaPercentage() {
        return maxMana > 0 ? (float) mana / maxMana : 0f;
    }

    // NBT serialization methods
    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("mana", mana);
        nbt.putInt("maxMana", maxMana);
    }

    public void loadNBTData(CompoundTag nbt) {
        mana = nbt.getInt("mana");
        maxMana = nbt.getInt("maxMana");

        // Ensure maxMana has a reasonable default if not found
        if (maxMana <= 0) {
            maxMana = ManaConfig.getDefaultMaxMana();
        }

        // Ensure mana doesn't exceed maxMana
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void copyFrom(ManaImplementation source) {
        this.mana = source.mana;
        this.maxMana = source.maxMana;
    }
}