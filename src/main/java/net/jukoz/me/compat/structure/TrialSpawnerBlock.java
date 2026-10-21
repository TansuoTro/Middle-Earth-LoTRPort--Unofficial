package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.util.StringRepresentable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** The subset of 1.21 trial states used by the donor's configured dungeons. */
public class TrialSpawnerBlock extends BaseEntityBlock {
    public enum Phase implements StringRepresentable {
        INACTIVE, WAITING_FOR_PLAYERS, ACTIVE, WAITING_FOR_REWARD_EJECTION, EJECTING_REWARD, COOLDOWN;
        public String asString() { return name().toLowerCase(java.util.Locale.ROOT); }
    }
    public static final EnumProperty<Phase> PHASE = EnumProperty.of("trial_spawner_state", Phase.class);
    public static final BooleanProperty OMINOUS = BooleanProperty.of("ominous");

    public TrialSpawnerBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(PHASE, Phase.WAITING_FOR_PLAYERS).with(OMINOUS, false));
    }
    @Override protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) { builder.add(PHASE, OMINOUS); }
    @Override public RenderShape getRenderType(BlockState state) { return RenderShape.MODEL; }
    @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new TrialSpawnerBlockEntity(pos, state); }
    @Override @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.TRIAL_SPAWNER, TrialSpawnerBlockEntity::tick);
    }
}
