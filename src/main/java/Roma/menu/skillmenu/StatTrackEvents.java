package Roma.menu.skillmenu;

import Roma.item.custom.ModPickaxeItem;
import Roma.menu.stats.ModStats;
import Roma.util.ModTags;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = "rma", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StatTrackEvents {

    private static final int RNG_MAX = 900;
    private static final float LEVEL_SCORE_MULTIPLIER = 10f;
    private static final float MATERIAL_SCORE_MULTIPLIER = 20f;

    private static final Map<UUID, Integer> batchMaterialCache = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> batchTickCache = new ConcurrentHashMap<>();

    private enum QualityTier {
        MASTERWORK("Masterwork", 995, 20, 4, "\u00a76Incredible! You crafted a Masterwork item!", ChatFormatting.GOLD, 4, 4.00f),
        FLAWLESS("Flawless", 800, 15, 3, "\u00a7dGreat job! You crafted a Flawless item.", ChatFormatting.DARK_PURPLE, 3, 2.80f),
        EXCEPTIONAL("Exceptional", 450, 10, 2, "\u00a7aWell done! You crafted an Exceptional item.", ChatFormatting.DARK_BLUE, 2, 1.75f),
        ADVANCED("Advanced", 250, 5, 1, "\u00a7eNice! You crafted an Advanced item.", ChatFormatting.DARK_GREEN, 1, 1.20f),
        STANDARD("Standard", Integer.MIN_VALUE, 0, 0, null, ChatFormatting.GRAY, 0, 1.00f);

        final String tagName;
        final int scoreThreshold;
        final int minLevelForRoll;
        final int unbreakingLevel;
        final String message;
        final ChatFormatting nameColor;
        final int minMaterialAvg;
        final float statMultiplier;

        QualityTier(String tagName, int scoreThreshold, int minLevelForRoll, int unbreakingLevel, String message, ChatFormatting nameColor, int minMaterialAvg, float statMultiplier) {
            this.tagName = tagName;
            this.scoreThreshold = scoreThreshold;
            this.minLevelForRoll = minLevelForRoll;
            this.unbreakingLevel = unbreakingLevel;
            this.message = message;
            this.nameColor = nameColor;
            this.minMaterialAvg = minMaterialAvg;
            this.statMultiplier = statMultiplier;
        }

        boolean isReached(float totalScore, int craftingLevel, int guaranteedFloorLevel, int avgMaterialLevel) {
            if (this == STANDARD) return true;
            if (avgMaterialLevel < this.minMaterialAvg) return false;

            boolean metByRoll = totalScore >= scoreThreshold && craftingLevel >= minLevelForRoll;
            boolean metByGuaranteedFloor = guaranteedFloorLevel > 0 && craftingLevel >= guaranteedFloorLevel;
            return metByRoll || metByGuaranteedFloor;
        }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        Player player = event.getEntity();
        ItemStack craftedItem = event.getCrafting();

        if (craftedItem.getItem() == Items.AIR || !(player instanceof ServerPlayer serverPlayer)) {
            return;
        }

        long currentTick = serverPlayer.level().getGameTime();
        UUID playerId = serverPlayer.getUUID();
        Container inventory = event.getInventory();

        if (craftedItem.is(ModTags.Items.XP_CRAFTING)) {
            serverPlayer.awardStat(ModStats.CUSTOM_ITEMS_CRAFTED.get());
        }

        int craftingLevel = SkillUtil.getSkillLevel(
                player,
                Stats.CUSTOM.get(ModStats.CUSTOM_ITEMS_CRAFTED.get()),
                x -> (int) (30 * Math.pow(1.357, x - 1))
        );

        int averageMaterialLevel;
        Long lastTick = batchTickCache.get(playerId);

        if (lastTick != null && lastTick == currentTick) {
            averageMaterialLevel = batchMaterialCache.getOrDefault(playerId, 1);
        } else {
            averageMaterialLevel = resolveAverageMaterialLevel(inventory);
            batchTickCache.put(playerId, currentTick);
            batchMaterialCache.put(playerId, averageMaterialLevel);
        }

        int rng = serverPlayer.level().random.nextInt(RNG_MAX + 1);

        float levelBonus = craftingLevel * LEVEL_SCORE_MULTIPLIER;
        float materialBonus = averageMaterialLevel * MATERIAL_SCORE_MULTIPLIER;
        float totalBonus = levelBonus + materialBonus;
        float totalScore = rng + totalBonus;

        QualityTier resultTier = QualityTier.STANDARD;
        for (QualityTier tier : QualityTier.values()) {
            int guaranteedFloor = switch (tier) {
                case FLAWLESS -> 30;
                case EXCEPTIONAL -> 20;
                case ADVANCED -> 15;
                default -> 0;
            };
            if (tier.isReached(totalScore, craftingLevel, guaranteedFloor, averageMaterialLevel)) {
                resultTier = tier;
                break;
            }
        }

        applyQuality(craftedItem, resultTier, averageMaterialLevel);
        if (craftedItem.isDamageableItem() && resultTier.unbreakingLevel > 0) {
            craftedItem.enchant(Enchantments.UNBREAKING, resultTier.unbreakingLevel);
        }

        Item craftedItemType = craftedItem.getItem();
        for (int i = 0; i < serverPlayer.getInventory().getContainerSize(); i++) {
            ItemStack invItem = serverPlayer.getInventory().getItem(i);
            if (!invItem.isEmpty() && invItem.getItem() == craftedItemType) {
                if (!invItem.getOrCreateTag().contains("Quality")) {
                    applyQuality(invItem, resultTier, averageMaterialLevel);
                    if (invItem.isDamageableItem() && resultTier.unbreakingLevel > 0) {
                        invItem.enchant(Enchantments.UNBREAKING, resultTier.unbreakingLevel);
                    }
                }
            }
        }

        String statOutput = String.format(" \u00a78[Lvl: %d | RNG: %d | Bonus: +%.0f]", craftingLevel, rng, totalBonus);

        if (resultTier.message != null) {
            serverPlayer.sendSystemMessage(Component.literal(resultTier.message + statOutput));
        } else if (resultTier == QualityTier.STANDARD) {
            serverPlayer.sendSystemMessage(Component.literal("\u00a7eYour materials or skill were too low, resulting in a Standard item." + statOutput));
        }
    }

    private static int resolveAverageMaterialLevel(Container inventory){
        int totalMaterialLevel = 0;
        int materialCount = 0;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack slotItem = inventory.getItem(i);
            if (slotItem.isEmpty()) continue;

            CompoundTag tag = slotItem.getTag();
            if (tag != null && tag.contains("MaterialLevel")) {
                int matLvl = 1;
                if (tag.contains("MaterialLevel", 99)) {
                    matLvl = tag.getInt("MaterialLevel");
                } else if (tag.contains("MaterialLevel", 8)) {
                    try {
                        matLvl = Integer.parseInt(tag.getString("MaterialLevel"));
                    } catch (NumberFormatException ignored) {}
                }
                totalMaterialLevel += Math.min(5, Math.max(1, matLvl));
            } else {
                totalMaterialLevel += 1;
            }
            materialCount++;
        }

        return materialCount > 0 ? (totalMaterialLevel / materialCount) : 1;
    }

    private static void applyQuality(ItemStack item, StatTrackEvents.QualityTier tier, int materialLevel){
        item.getOrCreateTag().putString("Quality", tier.tagName);
        item.getOrCreateTag().putInt("MaterialLevel", materialLevel);

        String baseName = item.getHoverName().getString();
        Component newName = Component.literal(tier.tagName + " " + baseName).withStyle(tier.nameColor);
        item.setHoverName(newName);

        if (tier.statMultiplier > 1.0f) {
            List<ModData> cachedStats = new ArrayList<>();
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> modifiers = item.getAttributeModifiers(slot);
                for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
                    cachedStats.add(new ModData(slot, entry.getKey(), entry.getValue()));
                }
            }

            if (item.hasTag() && item.getTag().contains("AttributeModifiers")) {
                item.getTag().remove("AttributeModifiers");
            }

            for (ModData data : cachedStats) {
                double amount = data.modifier.getAmount();

                ResourceLocation attrId = ForgeRegistries.ATTRIBUTES.getKey(data.attribute);

                if (attrId != null && tier.statMultiplier > 1.0f) {
                    String path = attrId.toString();
                    if (path.equals("minecraft:generic.attack_damage") ||
                            path.equals("minecraft:generic.armor") ||
                            path.equals("minecraft:generic.armor_toughness") ||
                            path.equals("minecraft:generic.mining_speed"))
                    {
                        amount *= tier.statMultiplier;
                    }
                }

                AttributeModifier newMod = new AttributeModifier(data.modifier.getId(), data.modifier.getName(), amount, data.modifier.getOperation());
                item.addAttributeModifier(data.attribute, newMod, data.slot);
            }
        }

        // Apply bitmask to hide messy Vanilla stat block
        int hideFlags = item.getOrCreateTag().getInt("HideFlags");
        item.getOrCreateTag().putInt("HideFlags", hideFlags | 2);
    }

    public static float getUtilityMultiplier(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Quality")) {
            return switch (stack.getTag().getString("Quality")) {
                case "Masterwork" -> 1.50f;  // +25% boost to Speed/Durability
                case "Flawless" -> 1.20f;    // +15%
                case "Exceptional" -> 1.10f; // +10%
                case "Advanced" -> 1.05f;    // +5%
                default -> 1.0f;
            };
        }
        return 1.0f;
    }

    // --- APPLY MINING SPEED IN-GAME ---
    @SubscribeEvent
    public static void onMineBlock(PlayerEvent.BreakSpeed event) {
        ItemStack tool = event.getEntity().getMainHandItem();
        float utilityMult = getUtilityMultiplier(tool);

        // Multiply their breaking speed by the smaller utility factor
        if (utilityMult > 1.0f) {
            event.setNewSpeed(event.getOriginalSpeed() * utilityMult);
        }
    }

    /**
     * DYNAMIC LORE RENDERER
     * Runs natively without writing fragile NBT JSON blocks!
     */
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();
        Player player = event.getEntity();

        // 1. REAL-TIME DURABILITY & UNBREAKING MATH
        if (item.isDamageableItem()) {
            int maxDurability = item.getMaxDamage();
            int currentDurability = maxDurability - item.getDamageValue();
            int unbreakingLevel = item.getEnchantmentLevel(Enchantments.UNBREAKING);

            StringBuilder durabilityBuilder = new StringBuilder();
            durabilityBuilder.append("Durability: ")
                    .append(currentDurability)
                    .append(" / ")
                    .append(maxDurability);

            // Calculate Effective Durability if item has Unbreaking
            if (unbreakingLevel > 0) {
                int effectiveCurrent;
                int effectiveMax;

                if (item.getItem() instanceof ArmorItem) {
                    // Armor Unbreaking math: 60% + (40% / (lvl + 1)) chance to take damage
                    float armorMultiplier = 1.0f / (0.6f + (0.4f / (unbreakingLevel + 1)));
                    effectiveCurrent = Math.round(currentDurability * armorMultiplier);
                    effectiveMax = Math.round(maxDurability * armorMultiplier);
                } else {
                    // Tool/Weapon Unbreaking math: (lvl + 1) multiplier
                    effectiveCurrent = currentDurability * (unbreakingLevel + 1);
                    effectiveMax = maxDurability * (unbreakingLevel + 1);
                }

                durabilityBuilder.append(" (\u00a7b~")
                        .append(effectiveCurrent)
                        .append(" Effective\u00a7r)");
            }

            event.getToolTip().add(Component.literal(durabilityBuilder.toString()).withStyle(ChatFormatting.GRAY));
        }

        // 2. STAT SCANNING & COMBAT CALCULATIONS
        if (item.hasTag() && item.getTag().contains("Quality")) {

            double damage = 0;
            double speed = 0;
            double armor = 0;
            double toughness = 0;
            double attributeMining = 0;

            double bonusDamage = 0;
            if (player != null) {
                int combatLevel = SkillUtil.getSkillLevel(
                        player,
                        Stats.CUSTOM.get(Stats.MOB_KILLS),
                        x -> (int) (25 * Math.pow(1.20, x - 1))
                );
                bonusDamage = combatLevel * 1.5F;
            }

            // Dynamically scan attribute modifiers on the item
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Multimap<Attribute, AttributeModifier> modifiers = item.getAttributeModifiers(slot);
                for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
                    ResourceLocation attrId = ForgeRegistries.ATTRIBUTES.getKey(entry.getKey());
                    if (attrId != null) {
                        String path = attrId.toString();
                        if (path.equals("minecraft:generic.attack_damage")) damage += entry.getValue().getAmount() + bonusDamage;
                        else if (path.equals("minecraft:generic.attack_speed")) speed += entry.getValue().getAmount();
                        else if (path.equals("minecraft:generic.armor")) armor += entry.getValue().getAmount();
                        else if (path.equals("minecraft:generic.armor_toughness")) toughness += entry.getValue().getAmount();
                        else if (path.equals("minecraft:generic.mining_speed")) attributeMining += entry.getValue().getAmount();
                    }
                }
            }

            // 3. MINING SPEED CALCULATION (Base Tier + Attributes)
            Item itemType = item.getItem();
            if (itemType instanceof DiggerItem digger) {
                float baseMiningSpeed = digger.getTier().getSpeed();

                // Multiply the total speed by the Utility multiplier we just created
                double totalMiningSpeed = (baseMiningSpeed + attributeMining) * getUtilityMultiplier(item);

                event.getToolTip().add(Component.literal("Mining Speed: " + formatStat(totalMiningSpeed)).withStyle(ChatFormatting.DARK_GREEN));
            }

            // 4. DISPLAY COMBAT STATS
            if (damage > 0) {
                event.getToolTip().add(Component.literal("Attack Damage: " + formatStat(damage + 1.0)).withStyle(ChatFormatting.DARK_RED));
            }
            if (speed != 0) {
                event.getToolTip().add(Component.literal("Attack Speed: " + formatStat(4.0 + speed)).withStyle(ChatFormatting.GOLD));
            }
            if (armor > 0) {
                event.getToolTip().add(Component.literal("Armor: " + formatStat(armor)).withStyle(ChatFormatting.DARK_AQUA));
            }
            if (toughness > 0) {
                event.getToolTip().add(Component.literal("Armor Toughness: " + formatStat(toughness)).withStyle(ChatFormatting.DARK_AQUA));
            }
        }
    }

    private static String formatStat(double value){
        if (value == Math.floor(value)) {
            return String.valueOf((int) value);
        }
        return String.format("%.1f", value);
    }

    @SubscribeEvent
    public static void onMobKilled (LivingDeathEvent event){
        if (event.getSource().getEntity() instanceof ServerPlayer player
                && event.getEntity().getType().is(ModTags.EntityTypes.XPMOBS)) {
            player.awardStat(ModStats.CUSTOM_MOBS_KILLED.get());
        }
    }

    @SubscribeEvent
    public static void onPlantBroken (BlockEvent.BreakEvent event){
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer
                && !serverPlayer.level().isClientSide()
                && event.getState().is(ModTags.Blocks.XPPLANTS)) {
            serverPlayer.awardStat(ModStats.CUSTOM_PLANTS_BROKEN.get());
        }
    }

    @SubscribeEvent
    public static void onOreMined (BlockEvent.BreakEvent event){
        BlockState state = event.getState();
        Player player = event.getPlayer();
        if (player instanceof ServerPlayer serverPlayer
                && !serverPlayer.level().isClientSide()
                && state.is(ModTags.Blocks.XPSTONE)) {
            serverPlayer.awardStat(ModStats.XP_MINED.get());
        }
    }

    private static class ModData {
        final EquipmentSlot slot;
        final Attribute attribute;
        final AttributeModifier modifier;
        ModData(EquipmentSlot slot, Attribute attribute, AttributeModifier modifier) {
            this.slot = slot;
            this.attribute = attribute;
            this.modifier = modifier;
        }
    }
}