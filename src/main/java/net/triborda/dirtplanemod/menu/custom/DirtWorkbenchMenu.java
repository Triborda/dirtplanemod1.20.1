package net.triborda.dirtplanemod.menu.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.triborda.dirtplanemod.DirtplaneMod;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DirtWorkbenchMenu extends AbstractContainerMenu {
    private final Level level;
    private final BlockPos pos;
    private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private final CraftingContainer craftingContainer = new CraftingContainer() {
        @Override
        public void fillStackedContents(StackedContents stackedContents) {

        }

        @Override
        public int getContainerSize() {
            return 9;
        }

        @Override
        public boolean isEmpty() {
            for (int i = 0; i < this.getContainerSize(); i++) {
                if (!this.getItem(i).isEmpty()) {
                    return false;
                }
            }
            return true;
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
        public ItemStack getItem(int index) {
            return items.get(index);
        }

        @Override
        public void setItem(int index, ItemStack stack) {
            items.set(index, stack);
            this.setChanged();
        }

        @Override
        public void setChanged() {
            DirtWorkbenchMenu.this.slotsChanged(this);
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
        public ItemStack removeItem(int index, int count) {
            return ContainerHelper.removeItem(items, index, count);
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
        public List<ItemStack> getItems() {
            return items;
        }
    };
    private final ResultContainer resultContainer = new ResultContainer();

    public DirtWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess, Level pLevel, BlockPos pPos) {
        super(MenuType.CRAFTING, pContainerId);
        this.level = pLevel;
        this.pos = pPos;

        this.addSlot(new CustomResultSlot(pPlayerInventory.player, this.craftingContainer, this.resultContainer, 0, 124, 35));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftingContainer, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(pPlayerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(pPlayerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void slotsChanged(Container container) {
        if (!level.isClientSide) {
            RecipeManager recipeManager = level.getRecipeManager();

            // Find the matching recipe
            java.util.Optional<CraftingRecipe> recipe = recipeManager.getRecipeFor(RecipeType.CRAFTING, craftingContainer, level);

            // Update the result container
            if (recipe.isPresent()) {
                resultContainer.setRecipeUsed(recipe.get());
                resultContainer.setItem(0, recipe.get().assemble(craftingContainer, level.registryAccess()));
            } else {
                resultContainer.setItem(0, ItemStack.EMPTY);
            }
        }
    }

    private class CustomResultSlot extends ResultSlot {
        public CustomResultSlot(Player player, CraftingContainer craftSlots, Container resultSlots, int index, int x, int y) {
            super(player, craftSlots, resultSlots, index, x, y);
        }


        @Override
        public void onTake(Player player, ItemStack stack) {
            super.onTake(player, stack);

            if (!level.isClientSide) {
                level.destroyBlock(pos, false);
                player.closeContainer();
            }
        }
    }
}
