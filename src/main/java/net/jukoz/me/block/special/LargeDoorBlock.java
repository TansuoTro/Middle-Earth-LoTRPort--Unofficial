package net.jukoz.me.block.special;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.Explosion;
import net.jukoz.me.MiddleEarth;
import org.jetbrains.annotations.Nullable;
import java.util.function.BiConsumer;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LargeDoorBlock extends Block {

    //TODO CLEAN CLASS

    public static final IntegerProperty PART = IntegerProperty.of("part", 0, 127);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE = BlockStateProperties.DOOR_HINGE;

    protected int doorHeight;
    protected int doorWidth;

    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

    public LargeDoorBlock(Settings settings) {
        super(settings.nonOpaque());
        doorHeight = 1;
        doorWidth = 1;
        this.setDefaultState((((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH)).with(getPart(), 0)).with(OPEN, false).with(HINGE, DoorHingeSide.LEFT));
    }

    //TODO improve this door to door invisibility
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) || super.isSideInvisible(state, stateFrom, direction);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getPart(), HORIZONTAL_FACING, OPEN, HINGE);
    }

    //Get the origin of the door based on the state and pos
    private BlockPos getOrigin(BlockPos pos, BlockState state){
        BlockPos blockPos;
        int part = state.get(getPart());

        int column = part / doorHeight;
        int line = part % doorHeight;

        if (state.get(OPEN)) {
            blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise().rotateYClockwise(), column);
            for (int i = 0; i < line; i++) {
                blockPos = blockPos.down();
            }
        } else {
            if(state.get(HINGE) == DoorHingeSide.LEFT) {
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), column);
            }else{
                blockPos = pos.offset(state.get(HORIZONTAL_FACING).rotateYClockwise(), column);
            }
            for (int i = 0; i < line; i++) {
                blockPos = blockPos.down();
            }
        }

       return blockPos;
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos1 = ctx.getBlockPos();
        Level world = ctx.getWorld();
        boolean canPlace = true;

        //Check space for placement
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                if(!world.getBlockState(blockPos).canReplace(ctx)){
                    canPlace = false;
                }
                blockPos = blockPos.up();
            }
            if(getHinge(ctx) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.offset(direction.rotateYClockwise(), j + 1);
            }else{
                blockPos = blockPos1.offset(direction.rotateYCounterclockwise(), j + 1);
            }
        }

        if(canPlace){
            return this.getDefaultState().with(HORIZONTAL_FACING, direction).with(OPEN, false).with(getPart(), 0).with(HINGE, this.getHinge(ctx));
        } else{
            return null;
        }
    }

    private DoorHingeSide getHinge(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos2 = blockPos.up();
        Direction direction2 = direction.rotateYCounterclockwise();
        BlockPos blockPos3 = blockPos.offset(direction2);
        BlockState blockState = blockView.getBlockState(blockPos3);
        BlockPos blockPos4 = blockPos2.offset(direction2);
        BlockState blockState2 = blockView.getBlockState(blockPos4);
        Direction direction3 = direction.rotateYClockwise();
        BlockPos blockPos5 = blockPos.offset(direction3);
        BlockState blockState3 = blockView.getBlockState(blockPos5);
        BlockPos blockPos6 = blockPos2.offset(direction3);
        BlockState blockState4 = blockView.getBlockState(blockPos6);
        int i = (blockState.isFullCube(blockView, blockPos3) ? -1 : 0) + (blockState2.isFullCube(blockView, blockPos4) ? -1 : 0) + (blockState3.isFullCube(blockView, blockPos5) ? 1 : 0) + (blockState4.isFullCube(blockView, blockPos6) ? 1 : 0);
        int j = direction.getOffsetX();
        int k = direction.getOffsetZ();
        Vec3 vec3d = ctx.getHitPos();
        double d = vec3d.x - (double)blockPos.getX();
        double e = vec3d.z - (double)blockPos.getZ();
        return (j >= 0 || !(e < 0.5)) && (j <= 0 || !(e > 0.5)) && (k >= 0 || !(d > 0.5)) && (k <= 0 || !(d < 0.5)) ? DoorHingeSide.LEFT : DoorHingeSide.RIGHT;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient){
            BlockPos blockPos = pos;
            //Places all additional blocks after placement is checked
            for (int i = 0; i < doorWidth; i++){
                int partIndex = doorHeight * i;
                for (int j = 0; j < doorHeight; j++) {
                    world.setBlockState(blockPos, (BlockState)state.with(getPart(), partIndex), 3);
                    blockPos = blockPos.up();
                    partIndex++;
                }
                if(state.get(HINGE) == DoorHingeSide.LEFT) {
                    blockPos = pos.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), i + 1);
                }else {
                    blockPos = pos.offset((Direction) state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), i + 1);
                }
            }
        }
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        //Breaks all blocks based on origin
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                world.breakBlock(blockPos, j == 0 && k == 0 && !player.isCreative());
                blockPos = blockPos.up();
            }
            if(state.get(OPEN)){
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
            } else if(state.get(HINGE) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
            }else {
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
            }
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onDestroyedByExplosion(Level world, BlockPos pos, Explosion explosion) {
        BlockState state = world.getBlockState(pos);
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        //Breaks all blocks based on origin
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                world.breakBlock(blockPos, j == 0 && k == 0);
                blockPos = blockPos.up();
            }
            if(state.get(OPEN)){
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
            } else if(state.get(HINGE) == DoorHingeSide.LEFT) {
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
            }else {
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
            }
        }

        super.onDestroyedByExplosion(world, pos, explosion);
    }

    //Check space for door opening/closing
    private boolean canToggle(BlockPos origin, BlockState state, Level world) {
        BlockPos blockPos = origin;
        BlockPos blockPos1 = blockPos;
        for (int j = 0; j < doorWidth; j++){
            for (int k = 0; k < doorHeight; k++) {
                if(j != 0){
                    if(!world.getBlockState(blockPos).isReplaceable()){
                        return false;
                    }
                }
                blockPos = blockPos.up();
            }
            if(state.get(OPEN)){
                if(state.get(HINGE) == DoorHingeSide.LEFT) {
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
                } else {
                    blockPos = blockPos1.offset((Direction) state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
                }
            } else {
                blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
            }
        }
        return true;
    }

    //Door opening
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockPos blockPos = getOrigin(pos, state);
        BlockPos blockPos1 = blockPos;

        if(canToggle(blockPos,state,world)){
            //Remove blocks
            for (int j = 0; j < doorWidth; j++){
                for (int k = 0; k < doorHeight; k++) {
                    world.setBlockState(blockPos, (BlockState)Blocks.AIR.getDefaultState(), 3);
                    blockPos = blockPos.up();
                }
                if(state.get(OPEN)) {
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), j + 1);
                }else {
                    if (state.get(HINGE) == DoorHingeSide.LEFT) {
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), j + 1);
                    }else{
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), j + 1);
                    }
                }
            }

            blockPos = blockPos1;

            //Place Blocks
            for (int i = 0; i < doorWidth; i++){
                int partIndex = doorHeight * i;
                for (int j = 0; j < doorHeight; j++) {
                    if(state.get(OPEN)){
                        world.setBlockState(blockPos, state.with(OPEN, false).with(getPart(), partIndex), 3);
                    } else {
                        world.setBlockState(blockPos, state.with(OPEN, true).with(getPart(), partIndex), 3);
                    }
                    blockPos = blockPos.up();
                    partIndex++;
                }
                if(!state.get(OPEN)){
                    blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING), i + 1);
                }else{
                    if(state.get(HINGE) == DoorHingeSide.LEFT){
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYClockwise(), i + 1);

                    } else {
                        blockPos = blockPos1.offset((Direction)state.get(HORIZONTAL_FACING).rotateYCounterclockwise(), i + 1);

                    }
                }
            }
            this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
            world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        } else {
            player.sendMessage(Component.translatable("alert.%s.large_door.blocked".formatted(MiddleEarth.MOD_ID)), true);
        }

        return InteractionResult.SUCCESS;
    }

    private void playOpenCloseSound(@Nullable Entity entity, Level world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? SoundEvents.BLOCK_WOODEN_DOOR_OPEN : SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundSource.BLOCKS, 1.5F, world.getRandom().nextFloat() * 0.1F + 0.4F);
    }

    public boolean isOpen(BlockState state) {
        return (Boolean)state.get(OPEN);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return (BlockState)state.with(HORIZONTAL_FACING, rotation.rotate((Direction)state.get(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : (BlockState)state.rotate(mirror.getRotation((Direction)state.get(HORIZONTAL_FACING)));
    }

    public int getDoorHeight() {
        return doorHeight;
    }

    public int getDoorWidth() {
        return doorWidth;
    }

    public IntegerProperty getPart() {
        return PART;
    }

    public boolean canPathfindThrough(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos, PathComputationType type) {
        return switch (type) {
            case LAND, AIR -> (Boolean) state.get(OPEN);
            case WATER -> false;
            default -> throw new IllegalStateException();
        };
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction direction = (Direction) state.get(HORIZONTAL_FACING);
        VoxelShape var10000 = null;
        if (state.get(HINGE) == DoorHingeSide.LEFT) {
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
