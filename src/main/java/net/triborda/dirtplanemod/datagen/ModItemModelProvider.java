package net.triborda.dirtplanemod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.blocks.ModBlocks;
import net.triborda.dirtplanemod.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DirtplaneMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.DIRT_CLUMP);
        simpleItem(ModItems.PLANT_MATTER);
        simpleItem(ModItems.DIRT_STICK);
        simpleItem(ModItems.DIRT_SAPLING);

        handheldItem(ModItems.DIRT_PICK_AXE);
        handheldItem(ModItems.DIRT_SHOVEL);

        evenSimplerBlockItem(ModBlocks.DIRT_CAULDRON);
    }

    // this appears as a 2d item when viewing in 3rd person
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.tryBuild(DirtplaneMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    // this appears as handheld like a tool when viewing in 3rd person
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/handheld")).texture("layer0",
                ResourceLocation.tryBuild(DirtplaneMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    // this allows to use the block model for inventory
    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(DirtplaneMod.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }
}
