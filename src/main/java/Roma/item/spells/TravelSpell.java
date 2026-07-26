package Roma.item.spells;

import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TravelSpell extends Spell {

    public TravelSpell() {
        super("Travel", 8, 2, SpellType.UTILITY);
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        if (!hasSufficientMana(player)) {
            player.sendSystemMessage(Component.literal("§cNot enough mana!"));
            return false;
        }

        try {
            if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                Vec3 lookDir = player.getViewVector(1.0F);
                Vec3 eyePos = player.getEyePosition();

                ThrownEnderpearl pearl = new ThrownEnderpearl(level, player);

                Vec3 spawnPos = eyePos.add(lookDir.normalize().scale(0.5D));
                pearl.setPos(spawnPos.x, spawnPos.y, spawnPos.z);

                pearl.shoot(lookDir.x, lookDir.y, lookDir.z, 5.0F, 0.0F);

                level.addFreshEntity(pearl);

                level.playSound(null, player.blockPosition(), SoundEvents.ENDER_PEARL_THROW, SoundSource.PLAYERS, 1.0F, 3.0F);
                addVisualEffects(serverLevel, player.blockPosition());

            }

            consumeMana(player);
            applyCooldown(player);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        for (int i = 0; i < 20; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * 6;
            double y = centerPos.getY() + Math.random() * 3;
            double z = centerPos.getZ() + (Math.random() - 0.5) * 6;

            level.sendParticles(ParticleTypes.CRIT, x, y, z, 3, 0.2, 0.2, 0.2, 0.1);
        }
    }

    @Override
    protected boolean hasSufficientMana(Player player) {
        return SpellUtil.hasEnoughMana(player, manaCost);
    }

    @Override
    protected boolean isOnCooldown(Player player) {
        return getCooldownFromPlayer(player, this) > 0;
    }

    @Override
    protected void consumeMana(Player player) {
        SpellUtil.tryCastSpell(player, manaCost);
    }

    @Override
    protected void applyCooldown(Player player) {
        setCooldownForPlayer(player, this, cooldown);
    }

    private int getCooldownFromPlayer(Player player, Spell spell) {
        return player.getPersistentData().getInt("cooldown_" + spell.getName());
    }

    private void setCooldownForPlayer(Player player, Spell spell, int ticks) {
        player.getPersistentData().putInt("cooldown_" + spell.getName(), ticks);
    }
}