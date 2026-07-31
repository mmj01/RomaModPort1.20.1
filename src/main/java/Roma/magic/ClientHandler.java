package Roma.magic;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHandler {

    @OnlyIn(Dist.CLIENT)
    public static void handleManaSync(ManaSyncPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                mana.setMaxMana(packet.getMaxMana());
                mana.setMana(packet.getMana());
            });
        }
    }

    // NEW: Handle incoming magic damage updates independently
    @OnlyIn(Dist.CLIENT)
    public static void handleMagicDamageSync(MagicDamageSyncPacket packet) {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
                mana.setMagicDamage(packet.getMagicDamage());
            });
        }
    }
}