package net.jukoz.me.block.special.shapingAnvil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.jukoz.me.item.items.SmithingHammerItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.Containers;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public abstract class AbstractShapingAnvilBlock extends BaseEntityBlock implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public AbstractShapingAnvilBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.defaultBlockState()).with(FACING, Direction.NORTH)));
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory inventory) {
            Containers.spawn(world, pos, inventory);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.defaultBlockState().with(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {

        super.onBreak(world, pos, state, player);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!world.isClientSide) {
            if (player.getMainHandStack().isEmpty() && !blockEntity.getItem(0).isEmpty()) {
                player.equipStack(EquipmentSlot.MAINHAND, blockEntity.getItem(0));
                blockEntity.removeStack(0);
            } else {
                MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                if (screenHandlerFactory != null) {
                    player.openMenu(screenHandlerFactory);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBlockBreakStart(BlockState state, Level world, BlockPos pos, Player player) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClientSide) {
            if (stack.getItem() instanceof SmithingHammerItem hammer && player.getAttackStrengthScale(0.5f) > 0.9f) {
                player.incrementStat(Stats.USED.getOrCreateStat(hammer));
                stack.use(world, player, player.getUsedItemHand());
                player.getItemInHand(player.getUsedItemHand()).damage(1, player, (p) -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                if (blockEntity instanceof ShapingAnvilBlockEntity shapingAnvilBlockEntity) {
                    ServerLevel serverWorld = (ServerLevel) world;
                    shapingAnvilBlockEntity.bonk(shapingAnvilBlockEntity, serverWorld, stack);
                }
            }
        }
    }
}
