package net.jukoz.me.block.special.shelobiteeggs;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.spider.larva.ShelobiteLarvaEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ShelobiteLarvaEggBlock extends AbstractShelobiteLarvaEgg {
    private static final EnumProperty<Direction> FACING;

    public ShelobiteLarvaEggBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.defaultBlockState().with(FACING, Direction.EAST));
    }
    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        super.appendProperties(builder);
    }
    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                .with(FACING, ctx.getHorizontalDirection());
    }

    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        return world.getBlockState(blockPos).isSolidBlock(world, blockPos);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return !state.canPlaceAt(world, pos) ? Blocks.AIR.defaultBlockState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void onSteppedOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if(!entity.collidedSoftly && !(entity instanceof net.minecraft.world.entity.monster.Spider)){
            breakEgg(world, pos, state);
            super.onSteppedOn(world, pos, state, entity);
        }
    }

    @Override
    public void onLandedUpon(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if(!entity.collidedSoftly && !(entity instanceof net.minecraft.world.entity.monster.Spider)) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
            breakEgg(world, pos, state);
        }

    }

    public static void breakEgg(Level world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
        world.levelEvent(2001, pos, Block.getRawIdFromState(state));
        RandomSource random = RandomSource.create();
        int amountOfSpider = random.nextInt(3) + 1;
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    public static void SpawnSpider(BlockPos pos, Level world){
        ShelobiteLarvaEntity entity = new ShelobiteLarvaEntity(EntitiesME.SHELOBITE_LARVA, world);
        entity.age = 0;
        entity.refreshPositionAndAngles(pos, 0, 0);
        if(world instanceof ServerLevelAccessor serverWorldAccess) {
            entity.initialize(serverWorldAccess, serverWorldAccess.getEffectiveDifficulty(pos), MobSpawnType.NATURAL, null, null);
        }
        world.spawnEntity(entity);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.union(
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0));
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
