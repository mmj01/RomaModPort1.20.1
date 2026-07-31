package Roma.item.spells;

import Roma.menu.skillmenu.SkillUtil;
import Roma.menu.stats.ModStats;
import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IceTrapSpell extends Spell {
    private int radius = 16;
    private int iceDuration = 400;
    private int slownessDuration = 600;


    private static final Map<BlockPos, IceData> trackedIceBlocks = new HashMap<>();

    private static class IceData {
        final BlockState originalBlock;
        final ServerLevel level;
        int ticksRemaining;

        IceData(BlockState original, ServerLevel level, int duration) {
            this.originalBlock = original;
            this.level = level;
            this.ticksRemaining = duration;
        }
    }

    public IceTrapSpell() {
        super("Frozen Prison", 75, 0, SpellType.OFFENSIVE);
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (level == null || player == null) {
            return false;
        }

        // FIX: Replaced undefined canCast() with standard checks
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + (remaining / 20) + " seconds remaining"));
            return false;
        }

        if (!SpellUtil.tryCastSpell(player, manaCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + manaCost + ", have " + currentMana));
            return false;
        }

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            // Awards XP equal to the mana spent!
            serverPlayer.awardStat(ModStats.MAGIC_USED.get(), this.manaCost);
            SkillUtil.syncMagicMana(serverPlayer);
        }

        try {
            BlockPos playerPos = player.blockPosition();
            int creaturesTrapped = trapNearbyCreatures(level, playerPos, player);

            if (level instanceof ServerLevel serverLevel) {
                addVisualEffects(serverLevel, playerPos);
                level.playSound(null, playerPos, SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0f, 0.8f);
            }

            if (creaturesTrapped > 0) {
                player.sendSystemMessage(Component.literal("§b❄ Trapped " + creaturesTrapped + " creatures in ice for 10 seconds!"));
            } else {
                player.sendSystemMessage(Component.literal("§eNo creatures found nearby to trap."));
            }

            applyCooldown(player);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            player.sendSystemMessage(Component.literal("§cSpell casting failed!"));
            return false;
        }
    }

    private int trapNearbyCreatures(Level level, BlockPos centerPos, Player caster) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return 0;
        }

        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(centerPos).inflate(radius),
                entity -> entity != caster && entity.isAlive() && !entity.isSpectator() && !entity.isInvulnerable()
        );

        int trapped = 0;
        for (LivingEntity entity : nearbyEntities) {
            if (trapCreatureInIce(serverLevel, entity, caster)) {
                trapped++;
            }
        }

        return trapped;
    }

    private boolean trapCreatureInIce(ServerLevel level, LivingEntity entity, Player caster) {
        BlockPos entityPos = entity.blockPosition();
        boolean anyIceCreated = false;

        AABB entityBounds = entity.getBoundingBox();
        int minX = (int) Math.floor(entityBounds.minX) - 1;
        int maxX = (int) Math.ceil(entityBounds.maxX);
        int minY = (int) Math.floor(entityBounds.minY);
        int maxY = (int) Math.ceil(entityBounds.maxY) + 1;
        int minZ = (int) Math.floor(entityBounds.minZ) - 1;
        int maxZ = (int) Math.ceil(entityBounds.maxZ);

        minX = Math.min(minX, entityPos.getX() - 1);
        maxX = Math.max(maxX, entityPos.getX() + 1);
        minY = Math.min(minY, entityPos.getY());
        maxY = Math.max(maxY, entityPos.getY() + 2);
        minZ = Math.min(minZ, entityPos.getZ() - 1);
        maxZ = Math.max(maxZ, entityPos.getZ() + 1);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos icePos = new BlockPos(x, y, z);
                    BlockState currentBlock = level.getBlockState(icePos);

                    if (currentBlock.getDestroySpeed(level, icePos) < 0) {
                        continue;
                    }

                    boolean success = level.setBlock(icePos, Blocks.ICE.defaultBlockState(), 3);
                    if (success) {
                        trackedIceBlocks.put(icePos, new IceData(currentBlock, level, iceDuration));
                        anyIceCreated = true;
                    }
                }
            }
        }

        if (anyIceCreated) {
            // Calculate final damage using base spell damage + player's bonus magic damage stat from capability
            int bonusDamage = SpellUtil.getPlayerMagicDamage(caster);

            // Deal the magic damage to the trapped creature, attributing the attack to the player
            entity.hurt(level.damageSources().indirectMagic(caster, caster), (float) bonusDamage);

            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, iceDuration + 40, 10));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, iceDuration, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, iceDuration, 3));
            entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, iceDuration + 40, 0));

            double centerX = (minX + maxX) / 2.0;
            double centerY = minY + 1.0;
            double centerZ = (minZ + maxZ) / 2.0;
            entity.teleportTo(centerX, centerY, centerZ);

            createFreezingEffect(level, entity.blockPosition());
        }

        return anyIceCreated;
    }

    private void createFreezingEffect(ServerLevel level, BlockPos pos) {
        for (int i = 0; i < 30; i++) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * 3;
            double y = pos.getY() + Math.random() * 3;
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * 3;

            level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 2, 0.1, 0.1, 0.1, 0.1);
            level.sendParticles(ParticleTypes.ITEM_SNOWBALL, x, y, z, 1, 0.2, 0.2, 0.2, 0.05);
        }
    }

    public static void tickIceBlocks() {
        if (trackedIceBlocks.isEmpty()) return;

        trackedIceBlocks.entrySet().removeIf(entry -> {
            BlockPos pos = entry.getKey();
            IceData data = entry.getValue();

            data.ticksRemaining--;

            if (data.ticksRemaining <= 40 && data.ticksRemaining > 0) {
                if (data.ticksRemaining % 5 == 0) {
                    data.level.sendParticles(ParticleTypes.DRIPPING_WATER,
                            pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                            1, 0.3, 0.1, 0.3, 0.1);
                }
            }

            if (data.ticksRemaining <= 0) {
                if (data.level.getBlockState(pos).is(Blocks.ICE)) {
                    data.level.setBlock(pos, data.originalBlock, 3);

                    data.level.sendParticles(ParticleTypes.ITEM_SNOWBALL,
                            pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                            8, 0.3, 0.3, 0.3, 0.1);

                    if (Math.random() < 0.1) {
                        data.level.playSound(null, pos, SoundEvents.GLASS_BREAK,
                                SoundSource.BLOCKS, 0.3f, 1.2f);
                    }

                    applyPostIceEffects(pos, data.level);
                }
                return true;
            }

            return false;
        });
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        for (int i = 0; i < 50; i++) {
            double angle = (i / 50.0) * 2 * Math.PI;
            double distance = 1 + Math.random() * radius;
            double x = centerPos.getX() + 0.5 + Math.cos(angle) * distance;
            double z = centerPos.getZ() + 0.5 + Math.sin(angle) * distance;
            double y = centerPos.getY() + Math.random() * 3;

            level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 3, 0.2, 0.2, 0.2, 0.1);
            level.sendParticles(ParticleTypes.CLOUD, x, y, z, 1, 0.1, 0.1, 0.1, 0.05);
        }

        for (int i = 0; i < 35; i++) {
            double x = centerPos.getX() + 0.5 + (Math.random() - 0.5) * 6;
            double y = centerPos.getY() + 0.5 + Math.random() * 2;
            double z = centerPos.getZ() + 0.5 + (Math.random() - 0.5) * 6;

            level.sendParticles(ParticleTypes.ITEM_SNOWBALL, x, y, z, 2, 0.3, 0.3, 0.3, 0.1);
        }

        for (int i = 0; i < 25; i++) {
            double angle = (i / 25.0) * 4 * Math.PI;
            double height = (i / 25.0) * 4;
            double x = centerPos.getX() + 0.5 + Math.cos(angle) * 2;
            double z = centerPos.getZ() + 0.5 + Math.sin(angle) * 2;
            double y = centerPos.getY() + height;

            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 1, 0, 0, 0, 0.4);
        }
    }

    private static void applyPostIceEffects(BlockPos pos, ServerLevel level) {
        List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(
                LivingEntity.class,
                new AABB(pos).inflate(2.0)
        );

        for (LivingEntity entity : nearbyEntities) {
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            entity.removeEffect(MobEffects.WEAKNESS);
            entity.removeEffect(MobEffects.WATER_BREATHING);

            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 0));
        }
    }

    public static int getTrackedIceBlockCount() {
        return trackedIceBlocks.size();
    }

    public static void clearAllTrackedIce() {
        for (Map.Entry<BlockPos, IceData> entry : trackedIceBlocks.entrySet()) {
            IceData data = entry.getValue();
            if (data.level.getBlockState(entry.getKey()).is(Blocks.ICE)) {
                data.level.setBlock(entry.getKey(), data.originalBlock, 3);
            }
        }
        trackedIceBlocks.clear();
    }

    public void setTrapRadius(int newRadius) {
        this.radius = Math.max(3, Math.min(20, newRadius));
    }

    public void setIceDuration(int ticks) {
        this.iceDuration = Math.max(40, Math.min(600, ticks));
    }

    @Override
    protected boolean hasSufficientMana(Player player) {
        try {
            return SpellUtil.hasEnoughMana(player, manaCost);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected boolean isOnCooldown(Player player) {
        try {
            if (cooldown == 0) {
                return false;
            }

            int cooldownRemaining = getCooldownFromPlayer(player, this);
            return cooldownRemaining > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void consumeMana(Player player) {
        try {
            SpellUtil.tryCastSpell(player, manaCost);
            player.awardStat(ModStats.MAGIC_USED.get(), manaCost);
        } catch (Exception e) {
        }
    }

    @Override
    protected void applyCooldown(Player player) {
        try {
            setCooldownForPlayer(player, this, cooldown);
        } catch (Exception e) {
        }
    }

    private int getCooldownFromPlayer(Player player, Spell spell) {
        if (player == null || player.getPersistentData() == null) return 0;
        return player.getPersistentData().getInt("cooldown_" + spell.getName());
    }

    private void setCooldownForPlayer(Player player, Spell spell, int ticks) {
        if (player != null && player.getPersistentData() != null) {
            player.getPersistentData().putInt("cooldown_" + spell.getName(), ticks);
        }
    }
}