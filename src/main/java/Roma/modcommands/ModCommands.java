package Roma.modcommands;


import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModCommands {
    public static boolean CUSTOM_KEEP_INVENTORY = false;


    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("customkeepinventory")
                .requires(source -> source.hasPermission(2)) // Only ops
                .then(Commands.literal("true")
                        .executes(ctx -> {
                            CUSTOM_KEEP_INVENTORY = true;
                            ctx.getSource().sendSuccess(() -> Component.literal("Custom keep inventory enabled."), true);
                            return 1;
                        })
                )
                .then(Commands.literal("false")
                        .executes(ctx -> {
                            CUSTOM_KEEP_INVENTORY = false;
                            ctx.getSource().sendSuccess(() -> Component.literal("Custom keep inventory disabled."), true);
                            return 1;
                        })
                )
        );
    }
}











