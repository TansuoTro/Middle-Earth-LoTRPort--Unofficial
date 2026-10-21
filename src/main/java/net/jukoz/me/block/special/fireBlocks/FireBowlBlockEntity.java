package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public class FireBowlBlockEntity extends ToggleableFireBlockEntity {

    public FireBowlBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FIRE_BOWL, pos, state);
    }
}
