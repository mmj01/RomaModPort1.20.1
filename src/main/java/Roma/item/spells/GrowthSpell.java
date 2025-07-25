// Growth Spell - Grows crops around the player to full maturity
package Roma.item.spells;

import Roma.magic.SpellUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GrowthSpell extends Spell {
    private int radius = 6; // Growth radius around player
    private int boneMealEffect = 3; // Number of bone meal applications per crop

    public GrowthSpell() {
        super("Nature's Blessing", 100, 0, SpellType.UTILITY); // Low mana cost, moderate cooldown
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        if (!SpellUtil.tryCastSpell(player, manaCost)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need " + manaCost + ", have " + currentMana));
            return false;
        }

        try {
            BlockPos playerPos = player.blockPosition();
            int cropsGrown = growCropsAroundPlayer(level, playerPos);

            // Add visual effects
            if (level instanceof ServerLevel serverLevel) {
                addVisualEffects(serverLevel, playerPos);
            }

            // Give feedback to player
            if (cropsGrown > 0) {
                player.sendSystemMessage(Component.literal("§a✦ Grew " + cropsGrown + " crops to maturity!"));
            } else {
                player.sendSystemMessage(Component.literal("§eNo crops found nearby to grow."));
            }

            applyCooldown(player);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private int growCropsAroundPlayer(Level level, BlockPos centerPos) {
        int cropsGrown = 0;

        // Search in a radius around the player
        for (int x = -radius; x <= radius; x++) {
            for (int y = -2; y <= 2; y++) { // Check a few blocks up and down
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = centerPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();

                    if (isCrop(block)) {
                        if (growCrop(level, pos, state, block)) {
                            cropsGrown++;
                        }
                    }
                }
            }
        }

        return cropsGrown;
    }

    private boolean isCrop(Block block) {
        // Check if it's a vanilla crop type
        if (block instanceof CropBlock ||
                block instanceof StemBlock ||
                block instanceof AttachedStemBlock ||
                block instanceof NetherWartBlock ||
                block instanceof CocoaBlock ||
                block instanceof SweetBerryBushBlock ||
                block instanceof CaveVinesPlantBlock ||
                block instanceof CaveVinesBlock) {
            return true;
        }
    //FIX HERE
        // Check for your custom crops by registry name
        String blockName = block.getDescriptionId();
        return blockName.contains("rma:") && (
                blockName.contains("crop") ||
                        blockName.contains("plant") ||
                        blockName.contains("wheat") ||
                        blockName.contains("corn") ||
                        blockName.contains("tomato") ||
                        blockName.contains("carrot") ||
                        blockName.contains("potato") ||
                        isCustomCrop(block)
        );
    }

    // Method to explicitly check for your custom crop blocks
    private boolean isCustomCrop(Block block) {
        // Option 1: Check by specific blocks (if you have references)
        // return block == ModBlocks.CUSTOM_WHEAT.get() ||
        //        block == ModBlocks.CUSTOM_CORN.get();

        // Option 2: Check if block implements BonemealableBlock (recommended)
        return block instanceof BonemealableBlock;
    }

    private boolean growCrop(Level level, BlockPos pos, BlockState state, Block block) {
        boolean grewSomething = false;

        // Apply bone meal effect multiple times for guaranteed growth
        for (int i = 0; i < boneMealEffect; i++) {
            if (block instanceof BonemealableBlock bonemealer) {
                if (bonemealer.isValidBonemealTarget(level, pos, state, level.isClientSide)) {
                    if (bonemealer.isBonemealSuccess(level, level.random, pos, state)) {
                        bonemealer.performBonemeal((ServerLevel) level, level.random, pos, state);
                        grewSomething = true;
                        // Update state for next iteration
                        state = level.getBlockState(pos);
                    }
                }
            }
            // Special handling for crops that use age properties
            else if (hasCropAgeProperty(state)) {
                if (growCropByAge(level, pos, state)) {
                    grewSomething = true;
                    state = level.getBlockState(pos);
                }
            }
        }

        return grewSomething;
    }

    private boolean hasCropAgeProperty(BlockState state) {
        // Check vanilla age properties
        if (state.hasProperty(CropBlock.AGE) ||
                state.hasProperty(NetherWartBlock.AGE) ||
                state.hasProperty(CocoaBlock.AGE) ||
                state.hasProperty(SweetBerryBushBlock.AGE)) {
            return true;
        }

        // Check for custom age properties
        return hasCustomAgeProperty(state);
    }

    private boolean hasCustomAgeProperty(BlockState state) {
        // Check if any integer property could be an age property
        return state.getProperties().stream()
                .anyMatch(property -> property instanceof IntegerProperty &&
                        (property.getName().equals("age") ||
                                property.getName().equals("growth") ||
                                property.getName().equals("stage")));
    }

    private boolean growCropByAge(Level level, BlockPos pos, BlockState state) {
        IntegerProperty ageProperty = null;
        int maxAge = 0;

        // Check vanilla age properties first
        if (state.hasProperty(CropBlock.AGE)) {
            ageProperty = CropBlock.AGE;
            maxAge = 7;
        } else if (state.hasProperty(NetherWartBlock.AGE)) {
            ageProperty = NetherWartBlock.AGE;
            maxAge = 3;
        } else if (state.hasProperty(CocoaBlock.AGE)) {
            ageProperty = CocoaBlock.AGE;
            maxAge = 2;
        } else if (state.hasProperty(SweetBerryBushBlock.AGE)) {
            ageProperty = SweetBerryBushBlock.AGE;
            maxAge = 3;
        } else {
            // Handle custom age properties
            for (var property : state.getProperties()) {
                if (property instanceof IntegerProperty intProp &&
                        (property.getName().equals("age") ||
                                property.getName().equals("growth") ||
                                property.getName().equals("stage"))) {
                    ageProperty = intProp;
                    maxAge = intProp.getPossibleValues().stream()
                            .mapToInt(Integer::intValue)
                            .max()
                            .orElse(7);  // Default to 7 if can't determine
                    break;
                }
            }
        }

        if (ageProperty != null) {
            int currentAge = state.getValue(ageProperty);
            if (currentAge < maxAge) {
                // Set to max age for instant growth
                BlockState newState = state.setValue(ageProperty, maxAge);
                level.setBlock(pos, newState, 2);
                return true;
            }
        }

        return false;
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        // Nature-themed particles around the player
        for (int i = 0; i < 30; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * (radius * 2);
            double y = centerPos.getY() + Math.random() * 3;
            double z = centerPos.getZ() + (Math.random() - 0.5) * (radius * 2);

            // Mix of nature particles
            if (Math.random() < 0.7) {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 2, 0.3, 0.3, 0.3, 0.1);
            } else {
                level.sendParticles(ParticleTypes.COMPOSTER, x, y, z, 1, 0.2, 0.2, 0.2, 0.05);
            }
        }

        // Upward flowing particles around player
        for (int i = 0; i < 15; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * 2;
            double y = centerPos.getY();
            double z = centerPos.getZ() + (Math.random() - 0.5) * 2;

            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 3, 0.1, 0.1, 0.1, 0.8);
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