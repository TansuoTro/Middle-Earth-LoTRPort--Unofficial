package net.jukoz.me.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.item.FilledMapItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.MiddleEarth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FilledMapItem.class)
public class FilledMapItemMixin {
    @Redirect(
            method = "updateColors",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/BlockState;getMapColor(Lnet/minecraft/world/BlockGetter;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/MapColor;"
            )
    )
    private MapColor preventNullMapColor(BlockState state, BlockGetter world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);
        //debug(state, world, pos);
        return color == null ? MapColor.CLEAR : color;
    }

    private void debug(BlockState state, BlockGetter world, BlockPos pos) {
        MapColor color = state.getMapColor(world, pos);

        if (color == null) {
            MiddleEarth.LOGGER.logError("Null map color at " +  pos+ " block " + state.toString());
        }
    }
}
