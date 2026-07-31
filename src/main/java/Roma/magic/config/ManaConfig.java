package Roma.magic.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ManaConfig {

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading event) {
        // When the .toml file changes, immediately refresh the cached variables in memory!
        defaultMaxMana = DEFAULT_MAX_MANA.get();
        manaRegenRate = MANA_REGEN_RATE.get();
        manaRegenDelay = MANA_REGEN_DELAY.get();
        enableManaRegen = ENABLE_MANA_REGEN.get();
        regenIntervalTicks = REGEN_INTERVAL_TICKS.get();
        magicDamage=MAGIC_DAMAGE.get();

        showManaHUD = SHOW_MANA_HUD.get();
        hudOffsetX = HUD_OFFSET_X.get();
        hudOffsetY = HUD_OFFSET_Y.get();

        try {
            String colorHex = MANA_BAR_COLOR_HEX.get();
            manaBarColor = 0xFF000000 | Integer.parseInt(colorHex, 16);
        } catch (NumberFormatException e) {
            manaBarColor = 0xFF3366FF;
        }
    }

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Config values
    public static final ForgeConfigSpec.IntValue DEFAULT_MAX_MANA;
    public static final ForgeConfigSpec.IntValue MANA_REGEN_RATE;
    public static final ForgeConfigSpec.IntValue MANA_REGEN_DELAY;
    public static final ForgeConfigSpec.BooleanValue ENABLE_MANA_REGEN;
    public static final ForgeConfigSpec.IntValue REGEN_INTERVAL_TICKS;
    public static final ForgeConfigSpec.IntValue MAGIC_DAMAGE;

    // HUD Config values
    public static final ForgeConfigSpec.BooleanValue SHOW_MANA_HUD;
    public static final ForgeConfigSpec.IntValue HUD_OFFSET_X;
    public static final ForgeConfigSpec.IntValue HUD_OFFSET_Y;
    public static final ForgeConfigSpec.ConfigValue<String> MANA_BAR_COLOR_HEX;

    // Cached values - only access these after config is loaded
    private static int defaultMaxMana = 100; // fallback default
    private static int manaRegenRate = 1;
    private static int manaRegenDelay = 40;
    private static boolean enableManaRegen = true;
    private static int regenIntervalTicks = 20;
    private static int magicDamage=10;

    // HUD cached values
    private static boolean showManaHUD = true;
    private static int hudOffsetX = 0;
    private static int hudOffsetY = 0;
    private static int manaBarColor = 0xFF3366FF; // Blue color as default

    static {
        BUILDER.push("Mana Settings");

        DEFAULT_MAX_MANA = BUILDER
                .comment("Default maximum mana for players")
                .defineInRange("defaultMaxMana", 100, 1, 100000000);

        MANA_REGEN_RATE = BUILDER
                .comment("How much mana regenerates per regeneration tick")
                .defineInRange("manaRegenRate", 1, 0, 100000000);

        MANA_REGEN_DELAY = BUILDER
                .comment("Delay in ticks before mana starts regenerating after use")
                .defineInRange("manaRegenDelay", 20, 0, 2000000);

        ENABLE_MANA_REGEN = BUILDER
                .comment("Whether mana regeneration is enabled")
                .define("enableManaRegen", true);

        REGEN_INTERVAL_TICKS = BUILDER
                .comment("How often (in ticks) mana regeneration occurs")
                .defineInRange("regenIntervalTicks", 20, 1, 2000000);

        MAGIC_DAMAGE = BUILDER
                .comment("How much damage is applied to enemies when damaging spells occur")
                .defineInRange("magicDamage", 10, 10, 2000000);

        BUILDER.pop();

        BUILDER.push("HUD Settings");

        SHOW_MANA_HUD = BUILDER
                .comment("Whether to show the mana HUD overlay")
                .define("showManaHUD", true);

        HUD_OFFSET_X = BUILDER
                .comment("Horizontal offset for the mana HUD (positive = right, negative = left)")
                .defineInRange("hudOffsetX", 0, -500, 500);

        HUD_OFFSET_Y = BUILDER
                .comment("Vertical offset for the mana HUD (positive = down, negative = up)")
                .defineInRange("hudOffsetY", 0, -500, 500);

        MANA_BAR_COLOR_HEX = BUILDER
                .comment("Color of the mana bar in hex format (e.g., 3366FF for blue)")
                .define("manaBarColorHex", "3366FF");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        // Cache the config values when config loads
        defaultMaxMana = DEFAULT_MAX_MANA.get();
        manaRegenRate = MANA_REGEN_RATE.get();
        manaRegenDelay = MANA_REGEN_DELAY.get();
        enableManaRegen = ENABLE_MANA_REGEN.get();
        regenIntervalTicks = REGEN_INTERVAL_TICKS.get();
        magicDamage= MAGIC_DAMAGE.get();

        // Cache HUD values
        showManaHUD = SHOW_MANA_HUD.get();
        hudOffsetX = HUD_OFFSET_X.get();
        hudOffsetY = HUD_OFFSET_Y.get();

        // Parse hex color
        try {
            String colorHex = MANA_BAR_COLOR_HEX.get();
            manaBarColor = 0xFF000000 | Integer.parseInt(colorHex, 16);
        } catch (NumberFormatException e) {
            manaBarColor = 0xFF3366FF; // Default blue if parsing fails
        }
    }

    // Safe getter methods - these won't throw the config error
    // Replace your existing getters in ManaConfig.java with these:

    public static int getDefaultMaxMana() {return defaultMaxMana;
    }

    public static int getMagicDamage() {
        return magicDamage;
    }

    public static int getManaRegenRate() {
        return manaRegenRate;
    }


    public static int getManaRegenDelay() {
        return manaRegenDelay;
    }

    public static boolean isEnableManaRegen() {
        return enableManaRegen;
    }

    public static int getRegenIntervalTicks() {
        return regenIntervalTicks;
    }

    // HUD getter methods
    public static boolean isShowManaHUD() {
        return showManaHUD;
    }

    public static int getHudOffsetX() {
        return hudOffsetX;
    }

    public static int getHudOffsetY() {
        return hudOffsetY;
    }

    public static int getManaBarColor() {
        return manaBarColor;
    }
}