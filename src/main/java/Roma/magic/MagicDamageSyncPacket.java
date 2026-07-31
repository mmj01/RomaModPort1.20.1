package Roma.magic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class MagicDamageSyncPacket {
    private final int magicDamage;

    public MagicDamageSyncPacket(int magicDamage) {
        this.magicDamage = magicDamage;
    }

    public MagicDamageSyncPacket(FriendlyByteBuf buf) {
        this.magicDamage = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.magicDamage);
    }

    public int getMagicDamage() { return magicDamage; }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientHandler.handleMagicDamageSync(this);
        });
        context.setPacketHandled(true);
    }
}