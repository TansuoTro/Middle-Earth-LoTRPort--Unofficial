package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/** Forge compat shim for Fabric's {@code PlayerLookup}. */
public final class PlayerLookup {
    private PlayerLookup() {}

    public static List<ServerPlayer> tracking(ServerLevel level, net.minecraft.core.BlockPos pos) {
        return level.getChunkSource().chunkMap.getPlayers(
                new net.minecraft.core.BlockPos(pos.getX() >> 4, 0, pos.getZ() >> 4), false);
    }

    public static List<ServerPlayer> tracking(net.minecraft.world.entity.Entity entity) {
        net.minecraft.server.level.ServerLevel level = (net.minecraft.server.level.ServerLevel) entity.level();
        return tracking(level, entity.blockPosition());
    }

    public static List<ServerPlayer> around(ServerLevel level, Vec3 pos, double radius) {
        return level.getPlayers(player -> player.distanceToSqr(pos) <= radius * radius);
    }

    public static List<ServerPlayer> world(ServerLevel level) {
        return level.players();
    }

    public static List<ServerPlayer> all(net.minecraft.server.MinecraftServer server) {
        return server.getPlayerList().getPlayers();
    }

    private static ServerLevel serverLevelOf(net.minecraft.world.entity.Entity entity) {
        return (ServerLevel) entity.level();
    }
}
