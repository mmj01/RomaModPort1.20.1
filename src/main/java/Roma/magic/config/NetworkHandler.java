package Roma.magic.config;

import Roma.magic.MagicDamageSyncPacket;
import Roma.magic.ManaSyncPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("rma", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        // ID 0: Fast mana bar updates
        INSTANCE.messageBuilder(ManaSyncPacket.class, packetId++)
                .encoder(ManaSyncPacket::toBytes)
                .decoder(ManaSyncPacket::new)
                .consumerMainThread(ManaSyncPacket::handle)
                .add();

        // ID 1: Occasional stat/damage updates
        INSTANCE.messageBuilder(MagicDamageSyncPacket.class, packetId++)
                .encoder(MagicDamageSyncPacket::toBytes)
                .decoder(MagicDamageSyncPacket::new)
                .consumerMainThread(MagicDamageSyncPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

    public static <MSG> void sendToAllPlayersInDimension(MSG message, net.minecraft.server.level.ServerLevel level) {
        INSTANCE.send(PacketDistributor.DIMENSION.with(() -> level.dimension()), message);
    }
}