package net.jukoz.me.block.special.shelobiteeggs;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.spider.larva.ShelobiteLarvaEntity;
import java.util.Random;
import net.minecraft.util.RandomSource;

public abstract class AbstractShelobiteLarvaEgg extends Block {
    public AbstractShelobiteLarvaEgg(Settings settings) {
        super(settings);
    }

    public static void breakEgg(Level world, BlockPos pos, BlockState state) {
        world.playSound((Player)null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
        world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
        RandomSource random = new RandomSource();
        int amountOfSpider = random.nextInt(1, 4);
        for(int i = 0; i < amountOfSpider; i++)
            SpawnSpider(pos, world);
        world.removeBlock(pos, false);
    }

    private static void SpawnSpider(BlockPos pos, Level world){
        ShelobiteLarvaEntity entity = new ShelobiteLarvaEntity(EntitiesME.SHELOBITE_LARVA, world);
        entity.age = 0;
        entity.refreshPositionAndAngles(pos, 0, 0);
        if(world instanceof ServerLevelAccessor serverWorldAccess) {
            entity.initialize(serverWorldAccess, serverWorldAccess.getLocalDifficulty(pos), MobSpawnType.NATURAL, null, null);
        }
        world.spawnEntity(entity);
    }
}
