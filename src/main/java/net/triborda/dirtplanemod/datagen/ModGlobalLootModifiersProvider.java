package net.triborda.dirtplanemod.datagen;


import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.loot.AddItemModifier;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.minecraft.world.item.Items;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {


    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, DirtplaneMod.MOD_ID);
    }

    @Override
    protected void start() {
        add("test_iron_from_grass_block", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS_BLOCK).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.IRON_INGOT)).build()},
                Items.IRON_INGOT));

        add("test_iron_from_dirt_block", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.DIRT).build(),
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.IRON_INGOT)).build()},
                Items.IRON_INGOT));
    }
}
