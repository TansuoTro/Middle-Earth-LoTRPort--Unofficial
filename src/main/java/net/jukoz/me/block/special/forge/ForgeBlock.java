package net.jukoz.me.block.special.forge;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.InteractionHand;
import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<ForgePart> PART = EnumProperty.of("part", ForgePart.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public ForgeBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(LIT, false).with(PART, ForgePart.BOTTOM));
    }



    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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

                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), ingotStack);
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), nuggetStack);
                }
                ItemScatterer.spawn(world, pos, forgeBlockEntity);
            }
        }
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getBlockPos().add(0,1,0);
        Level world = ctx.getWorld();
        Direction direction = ctx.getHorizontalPlayerFacing().getOpposite();

        return world.getBlockState(pos).canReplace(ctx) && world.getWorldBorder().contains(pos) ? (BlockState)this.getDefaultState().with(FACING, direction).with(PART, ForgePart.BOTTOM) : null;
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.add(0,1,0);
            world.setBlockState(blockPos, (BlockState)state.with(PART, ForgePart.TOP), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClient) {
            ForgePart forgePart = (ForgePart)state.get(PART);
            ForgePart forgePartOpposite = (ForgePart)state.get(PART).getOpposite(state.get(PART));
            BlockPos blockPos;
            if(forgePart == ForgePart.BOTTOM){
                blockPos = pos.add(0,1,0);
            } else {
                blockPos = pos.add(0,-1,0);
            }
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(this) && blockState.get(PART) == forgePartOpposite) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
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
        if (world.isClient) {
            return InteractionResult.SUCCESS;
        } else {
            if(state.get(PART) == ForgePart.BOTTOM){
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if(screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
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
                world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false);
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
            DefaultParticleType simpleParticleType = ParticleTypes.CAMPFIRE_COSY_SMOKE;
            world.addImportantParticle(simpleParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        }
    }
}
