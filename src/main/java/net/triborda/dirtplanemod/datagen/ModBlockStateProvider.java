package net.triborda.dirtplanemod.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.blocks.ModBlocks;
import net.triborda.dirtplanemod.blocks.custom.DirtCauldronBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DirtplaneMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(ModBlocks.DIRT_CAULDRON.get()).forAllStates(state -> {
            int waterLevel = state.getValue(DirtCauldronBlock.WATER_LEVEL);
            boolean hasDirt = state.getValue(DirtCauldronBlock.HAS_DIRT_CLUMP);

            String suffix = "";
            if (waterLevel > 0 ) {
                suffix = "_lvl" + waterLevel;
                if (hasDirt) suffix += "_dirt";
            }

            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/dirt_cauldron" + suffix)))
                    .build();
        });

        simpleBlockWithItem(ModBlocks.DIRT_WORKBENCH.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/dirt_workbench")));
    }

/*
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
*/
}
