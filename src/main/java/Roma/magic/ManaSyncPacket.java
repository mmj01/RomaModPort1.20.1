package Roma.magic;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class ManaSyncPacket {
    private final int mana;
    private final int maxMana;

    public ManaSyncPacket(int mana, int maxMana) {
        this.mana = mana;
        this.maxMana = maxMana;
    }

    public ManaSyncPacket(FriendlyByteBuf buf) {
        this.mana = buf.readInt();
        this.maxMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.mana);
        buf.writeInt(this.maxMana);
    }

    public int getMana() { return mana; }
    public int getMaxMana() { return maxMana; }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientHandler.handleManaSync(this);
        });
        context.setPacketHandled(true);
    }
}