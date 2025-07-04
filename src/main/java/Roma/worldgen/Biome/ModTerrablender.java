package Roma.worldgen.Biome;


import Roma.roma;
import net.minecraft.resources.ResourceLocation;
import terrablender.api.Regions;

public class ModTerrablender {
    public static void registerBiomes() {
    Regions.register(new ModOverworldRegion( ResourceLocation.fromNamespaceAndPath(roma.MOD_ID,"overworld"), 50));
    }
}