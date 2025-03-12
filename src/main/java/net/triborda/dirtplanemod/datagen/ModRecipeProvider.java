package net.triborda.dirtplanemod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.triborda.dirtplanemod.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        // dirt clump -> dirt
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIRT)
                .pattern("DD")
                .pattern("DD")
                .define('D', ModItems.DIRT_CLUMP.get())
                .unlockedBy(getHasName(ModItems.DIRT_CLUMP.get()), has(ModItems.DIRT_CLUMP.get()))
                .save(pWriter);
        // dirt clump -> dirt stick
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIRT_STICK.get())
                .pattern("D")
                .pattern("D")
                .define('D', ModItems.DIRT_CLUMP.get())
                .unlockedBy(getHasName(ModItems.DIRT_CLUMP.get()), has(ModItems.DIRT_CLUMP.get()))
                .save(pWriter);
        // dirt clump + dirt stick -> dirt pickaxe
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIRT_PICK_AXE.get())
                .pattern("DDD")
                .pattern(" S ")
                .pattern(" S ")
                .define('D', ModItems.DIRT_CLUMP.get())
                .define('S', ModItems.DIRT_STICK.get())
                .unlockedBy(getHasName(ModItems.DIRT_CLUMP.get()), has(ModItems.DIRT_CLUMP.get()))
                .save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIRT_PICK_AXE.get())
                .pattern("D")
                .pattern("S")
                .pattern("S")
                .define('D', ModItems.DIRT_CLUMP.get())
                .define('S', ModItems.DIRT_STICK.get())
                .unlockedBy(getHasName(ModItems.DIRT_CLUMP.get()), has(ModItems.DIRT_CLUMP.get()))
                .save(pWriter);

    }
}
