package net.jukoz.me.resources.datas.factions;
import net.jukoz.me.compat.METext;

import net.minecraft.world.entity.player.Player;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.CommandUtils;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.NoFactionException;
import net.jukoz.me.exceptions.SpawnIdentifierException;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.ModColors;
import net.jukoz.me.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class FactionUtil {


    public static boolean updateFaction(ServerPlayer player, @Nullable Faction faction, @Nullable ResourceLocation spawnId) throws IdenticalFactionException, SpawnIdentifierException, FactionIdentifierException, NoFactionException {
        if(!assertUpdateFactionValues(player, faction, spawnId))
            return false;

        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.getWorld());

        // [CLEAR] If the next faction is null
        if(faction == null){
            return clearFaction(player);
        }

        // [REPLACE] If previous faction is not null and next faction is not null
        if(previousFaction != null){
            sendOnLeaveCommand(player, previousFaction);
            // Send leaving message to affected player
            MutableComponent targetText = Component.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), previousFaction.getFullName());
            player.sendMessage(METext.withColor(targetText, ModColors.WARNING.color));
        }

        // [JOIN] Add new affiliation data
        if(spawnId == null)
            spawnId = faction.getSpawnData().getDefaultSpawn();
        PlayerDataService.setNewFactionInformation(player, player.getWorld(), faction.getId(), spawnId);
        sendOnJoinCommand(player, faction);

        // Send join message to affected player
        MutableComponent targetText = Component.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
        player.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));

        sendOnFactionJoinMessage(player);
        return true;
    }

    private static boolean assertUpdateFactionValues(ServerPlayer player, Faction faction, ResourceLocation spawnId) throws IdenticalFactionException, SpawnIdentifierException {
        // Verify player
        if(player == null) return false;

        // Verify faction
        // Fetch player datas
        Faction previousFaction = PlayerDataService.getPlayerFaction(player, player.getWorld());

        if(previousFaction == null)
            return true;

        // If there is no faction update, return true
        if(previousFaction.getId() == faction.getId()) {
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(player, player.getWorld());
            if(spawnData != null && spawnId != spawnData.getIdentifier())
                throw new IdenticalFactionException();
        };

        // Verify spawnId
        if(getSpawnId(faction, spawnId) == null)
            return false;

        return true;
    }

    private static ResourceLocation getSpawnId(Faction faction, ResourceLocation spawnId) throws SpawnIdentifierException {
        SpawnDataHandler spawnDataHandler = faction.getSpawnData();
        if(spawnDataHandler != null){
            if(spawnId == null)
                spawnId = spawnDataHandler.getDefaultSpawn();

            boolean spawnExistInFaction = spawnDataHandler.getAllSpawnIdentifiers().contains(spawnId);

            if(spawnId == null || !spawnExistInFaction){
                throw new SpawnIdentifierException();
            }
        }
        return spawnId;
    }

    private static void sendOnJoinCommand(ServerPlayer player, Faction faction) {
        if(player == null || faction == null) return;

        Optional<List<String>> joinCommand = faction.getJoinCommands();
        if(joinCommand.isEmpty()) return;

        List<String> commands = joinCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    private static void sendOnLeaveCommand(ServerPlayer player, Faction previousFaction) {
        if(player == null || previousFaction == null) return;

        Optional<List<String>> leaveCommand = previousFaction.getLeaveCommands();
        if(leaveCommand.isEmpty()) return;

        List<String> commands = leaveCommand.orElse(null);

        if(commands.isEmpty()) return;
        CommandUtils.sendAllCommands(player, commands);
    }

    public static void sendOnFactionJoinMessage(Player player) {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null){
            MiddleEarth.LOGGER.logError("Couldn't find faction");
            return;
        }


        MutableComponent targetText = Component.translatable("event.%s.join.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
        ((ServerPlayer) player).networkHandler.sendPacket(
            new TitleS2CPacket(Component.of(""))
        );
        ((ServerPlayer) player).networkHandler.sendPacket(
                new SubtitleS2CPacket(METext.withColor(targetText, ModColors.SUCCESS.color))
        );

    }

    public static boolean clearFaction(ServerPlayer player) throws FactionIdentifierException, NoFactionException {
        Faction faction = PlayerDataService.getPlayerFaction(player, player.getWorld());
        if(faction == null)
            throw new NoFactionException();
        if(PlayerDataService.clearPlayerData(player)){
            sendOnLeaveCommand(player, faction);
            MutableComponent targetText = Component.translatable("event.%s.leave.faction.success".formatted(MiddleEarth.MOD_ID), faction.getFullName());
            player.sendMessage(METext.withColor(targetText, ModColors.WARNING.color));
            return true;
        }
        return false;
    }

    /**
     * Simply used to explore spawn points.
     * @param target player to teleport
     * @param spawnId spawn identifier the player need to teleport to
     * @return if the process was a success or not
     */
    public static boolean forceTeleportToSpawnMiddleEarthId(ServerPlayer target, ResourceLocation spawnId){
        BlockPos spawnBlockPos = null;
        spawnBlockPos = getSpawnBlockPos(target.getWorld(), spawnId);
        if(spawnBlockPos == null)
            return false;
        ModDimensions.teleportPlayerToMe(target, new Vec3(spawnBlockPos.getX(), spawnBlockPos.getY(), spawnBlockPos.getZ()), false, false);
        return true;
    }

    public static BlockPos getSpawnBlockPos(Level world, ResourceLocation spawnId) {
        BlockPos spawnBlockPos = null;
        for(Faction faction: FactionLookup.getAllFactions(world)){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null)
                spawnBlockPos = spawnDataHandler.getSpawnBlockPos(spawnId);
            if(spawnBlockPos != null) {
                return spawnBlockPos;
            }

            if(faction.getSubFactions() != null){
                for(ResourceLocation subfactionId : faction.getSubFactions()){
                    try {
                        Faction subFaction = null;
                        subFaction = FactionLookup.getFactionById(world, subfactionId);
                        SpawnDataHandler subFacspawnDataHandler = subFaction.getSpawnData();
                        if(subFacspawnDataHandler != null)
                            spawnBlockPos = subFacspawnDataHandler.getSpawnBlockPos(spawnId);
                        if(spawnBlockPos != null) {
                            return spawnBlockPos;
                        }
                    } catch (FactionIdentifierException e) {
                        return null;
                    }
                }
            }
        }
        return  null;
    }
}
