package net.jukoz.me.compat.structure;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/** The subset of 1.21 trial states used by the donor's configured dungeons. */
public class TrialSpawnerBlock extends BlockWithEntity {
    public enum Phase implements StringIdentifiable {
        INACTIVE, WAITING_FOR_PLAYERS, ACTIVE, WAITING_FOR_REWARD_EJECTION, EJECTING_REWARD, COOLDOWN;
        public String asString() { return name().toLowerCase(java.util.Locale.ROOT); }
    }
    public static final EnumProperty<Phase> PHASE = EnumProperty.of("trial_spawner_state", Phase.class);
    public static final BooleanProperty OMINOUS = BooleanProperty.of("ominous");

    public TrialSpawnerBlock(Settings settings) {
        super(settings);
        setDefaultState(stateManager.getDefaultState().with(PHASE, Phase.WAITING_FOR_PLAYERS).with(OMINOUS, false));
    }
    @Override protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(PHASE, OMINOUS); }
    @Override public BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    @Override public BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new TrialSpawnerBlockEntity(pos, state); }
    @Override @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, ModBlockEntities.TRIAL_SPAWNER, TrialSpawnerBlockEntity::tick);
    }
}
