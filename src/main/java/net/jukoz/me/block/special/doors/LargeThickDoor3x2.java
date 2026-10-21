package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.InteractionHand;
import net.jukoz.me.item.ResourceItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.world.entity.player.Player;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class LargeThickDoor3x2 extends LargeDoorBlock {
    public static final IntProperty PART = IntProperty.of("part", 0, 5);


    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 8);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 0, 8, 16, 16, 16);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(8, 0, 0, 16, 16, 16);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 16, 16);

    public LargeThickDoor3x2(Settings settings) {
        super(settings);
        this.doorHeight = 3;
        this.doorWidth  = 2;
    }

    @Override
    public IntProperty getPart() {
        return PART;
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(state.get(PART) == 4 && player.getMainHandStack().getItem() == ResourceItemsME.DWARVEN_KEY){
            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            return InteractionResult.PASS;
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = (Direction) state.get(HORIZONTAL_FACING);
        VoxelShape var10000 = null;
        if (state.get(HINGE) == DoorHinge.LEFT) {
            if (!state.get(OPEN)) {
                return switch (direction) {
                    case WEST -> EAST_SHAPE;
                    case EAST -> WEST_SHAPE;
                    case SOUTH -> NORTH_SHAPE;
                    default -> SOUTH_SHAPE;
                };
            } else {
                return switch (direction) {
                    case WEST -> SOUTH_SHAPE;
                    case EAST -> NORTH_SHAPE;
                    case SOUTH -> EAST_SHAPE;
                    default -> WEST_SHAPE;
                };
            }
        } else {
            if (!state.get(OPEN)) {
                return switch (direction) {
                    case WEST -> EAST_SHAPE;
                    case EAST -> WEST_SHAPE;
                    case SOUTH -> NORTH_SHAPE;
                    default -> SOUTH_SHAPE;
                };
            } else {
                return switch (direction) {
                    case WEST -> NORTH_SHAPE;
                    case EAST -> SOUTH_SHAPE;
                    case SOUTH -> WEST_SHAPE;
                    default -> EAST_SHAPE;
                };
            }
        }
    }
}