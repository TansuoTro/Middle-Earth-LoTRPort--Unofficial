package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.jukoz.me.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.jukoz.me.block.special.torches.METorchBlock;
import net.jukoz.me.block.special.torches.MEWallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin extends ThrownItemEntity {

    public PotionEntityMixin(EntityType<? extends ThrownItemEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "extinguishFire")
    private void onDestroyedByExplosion(BlockPos pos, CallbackInfo ci, @Local BlockState blockState) {

        if (AbstractToggleableFireBlock.isLitFireBlock(blockState) || METorchBlock.isLitTorch(blockState) || MEWallTorchBlock.isLitWallTorch(blockState)){
            this.getWorld().syncWorldEvent((Player)null, 1009, pos, 0);
            AbstractToggleableFireBlock.extinguish(this.getOwner(), this.getWorld(), pos, blockState);
            this.getWorld().setBlockState(pos, (BlockState)blockState.with(CampfireBlock.LIT, false));
        }
    }
}
