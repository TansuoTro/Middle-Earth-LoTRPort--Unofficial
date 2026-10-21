package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.tags.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

public class TorchOfOrthancBlock extends TorchBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 18.0, 11.0);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public TorchOfOrthancBlock(AbstractBlock.Settings settings, DefaultParticleType particleEffect) {
        super(settings, particleEffect);
        this.setDefaultState((((this.stateManager.getDefaultState().with(LIT, true)))));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(world.isClient){
            return InteractionResult.SUCCESS;
        } else {
            if(player.getStackInHand(player.getActiveHand()).isOf(Items.FLINT_AND_STEEL) && !state.get(LIT)){
                world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0F, 1.5F);
                world.setBlockState(pos, state.cycle(LIT));
            } else if (player.getStackInHand(player.getActiveHand()).isIn(ItemTags.SHOVELS) && state.get(LIT)) {
                world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, state.cycle(LIT));
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT});
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if(state.get(LIT)){
            double d = (double)pos.getX() + 0.5;
            double e = (double)pos.getY() + 1.4;
            double f = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
