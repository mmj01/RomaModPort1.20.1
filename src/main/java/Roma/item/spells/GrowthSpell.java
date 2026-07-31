package Roma.item.spells;

import Roma.menu.skillmenu.SkillUtil;
import Roma.menu.stats.ModStats;
import Roma.magic.ManaCapability;
import Roma.magic.ManaRegeneration;
import Roma.magic.ManaSyncPacket;
import Roma.magic.SpellUtil;
import Roma.magic.config.NetworkHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class GrowthSpell extends Spell {
    private int radius = 6;
    private int boneMealEffect = 3;

    public GrowthSpell() {
        super("Nature's Blessing", 10, 0, SpellType.UTILITY);
    }

    @Override
    public boolean cast(Level level, Player player, InteractionHand hand) {
        if (isOnCooldown(player)) {
            int remaining = getCooldownFromPlayer(player, this);
            player.sendSystemMessage(Component.literal("§cSpell on cooldown! " + remaining + " ticks remaining"));
            return false;
        }

        if (!SpellUtil.hasEnoughMana(player, 10)) {
            int currentMana = SpellUtil.getPlayerMana(player);
            player.sendSystemMessage(Component.literal("§cNot enough mana! Need at least 10, have " + currentMana));
            return false;
        }

        try {
            BlockPos playerPos = player.blockPosition();
            int cropsGrown = growCropsAroundPlayer(level, player, playerPos);

            if (cropsGrown > 0) {
                if (level instanceof ServerLevel serverLevel) {
                    addVisualEffects(serverLevel, playerPos);
                }
                player.sendSystemMessage(Component.literal("§a✦ Grew " + cropsGrown + " crops to maturity!"));
                applyCooldown(player);

                // ==========================================
                // FIX: Safely check and cast to ServerPlayer
                // ==========================================
                if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                    // Awards XP equal to the mana spent!
                    serverPlayer.awardStat(ModStats.MAGIC_USED.get(), this.manaCost);
                    //FOR TESTING
                    serverPlayer.awardStat(ModStats.MAGIC_USED.get(), 1000000);
                    serverPlayer.awardStat(ModStats.XP_MINED.get(), 1000000);
                    serverPlayer.awardStat(ModStats.CUSTOM_ITEMS_CRAFTED.get(), 1000000);
                    serverPlayer.awardStat(ModStats.CUSTOM_PLANTS_BROKEN.get(), 1000000);



                    SkillUtil.syncMagicMana(serverPlayer);
                }

                return true;
            } else {
                player.sendSystemMessage(Component.literal("§eNo crops found nearby to grow."));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void consumeMana(Player player, int amount) {
        player.getCapability(ManaCapability.MANA_CAPABILITY).ifPresent(mana -> {
            mana.consumeMana(amount);
            ManaRegeneration.onManaUsed(player);
            // Inside GrowthSpell.consumeMana():

// FIX: Added parentheses to isClientSide()
            if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                NetworkHandler.sendToPlayer(
                        new ManaSyncPacket(mana.getMana(), mana.getMaxMana()),
                        serverPlayer
                );
            }
        });
    }

    private int growCropsAroundPlayer(Level level, Player player, BlockPos centerPos) {
        int cropsGrown = 0;
        int manaPerCrop = 10;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = centerPos.offset(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();

                    if (isCrop(block)) {
                        if (!SpellUtil.hasEnoughMana(player, manaPerCrop)) {
                            return cropsGrown;
                        }

                        if (growCrop(level, pos, state, block)) {
                            consumeMana(player, manaPerCrop);
                            cropsGrown++;
                        }
                    }
                }
            }
        }

        return cropsGrown;
    }

    private boolean isCrop(Block block) {
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

    private boolean isCustomCrop(Block block) {
        return block instanceof BonemealableBlock;
    }

    private boolean growCrop(Level level, BlockPos pos, BlockState state, Block block) {
        boolean grewSomething = false;

        for (int i = 0; i < boneMealEffect; i++) {
            if (block instanceof BonemealableBlock bonemealer) {
                if (bonemealer.isValidBonemealTarget(level, pos, state, level.isClientSide)) {
                    if (bonemealer.isBonemealSuccess(level, level.random, pos, state)) {
                        bonemealer.performBonemeal((ServerLevel) level, level.random, pos, state);
                        grewSomething = true;
                        state = level.getBlockState(pos);
                    }
                }
            }
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
        if (state.hasProperty(CropBlock.AGE) ||
                state.hasProperty(NetherWartBlock.AGE) ||
                state.hasProperty(CocoaBlock.AGE) ||
                state.hasProperty(SweetBerryBushBlock.AGE)) {
            return true;
        }

        return hasCustomAgeProperty(state);
    }

    private boolean hasCustomAgeProperty(BlockState state) {
        return state.getProperties().stream()
                .anyMatch(property -> property instanceof IntegerProperty &&
                        (property.getName().equals("age") ||
                                property.getName().equals("growth") ||
                                property.getName().equals("stage")));
    }

    private boolean growCropByAge(Level level, BlockPos pos, BlockState state) {
        IntegerProperty ageProperty = null;
        int maxAge = 0;

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
            for (var property : state.getProperties()) {
                if (property instanceof IntegerProperty intProp &&
                        (property.getName().equals("age") ||
                                property.getName().equals("growth") ||
                                property.getName().equals("stage"))) {
                    ageProperty = intProp;
                    maxAge = intProp.getPossibleValues().stream()
                            .mapToInt(Integer::intValue)
                            .max()
                            .orElse(7);
                    break;
                }
            }
        }

        if (ageProperty != null) {
            int currentAge = state.getValue(ageProperty);
            if (currentAge < maxAge) {
                BlockState newState = state.setValue(ageProperty, maxAge);
                level.setBlock(pos, newState, 2);
                return true;
            }
        }

        return false;
    }

    private void addVisualEffects(ServerLevel level, BlockPos centerPos) {
        for (int i = 0; i < 30; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * (radius * 2);
            double y = centerPos.getY() + Math.random() * 3;
            double z = centerPos.getZ() + (Math.random() - 0.5) * (radius * 2);

            if (Math.random() < 0.7) {
                level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, y, z, 2, 0.3, 0.3, 0.3, 0.1);
            } else {
                level.sendParticles(ParticleTypes.COMPOSTER, x, y, z, 1, 0.2, 0.2, 0.2, 0.05);
            }
        }

        for (int i = 0; i < 15; i++) {
            double x = centerPos.getX() + (Math.random() - 0.5) * 2;
            double y = centerPos.getY();
            double z = centerPos.getZ() + (Math.random() - 0.5) * 2;

            level.sendParticles(ParticleTypes.ENCHANT, x, y, z, 3, 0.1, 0.1, 0.1, 0.8);
        }
    }

    @Override
    protected boolean hasSufficientMana(Player player) {
        return SpellUtil.hasEnoughMana(player, 10);
    }

    @Override
    protected boolean isOnCooldown(Player player) {
        return getCooldownFromPlayer(player, this) > 0;
    }

    @Override
    protected void consumeMana(Player player) {

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