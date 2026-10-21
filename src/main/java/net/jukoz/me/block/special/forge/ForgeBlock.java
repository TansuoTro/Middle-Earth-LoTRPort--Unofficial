package net.jukoz.me.block.special.forge;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.InteractionHand;
import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.Containers;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.math.Axis;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ForgeBlock extends BaseEntityBlock implements EntityBlock {
    public static final EnumProperty<ForgePart> PART = EnumProperty.create("part", ForgePart.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ForgeBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.defaultBlockState()).with(FACING, Direction.NORTH)).with(LIT, false).with(PART, ForgePart.BOTTOM));
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof ForgeBlockEntity forgeBlockEntity) {
            if (state.get(PART) == ForgePart.BOTTOM){
                MetalTypes metal = forgeBlockEntity.getCurrentMetal();
                int storage = forgeBlockEntity.getStorage();

                if (metal != MetalTypes.EMPTY){
                    ItemStack ingotStack = new ItemStack(metal.getIngot(), storage / 144);
                    MEComponents.set(ingotStack, MEComponents.TEMPERATURE_DATA, new TemperatureDataComponent(1000));
                    ItemStack nuggetStack = new ItemStack(metal.getNugget(), storage % 144 / 16);
                    MEComponents.set(nuggetStack, MEComponents.TEMPERATURE_DATA, new TemperatureDataComponent(1000));

                    Containers.spawn(world, pos.getX(), pos.getY(), pos.getZ(), ingotStack);
                    Containers.spawn(world, pos.getX(), pos.getY(), pos.getZ(), nuggetStack);
                }
                Containers.spawn(world, pos, forgeBlockEntity);
            }
        }
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getBlockPos().add(0,1,0);
        Level world = ctx.getWorld();
        Direction direction = ctx.getHorizontalDirection().getOpposite();

        return world.getBlockState(pos).canReplace(ctx) && world.getWorldBorder().contains(pos) ? (BlockState)this.defaultBlockState().with(FACING, direction).with(PART, ForgePart.BOTTOM) : null;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.add(0,1,0);
            world.setBlock(blockPos, (BlockState)state.setValue(PART, ForgePart.TOP), 3);
            world.updateNeighbours(pos, Blocks.AIR);
            state.updateNeighbours(world, pos, 3);
        }
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            ForgePart forgePart = (ForgePart)state.get(PART);
            ForgePart forgePartOpposite = (ForgePart)state.get(PART).getOpposite(state.get(PART));
            BlockPos blockPos;
            if(forgePart == ForgePart.BOTTOM){
                blockPos = pos.add(0,1,0);
            } else {
                blockPos = pos.add(0,-1,0);
            }
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.is(this) && blockState.get(PART) == forgePartOpposite) {
                world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(LIT);
        builder.add(PART);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if(state.get(PART) == ForgePart.BOTTOM){
                MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                if(screenHandlerFactory != null) {
                    player.openMenu(screenHandlerFactory);
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, ModBlockEntities.FORGE);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<ForgeBlockEntity> expectedType) {
        BlockEntityTicker ticker;
        if (world instanceof ServerLevel serverWorld) {
            ticker = checkType(givenType, expectedType, (worldx, pos, state, blockEntity) -> {
                ForgeBlockEntity.tick(serverWorld, pos, state, blockEntity);
            });
        } else {
            ticker = null;
        }

        return ticker;
    }

    @Override
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!state.get(LIT).booleanValue()) {
            return;
        }
        if (state.get(PART) == ForgePart.BOTTOM){
            double d = (double)pos.getX() + 0.5;
            double e = pos.getY();
            double f = (double)pos.getZ() + 0.5;
            if (random.nextDouble() < 0.1) {
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0f, 1.0f, false);
            }
            Direction direction = state.get(FACING);
            Direction.Axis axis = direction.getAxis();
            double g = 0.52;
            double h = random.nextDouble() * 0.6 - 0.3;
            double i = axis == Direction.Axis.X ? (double)direction.getOffsetX() * 0.52 : h;
            double j = random.nextDouble() * 6.0 / 16.0;
            double k = axis == Direction.Axis.Z ? (double)direction.getOffsetZ() * 0.52 : h;
            world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0, 0.0, 0.0);
        } else {
            SimpleParticleType simpleParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addImportantParticle(simpleParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        }
    }
}
