package net.jukoz.me.block.special;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.sound.SoundsME;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class OrcishDrumBlock extends Block {

    public OrcishDrumBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockState(pos.above()).isAir()) {
            RandomSource random = world.getRandom();
            world.addSyncedBlockEvent(pos, this, 0, 0);
            world.playSound(player, pos, SoundsME.ORC_DRUM, SoundSource.BLOCKS, 1.0F + random.nextFloat(), (float)(0.75F + (random.nextFloat() * 0.4)));
            world.emitGameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
            return InteractionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
