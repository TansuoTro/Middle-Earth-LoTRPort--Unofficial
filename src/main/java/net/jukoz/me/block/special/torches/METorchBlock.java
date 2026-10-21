package net.jukoz.me.block.special.torches;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public class METorchBlock extends TorchBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public METorchBlock(Settings settings) {
        super(settings, ParticleTypes.FLAME);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(LIT, true)))));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            if(player.isCreative()){
                world.setBlockState(pos, state.cycle(LIT));
            } else {
                ItemStack itemStack = player.getStackInHand(hand);
                if (state.get(LIT) && itemStack.isIn(ItemTags.SHOVELS)) {
                    extinguish(null, state, world, pos);
                } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.5F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
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
            double e = (double)pos.getY() + 0.9;
            double f = (double)pos.getZ() + 0.5;
            world.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0, 0.0, 0.0);
        }
    }

    public static boolean isLitTorch(BlockState state) {
        return state.contains(LIT) && (Boolean)state.get(LIT) && state.getBlock() instanceof METorchBlock;
    }
}
