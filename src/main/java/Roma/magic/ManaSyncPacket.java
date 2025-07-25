package Roma.magic;

import Roma.magic.ManaCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaSyncPacket {
    private final int mana;
    private final int maxMana;

    public ManaSyncPacket(int mana, int maxMana) {
        this.mana = mana;
        this.maxMana = maxMana;
    }

    public static void encode(ManaSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.mana);
        buf.writeInt(packet.maxMana);
    }

    public static ManaSyncPacket decode(FriendlyByteBuf buf) {
        return new ManaSyncPacket(buf.readInt(), buf.readInt());
    }

    public static void handle(ManaSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Only handle on client side
            if (ctx.get().getDirection().getReceptionSide().isClient()) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    // Use the safe capability getter
                    player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                        mana.setMaxMana(packet.maxMana);
                        mana.setMana(packet.mana);
                    });
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}