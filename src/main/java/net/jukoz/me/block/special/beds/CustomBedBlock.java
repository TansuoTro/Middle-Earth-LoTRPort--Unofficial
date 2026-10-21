package net.jukoz.me.block.special.beds;

import net.jukoz.me.block.registration.ModBlockEntities;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.RenderShape;

public class CustomBedBlock extends BedBlock {

    public CustomBedBlock(DyeColor color, Settings settings) {
        super(color, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CustomBedBlockEntity(ModBlockEntities.BED, pos, state);
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }
}
