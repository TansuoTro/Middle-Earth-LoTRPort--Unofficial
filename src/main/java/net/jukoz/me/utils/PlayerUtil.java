package net.jukoz.me.utils;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.math.Box;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerUtil {
    public static boolean isAgainstWall(Player entity) {
        Box boundingBox = entity.getBoundingBox();
        Level world = entity.getWorld();
        double testMovementAmount = 0.0001;

        Vec3 testMovement = new Vec3(testMovementAmount, 0.0, testMovementAmount);
        List<VoxelShape> collisions = world.getEntityCollisions(entity, boundingBox.stretch(testMovement));
        Vec3 result = Entity.adjustMovementForCollisions(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return checkIfBlockIsAllowed(world, entity);
        }

        testMovement = new Vec3(-testMovementAmount, 0.0, -testMovementAmount);
        collisions = world.getEntityCollisions(entity, boundingBox.stretch(testMovement));
        result = Entity.adjustMovementForCollisions(entity, testMovement, boundingBox, world, collisions);
        if (!result.equals(testMovement)) {
            return checkIfBlockIsAllowed(world, entity);
        }

        return false;
    }

    private static boolean checkIfBlockIsAllowed(Level world, Player player) {
        BlockState blockstate = world.getBlockState(player.getBlockPos().offset(player.getHorizontalFacing()));
        boolean isSolid = blockstate.isSolidBlock(world, player.getBlockPos());
        boolean isAllowed = !blockstate.isIn(TagKey.of(Registries.BLOCK, MiddleEarth.of("climbing_attribute_unallowed_blocks")));
        return isSolid && isAllowed;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull RaceType type){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.getWorld(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return raceType == type;
            }
        }
        return false;
    }

    public static boolean isOfRace(@NotNull Player entity, @NotNull List<RaceType> types){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getRace() != null){
            Race race = RaceLookup.getRace(entity.getWorld(), data.getRace());
            if(race != null){
                RaceType raceType = race.getRaceType();
                return types.contains(raceType);
            }
        }
        return false;
    }

    public static Faction fetchFaction(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null){
            try {
                return FactionLookup.getFactionById(entity.getWorld(), data.getFaction());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
    public static SpawnData fetchSpawn(@NotNull Player entity){
        PlayerData data = StateSaverAndLoader.getPlayerState(entity);
        if(data != null && data.getFaction() != null && data.getSpawn() != null){
            try {
                Faction faction = FactionLookup.getFactionById(entity.getWorld(), data.getFaction());
                return faction.getSpawnData().findSpawn(data.getSpawn());
            } catch (FactionIdentifierException e) {
                return null;
            }
        }
        return null;
    }
}
