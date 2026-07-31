package Roma.util;

import Roma.roma;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static class Items {
        public static final TagKey<Item> KEEPONDEATH = createItemTag("keepondeath");
        public static final TagKey<Item> RESTRICTED_CRAFTING_TEN = createItemTag("restricted_crafting_ten");
        public static final TagKey<Item> RESTRICTED_CRAFTING_FIVETEN = createItemTag("restricted_crafting_fiveten");
        public static final TagKey<Item> RESTRICTED_CRAFTING_TWENTY = createItemTag("restricted_crafting_twenty");
        public static final TagKey<Item> RESTRICTED_CRAFTING_FIVE = createItemTag("restricted_crafting_five");
        public static final TagKey<Item> XP_CRAFTING = createItemTag("xp_crafting");
    }

    public static class EntityTypes {
        // FIXED: Now uses <?> wildcard and calls createEntityTypeTag
        public static final TagKey<EntityType<?>> XPMOBS = createEntityTypeTag("xpmobs");
    }

    public static class Blocks {
        public static final TagKey<Block> WOOD = createTag("wood");
        public static final TagKey<Block> STONE = createTag("stone");
        public static final TagKey<Block> STONE_ORE_REPLACEABLES = createTag("stone_ore_replaceables");

        public static final TagKey<Block> XPSTONE = createTag("xpstone");
        public static final TagKey<Block> XPPLANTS = createTag("xpplants");

        public static final TagKey<Block> NEEDSWOODENTOOL = createTag("needswoodentool");
        public static final TagKey<Block> NEEDSSTONETOOL = createTag("needsstonetool");
        public static final TagKey<Block> NEEDSCOPPERTOOL = createTag("needscoppertool");
        public static final TagKey<Block> NEEDSIRONTOOL = createTag("needsirontool");
        public static final TagKey<Block> NEEDSBRASSTOOL = createTag("needsbrasstool");
        public static final TagKey<Block> NEEDSBRONZETOOL = createTag("needsbronzetool");
        public static final TagKey<Block> NEEDSLSTEELTOOL = createTag("needslsteeltool");
        public static final TagKey<Block> NEEDSHSTEELTOOL = createTag("needshsteeltool");
        public static final TagKey<Block> NEEDSSUPERALLOYTOOL = createTag("needssuperalloytool");
    }

    private static TagKey<Block> createTag(String name) {
        return BlockTags.create(new ResourceLocation(roma.MOD_ID, name));
    }

    private static TagKey<Item> createItemTag(String name) {
        return ItemTags.create(new ResourceLocation(roma.MOD_ID, name));
    }

    // FIXED: Uses TagKey.create(Registries.ENTITY_TYPE, ...) with correct return type
    private static TagKey<EntityType<?>> createEntityTypeTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(roma.MOD_ID, name));
    }
}