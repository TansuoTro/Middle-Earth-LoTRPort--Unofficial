package net.jukoz.me.block.special.crockpot;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.special.bellows.BellowsBlock;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CrockpotBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public CrockpotBlock(BlockBehaviour.Properties settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(HANGING, false));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        BlockEntity blockEntity = player.level().getBlockEntity(pos);

        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            boolean filled = crockpotBlockEntity.fill(stack);
            if(filled) {
                ItemStack remainder = stack.getCraftingRemainingItem();
                player.setStackInHand(hand, ItemUtils.exchangeStack(stack, player, remainder).copy());
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.1F);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                return InteractionResult.SUCCESS;
            } else {
                if(stack.isEmpty()) {
                    MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                    if(screenHandlerFactory != null) {
                        player.openMenu(screenHandlerFactory);
                    }
                    return InteractionResult.CONSUME;
                } else {
                    ItemStack stackResult = crockpotBlockEntity.fillBowl(stack.getItem());
                    if(!stackResult.isEmpty()) {
                        stack.decrement(1);
                        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 0.9F);

                        if(world.isClientSide) {
                            if (stack.isEmpty()) {
                                player.setStackInHand(hand, stackResult);
                            } else if (!player.getInventory().insertStack(stackResult)) {
                                player.dropItem(stackResult, false);
                            }
                        }

                        world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(4.0, 0.0, 4.0, 12, 7.0, 12.0);
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HANGING);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos bottomBlockPos = blockPos.below();
        BlockState blockState = blockView.getBlockState(bottomBlockPos);
        if(blockState.is(BlockTags.CAMPFIRES) || blockState.is(ModDecorativeBlocks.BONFIRE)) {
            return this.defaultBlockState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).with(HANGING, true);
        }
        return this.defaultBlockState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrockpotBlockEntity(pos, state, 0.375f);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return null; //validateTicker(world, type, ModBlockEntities.CROCKPOT);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<CrockpotBlockEntity> expectedType) {
        return world.isClientSide
                ? checkType(givenType, expectedType, CrockpotBlockEntity::clientTick)
                : checkType(givenType, expectedType, CrockpotBlockEntity::serverTick);
    }

    @Override
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.randomDisplayTick(state, world, pos, random);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            if(crockpotBlockEntity.isCooking()) {

            }
        }

    }
}
