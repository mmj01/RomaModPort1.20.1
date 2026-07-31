package Roma.menu.stats;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStats {
    public static final DeferredRegister<ResourceLocation> CUSTOM_STATS =
            DeferredRegister.create(Registries.CUSTOM_STAT, "rma");

    // 1. Spellcasting Stat
    public static final RegistryObject<ResourceLocation> MAGIC_USED =
            CUSTOM_STATS.register("magic_used", () -> new ResourceLocation("rma", "magic_used"));

    // 2. Custom Tag Mobs Killed Stat
    public static final RegistryObject<ResourceLocation> CUSTOM_MOBS_KILLED =
            CUSTOM_STATS.register("custom_mobs_killed", () -> new ResourceLocation("rma", "custom_mobs_killed"));

    // 3. Custom Tag Plants Broken Stat
    public static final RegistryObject<ResourceLocation> CUSTOM_PLANTS_BROKEN =
            CUSTOM_STATS.register("custom_plants_broken", () -> new ResourceLocation("rma", "custom_plants_broken"));

    public static final RegistryObject<ResourceLocation> XP_MINED =
            CUSTOM_STATS.register("xp_mined", () -> new ResourceLocation("rma", "xp_mined"));

    public static final RegistryObject<ResourceLocation> CUSTOM_ITEMS_CRAFTED =
            CUSTOM_STATS.register("custom_items_crafted", () -> new ResourceLocation("rma", "custom_items_crafted"));

    public static void register(IEventBus eventBus) {
        CUSTOM_STATS.register(eventBus);
    }

    // Call this inside FMLCommonSetupEvent in your main mod class so formatting displays correctly
    public static void setupStats() {
        Stats.CUSTOM.get(MAGIC_USED.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(CUSTOM_MOBS_KILLED.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(CUSTOM_PLANTS_BROKEN.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(XP_MINED.get(), StatFormatter.DEFAULT);
    }
}