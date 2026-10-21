package net.jukoz.me.compat.structure;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class VaultBlock extends BaseEntityBlock {
    public enum Phase implements StringRepresentable {
        INACTIVE, ACTIVE, UNLOCKING, EJECTING;
        public String asString() { return name().lowercaseVariant(java.util.Locale.ROOT); }
    }
    public static final EnumProperty<Phase> PHASE = EnumProperty.create("vault_state", Phase.class);
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public VaultBlock(BlockBehaviour.Properties settings) {
        super(settings);
        setDefaultState(stateManager.defaultBlockState().with(PHASE, Phase.INACTIVE)
                .with(FACING, Direction.NORTH).with(TrialSpawnerBlock.OMINOUS, false));
    }
    @Override protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) { builder.add(PHASE, FACING, TrialSpawnerBlock.OMINOUS); }
    @Override public RenderShape getRenderType(BlockState state) { return RenderShape.MODEL; }
    @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new VaultBlockEntity(pos, state); }
    @Override public BlockState getPlacementState(BlockPlaceContext context) { return getDefaultState().with(FACING, context.getHorizontalDirection().getOpposite()); }
    @Override public BlockState rotate(BlockState state, Rotation rotation) { return state.setValue(FACING, rotation.rotate(state.get(FACING))); }
    @Override public BlockState mirror(BlockState state, Mirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
    @Override public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide) return InteractionResult.SUCCESS;
        return world.getBlockEntity(pos) instanceof VaultBlockEntity vault && vault.unlock(player, hand)
                ? InteractionResult.CONSUME : InteractionResult.PASS;
    }
}
