package net.triborda.dirtplanemod.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.triborda.dirtplanemod.menu.custom.DirtWorkbenchMenu;

public class DirtWorkbenchBlock extends Block {
    public DirtWorkbenchBlock(Properties pProperties) {
        super(pProperties);
    }

    // acts as a crafting table
    @Override
    // Deprecated but no alternative
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer,
                                 InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide && pPlayer instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new SimpleMenuProvider(
                    (id, inventory, p) -> new DirtWorkbenchMenu(id, inventory, ContainerLevelAccess.create(pLevel, pPos), pLevel, pPos),
                    Component.translatable("container.crafting")
            ), pPos);
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    // breaks when item is crafted
}
