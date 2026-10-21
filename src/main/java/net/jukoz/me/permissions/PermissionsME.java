package net.jukoz.me.permissions;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.server.level.ServerPlayer;

public class PermissionsME {
    public static boolean checkMapTeleport(ServerPlayer player) {
        return Permissions.check(player, "middle-earth.map-teleport", 2);
    }
}
