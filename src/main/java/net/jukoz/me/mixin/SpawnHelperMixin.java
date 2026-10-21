package net.jukoz.me.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.jukoz.me.utils.SpawnUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnHelper.class)
public class SpawnHelperMixin {

    @Inject(method = "canSpawn", at = @At("HEAD"), cancellable = true)
    private static void canSpawnMixin(ServerLevel world, SpawnGroup group, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, MobSpawnSettings.SpawnEntry spawnEntry, BlockPos.Mutable pos, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        EntityType<?> type = spawnEntry.type;
        // Only checks if in the MEM dimension (TODO : make it more datadrivne)
        if (world.getRegistryKey().equals(ModDimensions.ME_DIMENSION_KEY) && !SpawnUtil.canCreatureSpawn(type, world, SpawnReason.NATURAL, pos, world.getRandom())) {
            cir.setReturnValue(false);
        }
    }
}
