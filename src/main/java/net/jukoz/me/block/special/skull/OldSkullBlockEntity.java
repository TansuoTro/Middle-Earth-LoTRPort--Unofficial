package net.jukoz.me.block.special.skull;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.jukoz.me.block.registration.ModBlockEntities;

public class OldSkullBlockEntity extends BlockEntity {
    public OldSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OLD_SKULL, pos, state);
    }
}
