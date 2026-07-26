package Roma.magic;

import Roma.magic.ManaCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                // Safely runs ClientHandler without crashing dedicated servers
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handleManaSync(this));
            });
        }

        context.setPacketHandled(true);
    }

    public int getMana() {
        return mana;
    }

    public int getMaxMana() {
        return maxMana;
    }
}