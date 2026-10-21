package net.jukoz.me.block.special.shapingAnvil;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.jukoz.me.item.items.SmithingHammerItem;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractShapingAnvilBlock extends BlockWithEntity implements BlockEntityProvider {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public AbstractShapingAnvilBlock(Settings settings) {
        super(settings);
        this.setDefaultState(((this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof Inventory inventory) {
            ItemScatterer.spawn(world, pos, inventory);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
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
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onBreak(Level world, BlockPos pos, BlockState state, Player player) {

        super.onBreak(world, pos, state, player);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!world.isClient) {
            if (player.getMainHandStack().isEmpty() && !blockEntity.getStack(0).isEmpty()) {
                player.equipStack(EquipmentSlot.MAINHAND, blockEntity.getStack(0));
                blockEntity.removeStack(0);
            } else {
                NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                if (screenHandlerFactory != null) {
                    player.openHandledScreen(screenHandlerFactory);
                }
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onBlockBreakStart(BlockState state, Level world, BlockPos pos, Player player) {
        ItemStack stack = player.getEquippedStack(EquipmentSlot.MAINHAND);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient) {
            if (stack.getItem() instanceof SmithingHammerItem hammer && player.getAttackCooldownProgress(0.5f) > 0.9f) {
                player.incrementStat(Stats.USED.getOrCreateStat(hammer));
                stack.use(world, player, player.getActiveHand());
                player.getStackInHand(player.getActiveHand()).damage(1, player, (p) -> p.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
                if (blockEntity instanceof ShapingAnvilBlockEntity shapingAnvilBlockEntity) {
                    ServerLevel serverWorld = (ServerLevel) world;
                    shapingAnvilBlockEntity.bonk(shapingAnvilBlockEntity, serverWorld, stack);
                }
            }
        }
    }
}
