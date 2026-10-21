package net.jukoz.me.world.chunkgen;

import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MapBasedCustomBiome;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.level.chunk.ChunkAccess;

public class ProceduralStructures {
    public static final int mapMultiplier = (int) Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION + MiddleEarthMapConfigs.PIXEL_WEIGHT - 2);

    public static void generateStructures(MapBasedCustomBiome biome, ChunkAccess chunk, int x, int y, int z) {
        if(biome.getBiomeKey() == MEBiomeKeys.NAN_CURUNIR || biome.getBiomeKey() == MEBiomeKeys.ISENGARD  || biome.getBiomeKey() == MEBiomeKeys.ISENGARD_HILL) {
            generateIsengard(chunk, x, y, z);
        }
    }

    // region Isengard
    public static final Vec2 centerOrthanc = new Vec2(1402, 1464).multiply(mapMultiplier);
    private static final float radiusOrthanc = 12.12f;
    private static final float topRadiusOrthanc = 4.63f;
    private static final int bottomOrthanc = 72;
    private static final int topOrthanc = 224;

    public static final float isengardRingRadius = 6.7f * mapMultiplier;
    public static final float isengardRingThickness = 6;
    public static final float isengardRingHillThickness = 9;
    private static final float isengardWallsHeight = 12;
    private static final float isengardPathSize = 0.032f;
    private static final BlockState isengardBlock = StoneBlockSets.MEDGON_SET.smoothBlocks.base().defaultBlockState();
    private static final BlockState isengardWallBlock = StoneBlockSets.MEDGON_SET.cobblestoneBlocks.base().defaultBlockState();

    private static void generateIsengard(ChunkAccess chunk, int x, int y, int z) {
        Vec2 coordinates = new Vec2(x, z);
        float distance = (float) Math.sqrt(centerOrthanc.distanceSquared(coordinates));
        if(distance < radiusOrthanc) {
            for(int i = bottomOrthanc; i < topOrthanc; i++) {
                float ratio = (float) (i - bottomOrthanc) / (topOrthanc - bottomOrthanc);
                float currentRadius = radiusOrthanc * (1 - ratio) + (topRadiusOrthanc * ratio);
                if (distance < currentRadius) {
                    chunk.setBlock(chunk.getPos().getBlockPos(x, i, z), isengardBlock, false);
                }
            }
        } else if(distance < isengardRingRadius + isengardRingThickness + isengardRingHillThickness) { // Ring hills (before walls)
            if (distance > isengardRingRadius - isengardRingThickness - isengardRingHillThickness) {
                float percentageDistance = Math.abs(Math.abs(isengardRingRadius - distance));
                percentageDistance = (isengardRingThickness + isengardRingHillThickness) - percentageDistance;
                float hillHeight = Math.min(3, 0.4f * percentageDistance); //(float) Math.pow(percentageDistance, 0.55f);

                for(int i = -1; i < (int)hillHeight; i++) {
                    BlockState blockState = Blocks.DIRT.defaultBlockState();
                    if(i == (int)hillHeight - 1) blockState = Blocks.GRASS_BLOCK.defaultBlockState();
                    chunk.setBlock(chunk.getPos().getBlockPos(x, y + i, z), blockState, false);
                }

                if(distance < isengardRingRadius + isengardRingThickness) { // Walls
                    if (distance > isengardRingRadius - isengardRingThickness) {
                        Vec2 direction = (centerOrthanc.add(coordinates.negate())).normalize();
                        float dropHeight = (float) Math.pow(Math.abs(Math.abs(isengardRingRadius - distance) / 3), 2);
                        float dotProduct = Math.abs(direction.dot(Vec2.EAST_UNIT));
                        if(dotProduct <= isengardPathSize && z > centerOrthanc.y) {
                            chunk.setBlock(chunk.getPos().getBlockPos(x, y, z), isengardWallBlock, false);
                            float tunnel = (float) Math.pow(dotProduct + 1, 52);
                            for(int i = (int) (isengardWallsHeight - 2 - tunnel); i < (isengardWallsHeight - dropHeight); i++) {
                                chunk.setBlock(chunk.getPos().getBlockPos(x, y + (int)hillHeight + i, z), isengardWallBlock, false);
                            }
                        } else {
                            for(int i = -1; i < (isengardWallsHeight - dropHeight); i++) {
                                chunk.setBlock(chunk.getPos().getBlockPos(x, y + (int)hillHeight +i, z), isengardWallBlock, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean isInsideIsengard(int x, int z) {
        Vec2 coordinates = new Vec2(x, z);
        float distance = (float) Math.sqrt(centerOrthanc.distanceSquared(coordinates));
        return distance <= isengardRingRadius - isengardRingThickness;
    }

    public static float addRingHillsIsengard(int x, int z) {
        Vec2 coordinates = new Vec2(x, z);
        float distance = (float) Math.sqrt(centerOrthanc.distanceSquared(coordinates));
        if(distance < isengardRingRadius + isengardRingThickness + isengardRingHillThickness) { // Around Walls
            if (distance > isengardRingRadius - isengardRingThickness - isengardRingHillThickness) {
                float percentage = (isengardRingRadius - distance) / (isengardRingThickness + isengardRingHillThickness);
                return (float) Math.pow(percentage, 0.7f);
            }
        }
        return 0;
    }
    // endregion
}
