package net.triborda.dirtplanemod.datagen;



import com.mojang.datafixers.util.Either;
import net.minecraft.advancements.critereon.*;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
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


        add("dirt_clump_from_grass_block", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS_BLOCK).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(
                                ItemPredicate.Builder.item()
                                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                        )).build()},
                ModItems.DIRT_CLUMP.get(), 4));
        add("dirt_clump_from_dirt", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(
                                ItemPredicate.Builder.item()
                                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                        )).build()},
                ModItems.DIRT_CLUMP.get(), 4));


        add("not_dirt_from_dirt", new RemoveItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                InvertedLootItemCondition.invert(
                        MatchTool.toolMatches(
                                ItemPredicate.Builder.item()
                                        .hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1)))
                        )).build()},
                Items.DIRT));
        add("not_dirt_from_grass_block", new RemoveItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS_BLOCK).build()},
                Items.DIRT));


    }
}
