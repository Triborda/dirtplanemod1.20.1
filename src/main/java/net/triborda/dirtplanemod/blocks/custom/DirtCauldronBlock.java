package net.triborda.dirtplanemod.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.triborda.dirtplanemod.item.ModItems;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.Level;

public class DirtCauldronBlock extends Block {
    public static final VoxelShape SHAPE = Block.box(0,0,0,16,16,16);

    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water_level", 0, 4);
    public static final BooleanProperty HAS_DIRT_CLUMP = BooleanProperty.create("has_dirt_clump");


    public DirtCauldronBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(WATER_LEVEL, 0)
                .setValue(HAS_DIRT_CLUMP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATER_LEVEL, HAS_DIRT_CLUMP);
    }


    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);
        boolean updated = false;
        if (!level.isClientSide) {

            // Add water
            if (heldItem.getItem() == Items.WATER_BUCKET && state.getValue(WATER_LEVEL) == 0) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, 4), 3);
                if (!player.isCreative()) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                updated = true;
            }

            // Add Dirt Clump
            else if (heldItem.getItem() == ModItems.DIRT_CLUMP.get() && !state.getValue(HAS_DIRT_CLUMP) && !(state.getValue(WATER_LEVEL) <= 0)) {
                level.setBlock(pos, state.setValue(HAS_DIRT_CLUMP, true), 3);
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                updated = true;
            }

            // Add Plant Matter
            else if (heldItem.getItem() == ModItems.PLANT_MATTER.get() && !(state.getValue(WATER_LEVEL) >= 4)) {
                level.setBlock(pos, state.setValue(WATER_LEVEL, (state.getValue(WATER_LEVEL)+1)), 3);
                if (!player.isCreative()) {
                    heldItem.shrink(1);
                }
                updated = true;
            }

            // Check for crafting condition
            BlockState newState = level.getBlockState(pos);
            if (newState.getValue(WATER_LEVEL) > 0 &&
                    newState.getValue(HAS_DIRT_CLUMP)) {

                // Spawn Dirt Sapling
                ItemEntity result = new ItemEntity(level,
                        pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5,
                        new ItemStack(ModItems.DIRT_SAPLING.get()));
                level.addFreshEntity(result);

                // Reset dirt + plant matter (decrement water)
                level.setBlock(pos, newState
                        .setValue(HAS_DIRT_CLUMP, false)
                        .setValue(WATER_LEVEL, (newState.getValue(WATER_LEVEL)-1)), 3);
            }

            return updated ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return updated ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
