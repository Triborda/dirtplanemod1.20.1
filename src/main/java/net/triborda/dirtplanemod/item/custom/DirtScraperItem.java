package net.triborda.dirtplanemod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.triborda.dirtplanemod.item.ModItems;

public class DirtScraperItem extends Item {
    public DirtScraperItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Player player = pContext.getPlayer();
        ItemStack stack = pContext.getItemInHand();

        if (!level.isClientSide) {
            if (level.getBlockState(pos).is(Blocks.GRASS_BLOCK)) {
                level.setBlockAndUpdate(pos, Blocks.DIRT.defaultBlockState());

                if (player != null) {
                    ItemStack plantMatter = new ItemStack(ModItems.PLANT_MATTER.get());
                    if (!player.addItem(plantMatter)) {
                        player.drop(plantMatter, false);
                    }
                }

                if (!player.isCreative()) {
                    stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(pContext.getHand()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
