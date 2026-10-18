package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class ChimneyBlockEntity extends ToggleableFireBlockEntity {

    public ChimneyBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHIMNEY, pos, state);
    }
}
