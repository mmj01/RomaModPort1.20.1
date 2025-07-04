package Roma;

import Roma.block.ModBlocks;

import Roma.entity.EntityRenderer;
import Roma.entity.Modentities;
import Roma.item.ModCreativeModeTabs;
import Roma.item.Moditems;

import Roma.item.custom.CustomAttribute;

import Roma.worldgen.Biome.ModTerrablender;
import Roma.worldgen.Biome.surface.ModSurfaceRules;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
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
import terrablender.api.SurfaceRuleManager;

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
        ModTerrablender.registerBiomes();
        Modentities.ENTITIES.register(modEventBus);
        modEventBus.register(Modentities.class);
        








        modEventBus.addListener(this::addCreative);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());









    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }


    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(Modentities.PERSIANASSASSIN.get(), EntityRenderer::new);
        }
    }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {







            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.WHEATCROP.get(), RenderType.cutout());
            });

        }
    }

