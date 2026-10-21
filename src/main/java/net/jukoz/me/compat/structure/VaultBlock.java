package net.jukoz.me.compat.structure;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

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
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(PHASE, FACING, TrialSpawnerBlock.OMINOUS); }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new VaultBlockEntity(pos, state); }
    @Override public BlockState getPlacementState(ItemPlacementContext context) { return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite()); }
    @Override public BlockState rotate(BlockState state, BlockRotation rotation) { return state.with(FACING, rotation.rotate(state.get(FACING))); }
    @Override public BlockState mirror(BlockState state, BlockMirror mirror) { return state.rotate(mirror.getRotation(state.get(FACING))); }
    @Override public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        return world.getBlockEntity(pos) instanceof VaultBlockEntity vault && vault.unlock(player, hand)
                ? ActionResult.CONSUME : ActionResult.PASS;
    }
}
