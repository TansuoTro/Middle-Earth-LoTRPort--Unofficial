package net.jukoz.me.block.special.plants;

import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.item.FoodItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import net.minecraft.world.level.gameevent.GameEvent;

public class ToughBerryBushBlock extends CustomPlantBlock
        implements Fertilizable {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = BlockStateProperties.AGE_3;
    private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public ToughBerryBushBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public ItemStack getPickStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(FoodItemsME.TOUGH_BERRIES);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.get(AGE) == 0) {
            return SMALL_SHAPE;
        }
        if (state.get(AGE) < 3) {
            return LARGE_SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int i = state.get(AGE);
        if (i < 3 && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            BlockState blockState = (BlockState) state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @Override
    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntitiesME.WARG || entity.getType() == EntityType.FOX || entity.getType() == EntityType.BAT) {
            entity.slowMovement(state, new Vec3(0.800000011920929, 0.75, 0.800000011920929));
            if (world instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)world;
                if ((Integer)state.get(AGE) != 0) {
                    Vec3 vec3d = new Vec3(entity.getX() - entity.lastRenderX, 0.0, entity.getZ() - entity.lastRenderZ);
                    if (vec3d.horizontalLengthSquared() > 0.0) {
                        double d = Math.abs(vec3d.getX());
                        double e = Math.abs(vec3d.getZ());
                        if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                            entity.damage(world.getDamageSources().sweetBerryBush(), 1.0F);
                        }
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean bl;
        int i = state.get(AGE);
        boolean bl2 = bl = i == MAX_AGE;
        if (!bl && player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }
        if (i > 1) {
            int j = 1 + world.random.nextInt(2);
            ToughBerryBushBlock.dropStack(world, pos, new ItemStack(FoodItemsME.TOUGH_BERRIES, j + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0f, 0.8f + world.random.nextFloat() * 0.4f);
            BlockState blockState = (BlockState) state.with(AGE, 1);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return InteractionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < MAX_AGE;
    }

    @Override
    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int i = Math.min(MAX_AGE, state.get(AGE) + 1);
        world.setBlockState(pos, (BlockState) state.with(AGE, i), Block.NOTIFY_LISTENERS);
    }
}
