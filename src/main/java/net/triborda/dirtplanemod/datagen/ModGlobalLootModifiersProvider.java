package net.triborda.dirtplanemod.datagen;


import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.item.ModItems;
import net.triborda.dirtplanemod.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraft.world.item.Items;
import net.triborda.dirtplanemod.loot.RemoveItemModifier;

import java.util.List;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {


    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, DirtplaneMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("plant_matter_from_grass_block", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS_BLOCK).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.DIRT_PICK_AXE.get())).build()},
                ModItems.PLANT_MATTER.get(), 1));

        add("plant_matter_from_dirt", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.DIRT_PICK_AXE.get())).build()},
                ModItems.PLANT_MATTER.get(), 1));
        add("dirt_clump_from_grass_block", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.DIRT_PICK_AXE.get()))).build()},
                ModItems.DIRT_CLUMP.get(), 4));
        add("dirt_clump_from_dirt", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(ItemPredicate.Builder.item().of(ModItems.DIRT_PICK_AXE.get()))).build()},
                ModItems.DIRT_CLUMP.get(), 4));


        add("not_dirt_from_dirt", new RemoveItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build()},
                Items.DIRT));
    }
}
