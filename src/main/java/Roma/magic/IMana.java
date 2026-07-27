package Roma.magic;

public interface IMana {
    int getMana();
    int getMaxMana();
    void setMana(int mana);
    void addMana(int amount);
    void consumeMana(int amount);
    boolean canConsumeMana(int amount);
    void setMaxMana(int maxMana);
    void regenerateMana(int amount);
    float getManaPercentage();
    int getManaRegenRate();
    void setManaRegenRate(int rate);
    int getManaRegenTime();
    void setManaRegenTime(int time);

}