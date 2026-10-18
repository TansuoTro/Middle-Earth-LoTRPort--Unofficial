package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.fireBlocks.AbstractToggleableFireBlock;
import net.jukoz.me.block.special.torches.METorchBlock;
import net.jukoz.me.block.special.torches.MEWallTorchBlock;
import net.jukoz.me.utils.BlockTagsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Inject(at = @At("TAIL"), method = "canPlantOnTop", cancellable = true)
    private void canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local BlockState blockState) {
        if (floor.isIn(BlockTagsME.FARMLANDS) || floor.isIn(TagKey.of(RegistryKeys.BLOCK, new Identifier(MiddleEarth.MOD_ID, "farmlands")))){
            cir.setReturnValue(true);
        }
    }
}
