package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public SmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SMALL_BRAZIER, pos, state);
    }
}
