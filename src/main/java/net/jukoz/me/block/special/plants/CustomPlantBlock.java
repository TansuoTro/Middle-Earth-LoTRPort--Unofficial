package net.jukoz.me.block.special.plants;

import net.minecraft.block.*;import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.Level;
import net.jukoz.me.particles.ModParticleTypes;
import net.jukoz.me.utils.BlockTagsME;

public class CustomPlantBlock extends PlantBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public CustomPlantBlock(Settings settings) {
        super(settings);
    }



    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return (floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTagsME.FARMLANDS)) && floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}