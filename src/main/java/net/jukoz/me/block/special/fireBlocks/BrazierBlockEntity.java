package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public class BrazierBlockEntity extends ToggleableFireBlockEntity {

    public BrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIG_BRAZIER, pos, state);
    }
}
