package net.triborda.dirtplanemod.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.triborda.dirtplanemod.DirtplaneMod;
import net.triborda.dirtplanemod.util.ModTags;

import java.util.List;

public class ModToolTiers {
    public static final Tier DIRT = TierSortingRegistry.registerTier(
            new ForgeTier(0, 5, 1f, 0f, 5,
                    ModTags.Blocks.NEEDS_DIRT_TOOL, () -> Ingredient.of(ModItems.DIRT_CLUMP.get())),
            ResourceLocation.tryBuild(DirtplaneMod.MOD_ID, "dirt"), List.of(), List.of(Tiers.WOOD));

}
