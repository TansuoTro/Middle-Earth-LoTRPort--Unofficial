package net.jukoz.me.resources.persistent_datas;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

public class AffiliationData {
    public DispositionType dispositionType;
    public ResourceLocation faction;
    public ResourceLocation spawnId;

    public AffiliationData(String disposition, ResourceLocation factionId, ResourceLocation spawnId) {
        this.dispositionType = DispositionType.valueOf(disposition);
        this.faction = factionId;
        this.spawnId = spawnId;
    }

    public DispositionType getDisposition(){
        return dispositionType;
    }

    public Vec3 getSpawnMiddleEarthCoordinate(Level world){
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
