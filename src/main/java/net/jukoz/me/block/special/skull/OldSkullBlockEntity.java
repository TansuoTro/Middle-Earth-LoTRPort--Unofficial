package net.jukoz.me.block.special.skull;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.block.registration.ModBlockEntities;

public class OldSkullBlockEntity extends BlockEntity {
    public OldSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OLD_SKULL, pos, state);
    }
}
