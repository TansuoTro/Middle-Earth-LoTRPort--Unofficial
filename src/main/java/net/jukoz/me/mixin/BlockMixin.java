package net.jukoz.me.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.entity.EntitiesME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Unique private static final float RANDOM_FLYING_BLOCK = 0.34f;
    @Unique private static final float DISCARD_DISTANCE = 3;
    @Unique private static final float FORCE = 80;
    @Unique private static final float VERTICAL_MULTIPLIER = 10;

    @Shadow protected abstract Block asBlock();

    @Inject(at = @At("HEAD"), method = "onDestroyedByExplosion")
    private void onDestroyedByExplosion(ServerLevel world, BlockPos pos, Explosion explosion, CallbackInfo ci) {
        //TODO fix this
        //if(!explosion.shouldDestroy()) return;
        Block block = this.asBlock();

        if(explosion.getEntity() == null || explosion.getEntity().getType() == EntitiesME.FIRE_OF_ORTHANC) {
            if(block != Blocks.TNT && block != ModDecorativeBlocks.FIRE_OF_ORTHANC) {
                if(Math.random() < RANDOM_FLYING_BLOCK) {
                    ExplosionAccessMixin access = (ExplosionAccessMixin) (Object) explosion;
                    net.minecraft.world.phys.Vec3 explosionPos = new net.minecraft.world.phys.Vec3(access.me$getX(), access.me$getY(), access.me$getZ());
                    float distance = (float) pos.getSquaredDistance(explosionPos);
                    if(distance < access.me$getPower() / DISCARD_DISTANCE) return;

                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, pos, block.defaultBlockState());
                    fallingBlockEntity.dropItem = false;
                    fallingBlockEntity.disableDrop();
                    Vec3 velocity = pos.toCenterPos().subtract(explosionPos).normalize();
                    float factor = FORCE / distance;
                    velocity.multiply(factor);
                    velocity.add(0, VERTICAL_MULTIPLIER * factor, 0);
                    fallingBlockEntity.setVelocity(velocity);
                }
            }
        }
    }

    @Inject(method = "sideCoversSmallSquare", at = @At("RETURN"), cancellable = true)
    private static void sideCoversSmallSquare(LevelReader world, BlockPos pos, Direction side, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = world.getBlockState(pos);
        if(blockState.getBlock() == ModDecorativeBlocks.ROPE) cir.setReturnValue(true);
    }
}
