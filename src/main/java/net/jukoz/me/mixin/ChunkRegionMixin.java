package net.jukoz.me.mixin;

import net.minecraft.server.level.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(WorldGenRegion.class)
public class ChunkRegionMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static int WorldGenRegion(int placementRadius) {
        if(placementRadius > 0) placementRadius++;
        else if (placementRadius < 0) placementRadius--;
        return placementRadius;
    }
}
