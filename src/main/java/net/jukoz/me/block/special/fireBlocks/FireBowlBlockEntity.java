package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class FireBowlBlockEntity extends ToggleableFireBlockEntity {

    public FireBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_BOWL, pos, state);
    }
}
