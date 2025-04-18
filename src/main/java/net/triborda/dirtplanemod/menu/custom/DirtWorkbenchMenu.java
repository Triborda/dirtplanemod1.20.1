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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.triborda.dirtplanemod.blocks.custom.DirtWorkbenchBlock.DirtCraftingContainer;
import net.triborda.dirtplanemod.DirtplaneMod;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DirtWorkbenchMenu extends RecipeBookMenu<DirtCraftingContainer> {
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
    private final Player player;
    private final DirtCraftingContainer container = new DirtCraftingContainer();
    private final Slot resultSlot;

    public DirtWorkbenchMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess, Level pLevel, BlockPos pPos) {
        super(MenuType.CRAFTING, pContainerId);
        this.level = pLevel;
        this.pos = pPos;
        this.player = pPlayerInventory.player;

        this.resultSlot = new Slot(this.resultContainer, 0, 124, 35) {
            private int removeCount;

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player pPlayer, ItemStack pStack) {
                this.checkTakeAchievements(pStack);
                Container craftingContainer = DirtWorkbenchMenu.this.container;
            }

            @Override
            public ItemStack remove(int pAmount) {
                if (this.hasItem())
                    this.removeCount += Math.min(pAmount, this.getItem().getCount());
                return super.remove(pAmount);
            }

            @Override
            protected void onQuickCraft(ItemStack pStack, int pAmount) {
                this.removeCount += pAmount;
                this.checkTakeAchievements(pStack);

                if (!level.isClientSide) {
                    level.destroyBlock(pos, false);
                    player.closeContainer();
                }
            }

            @Override
            protected void onSwapCraft(int pNumItemsCrafted) {
                this.removeCount = pNumItemsCrafted;
            }

            @Override
            protected void checkTakeAchievements(ItemStack pStack) {
                if (this.removeCount > 0)
                    pStack.onCraftedBy(DirtWorkbenchMenu.this.player.level(), DirtWorkbenchMenu.this.player, this.removeCount);
                if (this.container instanceof RecipeHolder recipeHolder)
                    recipeHolder.awardUsedRecipes(DirtWorkbenchMenu.this.player, List.of());
                this.removeCount = 0;
            }
        };

        this.addSlot(this.resultSlot);

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

    private static final int RESULT_SLOT = 0;
    private static final int CRAFTING_START = 1;
    private static final int CRAFTING_STOP = 9;
    private static final int INVENTORY_START = 10;
    private static final int INVENTORY_STOP = 45;
    private static final int HOTBAR_START = 37;

    @Override
    public ItemStack quickMoveStack(Player player, int idx) {
        ItemStack ret = ItemStack.EMPTY;
        Slot slot = this.slots.get(idx);
        if (!slot.hasItem())
            return ret;

        ItemStack item = slot.getItem();
        ret = item.copy();

        if (idx == RESULT_SLOT)
        {
            if (!this.moveItemStackTo(item, INVENTORY_START, INVENTORY_STOP + 1, true))
                return ItemStack.EMPTY;

            slot.onQuickCraft(item, ret);
        }
        else if (idx > INVENTORY_START && idx < INVENTORY_STOP + 1)
        {
            if (!this.moveItemStackTo(item, CRAFTING_START, CRAFTING_STOP + 1, false))
            {
                if (idx < HOTBAR_START)
                {
                    if (!this.moveItemStackTo(item, HOTBAR_START, INVENTORY_STOP + 1, false))
                        return ItemStack.EMPTY;
                }
                else if (!this.moveItemStackTo(item, INVENTORY_START, HOTBAR_START, false))
                    return ItemStack.EMPTY;
            }
        }
        else if (!this.moveItemStackTo(item, INVENTORY_START, INVENTORY_STOP + 1, false))
            return ItemStack.EMPTY;

        if (item.isEmpty())
                slot.set(ItemStack.EMPTY);
        else
            slot.setChanged();

        if (item.getCount() == ret.getCount())
            return ItemStack.EMPTY;

        return ret;
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

    @Override
    public void fillCraftSlotsStackedContents(StackedContents stackedContents) {
        this.craftingContainer.fillStackedContents(stackedContents);
    }

    @Override
    public void clearCraftingContent() {
        this.craftingContainer.clearContent();
        this.resultContainer.clearContent();
    }

    @Override
    public boolean recipeMatches(Recipe<? super DirtCraftingContainer> recipe) {
        return recipe.matches(this.container, this.player.level());
    }

    @Override
    public int getResultSlotIndex() {
        return 0;
    }

    @Override
    public int getGridWidth() {
        return 3;
    }

    @Override
    public int getGridHeight() {
        return 3;
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    @Override
    public boolean shouldMoveToInventory(int i) {
        return i != 0;
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
