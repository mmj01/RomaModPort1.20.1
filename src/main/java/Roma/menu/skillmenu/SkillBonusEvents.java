package Roma.menu.skillmenu;

import Roma.menu.stats.ModStats;
import Roma.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillBonusEvents {



    /**
     * MINING BONUS: +7% chance per level to double ore drops.
     */
    @SubscribeEvent
    public static void onOreMined(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();

        if (player != null && !player.level().isClientSide() && state.is(ModTags.Blocks.XPSTONE)) {
            int miningLevel = SkillUtil.getSkillLevel(player, Stats.CUSTOM.get(ModStats.XP_MINED.get()),
                    x -> (int) (100 * Math.pow(1.20, x - 1)));

            float doubleDropChance = miningLevel * 0.07F;

            if (player.level().random.nextFloat() < doubleDropChance) {
                ServerLevel serverLevel = (ServerLevel) player.level();
                List<ItemStack> drops = Block.getDrops(state, serverLevel, event.getPos(),
                        serverLevel.getBlockEntity(event.getPos()), player, player.getMainHandItem());

                for (ItemStack drop : drops) {
                    Block.popResource(serverLevel, event.getPos(), drop.copy());
                }
            }
        }
    }



    /**
     * COMBAT BONUS: +1.5 Extra Melee Damage per Combat Level.
     */
    @SubscribeEvent
    public static void onCombatDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide()) {

            int combatLevel = SkillUtil.getSkillLevel(player, Stats.CUSTOM.get(Stats.MOB_KILLS),
                    x -> (int) (25 * Math.pow(1.20, x - 1)));

            float bonusDamage = combatLevel * 1.5F;
            event.setAmount(event.getAmount() + bonusDamage);
        }
    }

    /**
     * SPELLCASTING BONUS: +2.5 Extra Magic Damage per Spellcasting Level.
     */
    @SubscribeEvent
    public static void onMagicDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide()) {

            // Check if the damage source is Magic (Vanilla potions, or your custom spells if using these types)
            if (event.getSource().is(DamageTypes.MAGIC) || event.getSource().is(DamageTypes.INDIRECT_MAGIC)) {

                // Fetch the Spellcasting Level using the exact curve from your SkillMenu
                int spellLevel = SkillUtil.getSkillLevel(player, Stats.CUSTOM.get(ModStats.MAGIC_USED.get()),
                        x -> (int) (100 + Math.exp(0.610278626 * x)));

                // Add +2.5 damage per Spellcasting level (Level 10 = +25.0 Damage, Level 20 = +50.0 Damage)
                float bonusMagicDamage = spellLevel * 2.5F;

                event.setAmount(event.getAmount() + bonusMagicDamage);
            }
        }
    }


    // --- MANA INITIALIZATION EVENTS ---

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        SkillUtil.syncMagicMana(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        // Runs when the player respawns or travels through dimensions (Nether/End)
        SkillUtil.syncMagicMana(event.getEntity());
    }
}