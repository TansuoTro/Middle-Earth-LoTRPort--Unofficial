package net.jukoz.me.block.special;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MirkwoodSpiderEggBlock extends Block {
    private static final DirectionProperty FACING;

    public MirkwoodSpiderEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.EAST));
    }
    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }
    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing());
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onSteppedOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.collidedSoftly && entity.getType() != ModEntities.MIRKWOOD_SPIDER){
            this.breakEgg(world, pos, state);
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    @Override
    public void onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.onLandedUpon(world, state, pos, entity, fallDistance);
        breakEgg(world, pos, state);
    }

    private void breakEgg(Level world, BlockPos pos, BlockState state) {
        world.playSound((Player)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        Random random = new Random();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    private void SpawnSpider(BlockPos pos, Level world){
        MirkwoodSpiderEntity entity = new MirkwoodSpiderEntity(ModEntities.MIRKWOOD_SPIDER, world);
        entity.age = 0;
        entity.setPos(pos.getX(), pos.getY() + 1, pos.getZ());
        world.spawnEntity(entity);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return VoxelShapes.union(
                Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 7.0, 11.0));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}

