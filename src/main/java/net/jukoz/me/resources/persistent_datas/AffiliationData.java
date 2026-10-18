package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AffiliationData {
    public DispositionType dispositionType;
    public Identifier faction;
    public Identifier spawnId;

    public AffiliationData(String disposition, Identifier factionId, Identifier spawnId) {
        this.dispositionType = DispositionType.valueOf(disposition);
        this.faction = factionId;
        this.spawnId = spawnId;
    }


    public DispositionType getDisposition(){
        return dispositionType;
    }

    public Vec3d getSpawnMiddleEarthCoordinate(World world){
        try{
            Faction foundFaction = FactionLookup.getFactionById(world,faction);
            SpawnData spawnData = foundFaction.getSpawnData().findSpawn(spawnId);
            BlockPos blockpos = spawnData.getBlockPos();
            if(!spawnData.isDynamic()){ // Return custom spawn coords
                return blockpos.toCenterPos();
            }
            int height = ModDimensions.getDimensionHeight(blockpos.getX(), blockpos.getZ()).y;
            blockpos = new BlockPos(blockpos.getX(), height, blockpos.getZ());
            return blockpos.toCenterPos();
        } catch (FactionIdentifierException e){
            MiddleEarth.LOGGER.logError("AffiliationData::getSpawnMiddleEarthCoordinate - PlayerFactionPayload couldn't be found <%s>".formatted(faction));
            return null;
        }
    }

    @Override
    public String toString() {
        return "Disposition=" + getDisposition().toString() + ";\nPlayerFactionPayload=" + faction + ";\nSpawn=" + spawnId + ";";
    }
}
