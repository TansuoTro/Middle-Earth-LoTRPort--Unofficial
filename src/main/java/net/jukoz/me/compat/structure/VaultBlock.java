package net.jukoz.me.compat.structure;

import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.level.Level;

public class VaultBlock extends BlockWithEntity {
    public enum Phase implements StringIdentifiable {
        INACTIVE, ACTIVE, UNLOCKING, EJECTING;
        public String asString() { return name().toLowerCase(java.util.Locale.ROOT); }
    }
    public static final EnumProperty<Phase> PHASE = EnumProperty.of("vault_state", Phase.class);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

    public VaultBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(PHASE, Phase.INACTIVE)
                .with(FACING, Direction.NORTH).with(TrialSpawnerBlock.OMINOUS, false));
    }
    @Override protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) { builder.add(PHASE, FACING, TrialSpawnerBlock.OMINOUS); }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new VaultBlockEntity(pos, state); }
    @Override public BlockState getPlacementState(BlockPlaceContext context) { return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite()); }
    @Override public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
    @Override public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
    @Override public ActionResult onUse(BlockState state, Level world, BlockPos pos, Player player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        return world.getBlockEntity(pos) instanceof VaultBlockEntity vault && vault.unlock(player, hand)
                ? ActionResult.CONSUME : ActionResult.PASS;
    }
}
