package net.jukoz.me.compat;

import net.minecraft.world.phys.AABB;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * 1.20.1 shims for block-shape helpers introduced after 1.20.1.
 *
 * <ul>
 *   <li>{@code Block.createShapeArray(int, IntFunction)} (1.21)</li>
 *   <li>{@code Block.createCuboidZShape(...)} (1.21)</li>
 *   <li>{@code Shapes.createHorizontalFacingShapeMap(...)} (1.21)</li>
 * </ul>
 */
public final class MEBlockShapes {
    private MEBlockShapes() {
    }

    /** 1.21's {@code Block.createShapeArray}: an array indexed 0..size (inclusive). */
    public static VoxelShape[] createShapeArray(int size, IntFunction<VoxelShape> indexToShape) {
        VoxelShape[] shapes = new VoxelShape[size + 1];
        for (int i = 0; i <= size; i++) {
            shapes[i] = indexToShape.apply(i);
        }
        return shapes;
    }

    /** 1.21's three-argument {@code createCuboidZShape(sizeXy, minZ, maxZ)}. */
    public static VoxelShape createCuboidZShape(double sizeXy, double minZ, double maxZ) {
        return createCuboidZShape(sizeXy, sizeXy, minZ, maxZ);
    }

    /** 1.21's four-argument {@code createCuboidZShape(sizeX, sizeY, minZ, maxZ)}. */
    public static VoxelShape createCuboidZShape(double sizeX, double sizeY, double minZ, double maxZ) {
        return createCuboidZShape(sizeX, (16.0 - sizeY) / 2.0, (16.0 + sizeY) / 2.0, minZ, maxZ);
    }

    /** 1.21's five-argument {@code createCuboidZShape(sizeX, minY, maxY, minZ, maxZ)}. */
    public static VoxelShape createCuboidZShape(double sizeX, double minY, double maxY, double minZ, double maxZ) {
        double minX = (16.0 - sizeX) / 2.0;
        double maxX = (16.0 + sizeX) / 2.0;
        return Shapes.addBox(minX / 16.0, minY / 16.0, minZ / 16.0, maxX / 16.0, maxY / 16.0, maxZ / 16.0);
    }

    public static Map<Direction, VoxelShape> createHorizontalFacingShapeMap(VoxelShape shape) {
        return createHorizontalFacingShapeMap(shape, new Vec3(0.5, 0.5, 0.5));
    }

    public static Map<Direction, VoxelShape> createHorizontalFacingShapeMap(VoxelShape shape, Vec3 anchor) {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        int i = 0;
        for (Direction direction : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}) {
            map.put(direction, rotateAroundY(shape, anchor, i));
            i++;
        }
        return map;
    }

    public static Map<Direction, VoxelShape> createFacingShapeMap(VoxelShape shape) {
        return createFacingShapeMap(shape, new Vec3(0.5, 0.5, 0.5));
    }

    public static Map<Direction, VoxelShape> createFacingShapeMap(VoxelShape shape, Vec3 anchor) {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        for (Direction direction : Direction.values()) {
            map.put(direction, shape);
        }
        return map;
    }

    private static VoxelShape rotateAroundY(VoxelShape shape, Vec3 anchor, int quarterTurns) {
        int turns = ((quarterTurns % 4) + 4) % 4;
        if (turns == 0) {
            return shape;
        }
        List<VoxelShape> parts = new ArrayList<>();
        for (AABB box : shape.toAabbs()) {
            double[] xs = {box.minX * 16.0, box.maxX * 16.0};
            double[] zs = {box.minZ * 16.0, box.maxZ * 16.0};
            double minX = Double.POSITIVE_INFINITY;
            double maxX = Double.NEGATIVE_INFINITY;
            double minZ = Double.POSITIVE_INFINITY;
            double maxZ = Double.NEGATIVE_INFINITY;
            for (double x : xs) {
                for (double z : zs) {
                    double rx = x;
                    double rz = z;
                    for (int t = 0; t < turns; t++) {
                        double nx = anchor.x * 16.0 + (rz - anchor.z * 16.0);
                        double nz = anchor.z * 16.0 - (rx - anchor.x * 16.0);
                        rx = nx;
                        rz = nz;
                    }
                    minX = Math.min(minX, rx);
                    maxX = Math.max(maxX, rx);
                    minZ = Math.min(minZ, rz);
                    maxZ = Math.max(maxZ, rz);
                }
            }
            parts.add(Shapes.addBox(minX / 16.0, box.minY, minZ / 16.0, maxX / 16.0, box.maxY, maxZ / 16.0));
        }
        VoxelShape result = Shapes.empty();
        for (VoxelShape part : parts) {
            result = Shapes.union(result, part);
        }
        return result;
    }
}
