package net.jukoz.me.block.special.fireBlocks;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public class GildedSmallBrazierBlockEntity extends ToggleableFireBlockEntity {

    public GildedSmallBrazierBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GILDED_SMALL_BRAZIER, pos, state);
    }
}
