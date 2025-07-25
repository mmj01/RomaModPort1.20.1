package Roma;

import Roma.block.ModBlocks;
import Roma.enchantment.ModEnchantments;
import Roma.entity.custom.AssassinlvoneRenderer;
import Roma.entity.Modentities;
import Roma.item.ModCreativeModeTabs;
import Roma.item.Moditems;
import Roma.item.custom.CustomAttribute;

// Add these imports for the mana system
import Roma.item.spells.IceTrapSpell;
import Roma.magic.config.ManaConfig;


import Roma.magic.config.NetworkHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(roma.MOD_ID)
public class roma
{
    public static final String MOD_ID = "rma";
    public static final Logger LOGGER = LogUtils.getLogger();

    public roma()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        //Place Registers here for new items
        Moditems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        CustomAttribute.register(modEventBus);
        ModEnchantments.register(modEventBus);

        Modentities.ENTITIES.register(modEventBus);
        modEventBus.register(Modentities.class);

        // === MANA SYSTEM REGISTRATION === //
        // Register mana system configuration
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ManaConfig.SPEC, "rma-mana.toml");

        // Register mana networking
        NetworkHandler.register();
        // === END MANA SYSTEM === //

        modEventBus.addListener(this::addCreative);


    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // You can add any common setup for mana system here if needed
        // Most of the mana system is handled automatically through events
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        // Add any mana-related items to creative tabs here if you create them
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Server-side initialization if needed
    }
    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase == TickEvent.Phase.END) {
            IceTrapSpell.tickIceBlocks();
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;
            var data = player.getPersistentData();

            // Reduce all spell cooldowns
            for (String key : data.getAllKeys()) {
                if (key.startsWith("cooldown_")) {
                    int currentCooldown = data.getInt(key);
                    if (currentCooldown > 0) {
                        data.putInt(key, currentCooldown - 1);
                    }
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(Modentities.PERSIANASSASSIN.get(), AssassinlvoneRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.WHEATCROP.get(), RenderType.cutout());
            });
        }
    }
}