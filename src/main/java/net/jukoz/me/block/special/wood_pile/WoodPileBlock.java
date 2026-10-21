package net.jukoz.me.block.special.wood_pile;

import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.Random;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;

public class WoodPileBlock  extends BaseEntityBlock implements EntityBlock {

    public static final IntegerProperty STAGE = IntegerProperty.of("stage", 0, 2);
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape STAGE_0, STAGE_1, STAGE_2;

    public WoodPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH).with(STAGE, 0));
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, STAGE);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(STAGE, 0);
    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof WoodPileBlockEntity woodPileBlockEntity) {
                Containers.spawn(world, pos, woodPileBlockEntity);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClient) {
            return InteractionResult.SUCCESS;
        } else {
            if(player.isCreative()){
                world.setBlockState(pos, state.cycle(STAGE));
            } else {
                if (addStackRightClick(world, pos, player, hand)) {
                    MenuProvider screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openHandledScreen(screenHandlerFactory);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public boolean addStackRightClick(Level world, BlockPos pos, Player player, InteractionHand hand){
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty() && player.getStackInHand(hand).isIn(ItemTags.LOGS)) {
            for(int i = 0;i <= 8; i++){
                if (blockEntity.getStack(i).isEmpty()) {
                    blockEntity.setStack(i, player.getStackInHand(hand).copy());
                    player.getStackInHand(hand).setCount(0);
                }
            }
        } else {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodPileBlockEntity(pos,state);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(STAGE)) {
            case 2 -> STAGE_2;
            case 1 -> STAGE_1;
            default -> STAGE_0;
        };
    }

    static {
        STAGE_0 = Shapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 4, 16));

        STAGE_1 = Shapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 7, 16));

        STAGE_2 = Shapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 9, 16));
    }
}
