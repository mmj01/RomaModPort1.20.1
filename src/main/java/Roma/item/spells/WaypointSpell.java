// Waypoint Spell - Save current location and teleport back to it
package Roma.item.spells;

import Roma.menu.skillmenu.SkillUtil;
import Roma.menu.stats.ModStats;
import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WaypointSpell extends Spell {
    private boolean isSetMode = true; // true = set waypoint, false = teleport to waypoint

    // Static storage for waypoints (persists across spell instances)
    private static final Map<UUID, WaypointData> playerWaypoints = new HashMap<>();

    // Data class to store waypoint information
    private static class WaypointData {
        final Vec3 position;
        final ResourceKey<Level> dimension;
        final float yaw, pitch;
        long timeSet;

        WaypointData(Vec3 pos, ResourceKey<Level> dim, float yaw, float pitch) {
            this.position = pos;
            this.dimension = dim;
            this.yaw = yaw;
            this.pitch = pitch;
            this.timeSet = System.currentTimeMillis();
        }
    }

    public WaypointSpell() {
        super("Mystic Anchor", 100, 0, SpellType.UTILITY); // Moderate cost and cooldown
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        // Check if player is sneaking to determine mode
        boolean shouldSetWaypoint = player.isShiftKeyDown();
        UUID playerId = player.getUUID();

        if (shouldSetWaypoint) {
            return setWaypoint(level, player, playerId);
        } else {
            return teleportToWaypoint(level, player, playerId);
        }
    }

    private boolean setWaypoint(Level level, Player player, UUID playerId) {
        if (!SpellUtil.tryCastSpell(player, manaCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + manaCost + ", have " + currentMana));
            return false;
        }

        try {
            // Save current position and dimension
            Vec3 position = player.position();
            ResourceKey<Level> dimension = level.dimension();
            float yaw = player.getYRot();
            float pitch = player.getXRot();

            playerWaypoints.put(playerId, new WaypointData(position, dimension, yaw, pitch));

            // Add visual effects for setting waypoint
            if (level instanceof ServerLevel serverLevel) {
                addSetWaypointEffects(serverLevel, player.blockPosition());
            }

            player.sendSystemMessage(Component.literal("§a⚓ Waypoint set at your current location!"));
            applyCooldown(player);
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                // Awards XP equal to the mana spent!
                serverPlayer.awardStat(ModStats.MAGIC_USED.get(), this.manaCost);
                SkillUtil.syncMagicMana(serverPlayer);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean teleportToWaypoint(Level level, Player player, UUID playerId) {
        WaypointData waypoint = playerWaypoints.get(playerId);

        if (waypoint == null) {
            player.sendSystemMessage(Component.literal("§cNo waypoint set! Sneak while casting to set a waypoint."));
            return false;
        }

        // Higher mana cost for teleportation
        //SET MANA HERE FOR TELEPORT
        int teleportCost = 40;
        if (!SpellUtil.tryCastSpell(player, teleportCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + teleportCost + ", have " + currentMana));
            return false;
        }

        try {
            if (!(player instanceof ServerPlayer serverPlayer)) {
                return false;
            }

            // Add departure effects
            if (level instanceof ServerLevel serverLevel) {
                addTeleportDepartureEffects(serverLevel, player.blockPosition());
            }

            // Check if waypoint is in different dimension
            if (!waypoint.dimension.equals(level.dimension())) {
                // Cross-dimensional teleport
                ServerLevel targetLevel = serverPlayer.getServer().getLevel(waypoint.dimension);
                if (targetLevel == null) {
                    player.sendSystemMessage(Component.literal("§cWaypoint dimension no longer exists!"));
                    return false;
                }

                // Teleport to different dimension
                serverPlayer.teleportTo(targetLevel,
                        waypoint.position.x, waypoint.position.y, waypoint.position.z,
                        waypoint.yaw, waypoint.pitch);

                // Add arrival effects in target dimension
                addTeleportArrivalEffects(targetLevel, BlockPos.containing(waypoint.position));
            } else {
                // Same dimension teleport
                serverPlayer.teleportTo(waypoint.position.x, waypoint.position.y, waypoint.position.z);
                serverPlayer.setYRot(waypoint.yaw);
                serverPlayer.setXRot(waypoint.pitch);

                // Add arrival effects
                if (level instanceof ServerLevel serverLevel) {
                    addTeleportArrivalEffects(serverLevel, BlockPos.containing(waypoint.position));
                }
            }

            // Calculate time since waypoint was set
            long timeDiff = System.currentTimeMillis() - waypoint.timeSet;
            long minutesAgo = timeDiff / (1000 * 60);

            if (minutesAgo > 0) {
                player.sendSystemMessage(Component.literal("§b✦ Teleported to waypoint set " + minutesAgo + " minutes ago!"));
            } else {
                player.sendSystemMessage(Component.literal("§b✦ Teleported to waypoint!"));
            }

            applyCooldown(player);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addSetWaypointEffects(ServerLevel level, BlockPos pos) {
        // Golden anchor-like particles rising up
        for (int i = 0; i < 20; i++) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * 2;
            double y = pos.getY() + Math.random() * 3;
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * 2;

            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 3, 0.1, 0.1, 0.1, 0.8);
        }

        // Ring of particles on ground
        for (int i = 0; i < 16; i++) {
            double angle = (i / 16.0) * 2 * Math.PI;
            double x = pos.getX() + 0.5 + Math.cos(angle) * 2;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * 2;
            double y = pos.getY() + 0.1;

            level.sendParticles(ParticleTypes.END_ROD, x, y, z, 1, 0, 0.2, 0, 0.05);
        }
    }

    private void addTeleportDepartureEffects(ServerLevel level, BlockPos pos) {
        // Swirling portal-like effect
        for (int i = 0; i < 30; i++) {
            double angle = (i / 30.0) * 4 * Math.PI;
            double radius = 1.5 * (1 - (i / 30.0)); // Shrinking spiral
            double x = pos.getX() + 0.5 + Math.cos(angle) * radius;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * radius;
            double y = pos.getY() + 0.5 + (i / 30.0) * 2;

            level.sendParticles(ParticleTypes.PORTAL, x, y, z, 2, 0.1, 0.1, 0.1, 0.1);
        }

        // Implosion effect
        for (int i = 0; i < 15; i++) {
            double x = pos.getX() + 0.5 + (Math.random() - 0.5) * 3;
            double y = pos.getY() + 0.5 + Math.random() * 2;
            double z = pos.getZ() + 0.5 + (Math.random() - 0.5) * 3;

            level.sendParticles(ParticleTypes.WITCH, x, y, z, 1, -0.3, -0.1, -0.3, 0.5);
        }
    }

    private void addTeleportArrivalEffects(ServerLevel level, BlockPos pos) {
        // Explosion of particles on arrival
        for (int i = 0; i < 25; i++) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;

            level.sendParticles(ParticleTypes.DRAGON_BREATH, x, y, z, 3, 1.5, 1, 1.5, 0.1);
        }

        // Expanding ring effect
        for (int i = 0; i < 20; i++) {
            double angle = (i / 20.0) * 2 * Math.PI;
            double x = pos.getX() + 0.5 + Math.cos(angle) * 2.5;
            double z = pos.getZ() + 0.5 + Math.sin(angle) * 2.5;
            double y = pos.getY() + 0.1;

            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 2, 0, 0.5, 0, 0.2);
        }
    }

    // Utility methods
    public static boolean hasWaypoint(Player player) {
        return playerWaypoints.containsKey(player.getUUID());
    }

    public static void clearWaypoint(Player player) {
        playerWaypoints.remove(player.getUUID());
        player.sendSystemMessage(Component.literal("§6Waypoint cleared."));
    }

    public static String getWaypointInfo(Player player) {
        WaypointData waypoint = playerWaypoints.get(player.getUUID());
        if (waypoint == null) {
            return "No waypoint set";
        }

        long timeDiff = System.currentTimeMillis() - waypoint.timeSet;
        long minutesAgo = timeDiff / (1000 * 60);

        return String.format("Waypoint: %.1f, %.1f, %.1f (%s) - Set %d minutes ago",
                waypoint.position.x, waypoint.position.y, waypoint.position.z,
                waypoint.dimension.location().getPath(), minutesAgo);
    }

    // Method to save waypoints to player data (call this from your mod's save handler)
    public static void saveWaypointToPlayerData(Player player) {
        WaypointData waypoint = playerWaypoints.get(player.getUUID());
        if (waypoint != null) {
            player.getPersistentData().putDouble("waypoint_x", waypoint.position.x);
            player.getPersistentData().putDouble("waypoint_y", waypoint.position.y);
            player.getPersistentData().putDouble("waypoint_z", waypoint.position.z);
            player.getPersistentData().putString("waypoint_dim", waypoint.dimension.location().toString());
            player.getPersistentData().putFloat("waypoint_yaw", waypoint.yaw);
            player.getPersistentData().putFloat("waypoint_pitch", waypoint.pitch);
            player.getPersistentData().putLong("waypoint_time", waypoint.timeSet);
        }
    }

    // Method to load waypoints from player data (call this when player joins)
    public static void loadWaypointFromPlayerData(Player player) {
        if (player.getPersistentData().contains("waypoint_x")) {
            double x = player.getPersistentData().getDouble("waypoint_x");
            double y = player.getPersistentData().getDouble("waypoint_y");
            double z = player.getPersistentData().getDouble("waypoint_z");
            String dimString = player.getPersistentData().getString("waypoint_dim");
            float yaw = player.getPersistentData().getFloat("waypoint_yaw");
            float pitch = player.getPersistentData().getFloat("waypoint_pitch");
            long timeSet = player.getPersistentData().getLong("waypoint_time");

            // Reconstruct dimension key (you may need to adjust this based on your mod structure)
            ResourceKey<Level> dimension = ResourceKey.create(
                    net.minecraft.core.registries.Registries.DIMENSION,
                    net.minecraft.resources.ResourceLocation.parse(dimString)
            );

            WaypointData waypoint = new WaypointData(new Vec3(x, y, z), dimension, yaw, pitch);
            waypoint.timeSet = timeSet; // This would need reflection to set, or make timeSet non-final
            playerWaypoints.put(player.getUUID(), waypoint);
        }
    }

    // Standard spell methods...
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
        // Handled by SpellUtil.tryCastSpell()
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