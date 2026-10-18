package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class BrazierBlockEntity extends ToggleableFireBlockEntity {

    public BrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIG_BRAZIER, pos, state);
    }
}
