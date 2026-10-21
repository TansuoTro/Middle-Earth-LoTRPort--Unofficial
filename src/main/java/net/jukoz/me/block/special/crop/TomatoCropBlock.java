package net.jukoz.me.block.special.crop;

import net.jukoz.me.item.ResourceItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TomatoCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 30.0, 14.0);

    public TomatoCropBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected ItemLike getSeedsItem() {
        return ResourceItemsME.TOMATO_SEEDS;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(super.getAge(state) > 1){
            return SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }
    @Override
    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        if(super.getAge(state) > 1) {
            return world.getBlockState(pos).canPlaceAt(world, pos.add(0,1,0));
            // todo : make it so whenever the tomato wants to grow, it checks the origin above to see if you can place anything
        }
        return super.canGrow(world, random, pos, state);
    }
}
