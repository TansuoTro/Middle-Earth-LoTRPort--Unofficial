package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.item.EquipmentItemsME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin {

    @ModifyExpressionValue(method = "onUseWithItem",at = @At(value = "INVOKE",
            target = "Lnet/minecraft/block/CampfireBlock;isLitCampfireInRange(Lnet/minecraft/world/Level;Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean preventBeeAnger(boolean original, ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return original || player.getEquippedStack(EquipmentSlot.HEAD).isOf(EquipmentItemsME.BEEKEEPER_MASK);

    }
}
