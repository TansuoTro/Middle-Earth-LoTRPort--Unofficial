package net.jukoz.me.utils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.resources.datas.biome_events.BiomeEventDataLookup;

public class SpawnUtil {
    public static boolean canCreatureSpawn(EntityType<?> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, RandomSource random) {
        if (spawnReason == SpawnReason.NATURAL && serverWorldAccess instanceof ServerLevel world) {
            Holder<Biome> biome = world.getBiome(blockPos);
            return BiomeEventDataLookup.canEntitySpawn(world, biome, blockPos, type, random);
        }
        return true;
    }

    public static boolean canSpawn(BlockPos blockPos, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason) {
        BlockPos below = blockPos.down();
        boolean isOnSolidGround = serverWorldAccess.getBlockState(below).isSolidBlock(serverWorldAccess, below);
        boolean isNotOnTopOfLogs = !serverWorldAccess.getBlockState(below).isIn(BlockTags.LOGS);
        return isOnSolidGround && isNotOnTopOfLogs;
    }
}
