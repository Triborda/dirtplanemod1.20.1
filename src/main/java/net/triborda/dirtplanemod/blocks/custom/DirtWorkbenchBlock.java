package net.triborda.dirtplanemod.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.triborda.dirtplanemod.menu.custom.DirtWorkbenchMenu;

import java.util.List;

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

    public static class DirtCraftingContainer implements CraftingContainer {
        private final NonNullList<ItemStack> items;

        public DirtCraftingContainer () {
            this.items = NonNullList.withSize(9, ItemStack.EMPTY);
        }

        @Override
        public int getWidth() {
            return 3;
        }

        @Override
        public int getHeight() {
            return 3;
        }

        @Override
        public List<ItemStack> getItems() {
            return items;
        }

        @Override
        public int getContainerSize() {
            return items.size();
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack itemStack : items) {
                if (!itemStack.isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public ItemStack getItem(int i) {
            return items.get(i);
        }

        @Override
        public ItemStack removeItem(int i, int i1) {
            return ContainerHelper.removeItem(items, i, i1);
        }

        @Override
        public ItemStack removeItemNoUpdate(int i) {
            ItemStack itemStack = items.get(i);
            if (itemStack.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                items.set(i, ItemStack.EMPTY);
                return itemStack;
            }
        }

        @Override
        public void setItem(int i, ItemStack itemStack) {
            items.set(i, itemStack);
        }

        @Override
        public void setChanged() {
            // Notify the container that the contents have changed
        }

        @Override
        public boolean stillValid(Player player) {
            return true;
        }

        @Override
        public void clearContent() {
            items.clear();
        }

        @Override
        public void fillStackedContents(StackedContents stackedContents) {
            for (ItemStack itemStack : items) {
                if (!itemStack.isEmpty()) {
                    stackedContents.accountStack(itemStack);
                }
            }
        }
    }
}
