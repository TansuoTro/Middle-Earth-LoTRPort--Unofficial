package net.jukoz.me.block.special.verticalSlabs;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;

public class TransparentVerticalSlab extends VerticalSlabBlock{


    public TransparentVerticalSlab(Settings settings) {
        super(settings);
    }

    protected boolean isTransparent(BlockState state) {
        return true;
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return VoxelShapes.empty();
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }
}
