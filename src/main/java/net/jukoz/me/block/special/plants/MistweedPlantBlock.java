package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelEvents;
import net.minecraft.world.level.LevelView;import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.particles.ModParticleTypes;
import org.jetbrains.annotations.Nullable;

public class MistweedPlantBlock extends CustomPlantBlock implements Fertilizable {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public MistweedPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, true));
    }



    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public void randomDisplayTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.get(ACTIVE) && random.nextDouble() <= 0.45) {
            double d = (double)pos.getX() + random.nextDouble() * 16.0 - 8.0;
            double e = (double)pos.getY() + random.nextDouble() * 5.0;
            double f = (double)pos.getZ() + random.nextDouble() * 16.0 - 8.0;
            world.addImportantParticle(ModParticleTypes.BIOME_FOG_PARTICLE, true, d, e, f, 0.0, 0.0, 0.0);
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (!state.get(ACTIVE) && itemStack.isIn(ItemTags.AXES)) {
                activateState(null, state, world, pos);
            } else if (state.get(ACTIVE) && itemStack.isOf(Items.HONEYCOMB)) {
                deactivateState(null, state, world, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected static void activateState(@Nullable Player player, BlockState state, Level world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVE, true), 11);
        world.playSound(null, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        world.syncWorldEvent(WorldEvents.WAX_REMOVED, pos, 0);
    }

    protected static void deactivateState(@Nullable Player player, BlockState state, Level world, BlockPos pos) {
        world.setBlockState(pos, state.with(ACTIVE, false), 11);
        world.playSound(null, pos, SoundEvents.ITEM_HONEYCOMB_WAX_ON, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        world.syncWorldEvent(WorldEvents.BLOCK_WAXED, pos, 0);
    }

    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        // 1.20.1 Fertilizable has no findPosToSpreadTo helper; do a small random spread search.
        for (int i = 0; i < 8; i++) {
            int dx = random.nextInt(5) - 2;
            int dz = random.nextInt(5) - 2;
            if (dx == 0 && dz == 0) continue;
            BlockPos spreadPos = pos.add(dx, 0, dz);
            BlockState spreadFloor = world.getBlockState(spreadPos.down());
            if (world.isAir(spreadPos) && this.canPlantOnTop(spreadFloor, world, spreadPos.down())) {
                world.setBlockState(spreadPos, this.getDefaultState());
                return;
            }
        }
    }
}
