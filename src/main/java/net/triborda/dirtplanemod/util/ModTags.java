package net.triborda.dirtplanemod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.triborda.dirtplanemod.DirtplaneMod;

import java.util.Objects;

public class ModTags {
    public static class Blocks {
        //public static final TagKey<Block> BLOCK_TAG_KEY = tag("block_tag_key");
        public static final TagKey<Block> NEEDS_DIRT_TOOL = tag("needs_dirt_tool");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(Objects.requireNonNull(ResourceLocation.tryBuild(DirtplaneMod.MOD_ID, name)));
        }
    }
    public static class Items {
        //public static final TagKey<Item> ITEM_TAG_KEY = tag("item_tag_key");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(Objects.requireNonNull(ResourceLocation.tryBuild(DirtplaneMod.MOD_ID, name)));
        }
    }
}
