package net.jukoz.me.block.special.bellows;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlockEntities;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.sound.SoundsME;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import java.util.Random;
import net.minecraft.util.RandomSource;

public class BellowsBlockEntity extends BlockEntity {
    public static final int MAX_TICKS = 30;
    public static final int AVERAGE_PARTICLES = 3;
    public static final int PARTICLE_AMOUNT_MODIFIER = 2;

    public static RandomSource RANDOM;

    public int animationProgress;
    private static final String ID = "bellows";
    public boolean pumping;
    
    public BellowsBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BELLOWS, pos, state);
        if(RANDOM == null)
            RANDOM = new RandomSource();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> toUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean tryPumpingBellow(BlockState state, Level world, BlockPos pos, BellowsBlockEntity blockEntity, Direction direction, Entity entity) {
        if(blockEntity.animationProgress == 0) {
            if (!world.isClient){
                if(blockEntity.activate(direction)){
                    BlockPos forgePos = pos.offset(state.get(BellowsBlock.FACING));
                    if(world.getBlockState(forgePos).isIn(TagKey.of(Registries.BLOCK, MiddleEarth.of("forge")))) {
                        ForgeBlockEntity forgeBlockEntity = (ForgeBlockEntity) world.getBlockEntity(forgePos);
                        if(forgeBlockEntity != null) {
                            forgeBlockEntity.bellowsBoost();
                        }
                    }
                    world.playSound((Player)null,  pos, SoundsME.BELLOWS_PUSH, SoundSource.BLOCKS, 2.0F, 1.0F);
                    world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean activate(Direction direction) {
        if (!this.pumping) {
            this.pumping = true;
            this.animationProgress = 0;
            if(this.world != null){
                BlockPos blockPos = this.getPos();
                this.world.addSyncedBlockEvent(blockPos, this.getCachedState().getBlock(), 1, direction.getId());
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.pumping = true;
            this.animationProgress = 0;
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    private static void tick(BellowsBlockEntity blockEntity) {
        if(blockEntity.pumping) {
            ++blockEntity.animationProgress;
            if(blockEntity.animationProgress > MAX_TICKS) {
                blockEntity.pumping = false;
                blockEntity.animationProgress = 0;
            }
        }
    }

    public static void clientTick(Level world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        // Only occurs if it's the initial tick
        if(blockEntity.pumping && blockEntity.animationProgress == 0)
        {
            Vec3i directionVec = state.get(BellowsBlock.FACING).getVector();
            Vec3 center = pos.toCenterPos();

            int particleAmount = RANDOM.nextInt(AVERAGE_PARTICLES - PARTICLE_AMOUNT_MODIFIER, AVERAGE_PARTICLES + PARTICLE_AMOUNT_MODIFIER);
            for(int i = 0; i < particleAmount; i++){
                world.addParticle(ParticleTypes.POOF,
                        center.getX() + directionVec.getX() * 0.4f,
                        center.getY() - 0.2f,
                        center.getZ() + directionVec.getZ() * 0.4f,
                        0, 0, 0);
            }
        }

        tick(blockEntity);
    }

    public static void serverTick(Level world, BlockPos pos, BlockState state, BellowsBlockEntity blockEntity) {
        tick(blockEntity);
    }
}
