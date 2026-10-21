package net.jukoz.me.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.Level;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.utils.IEntityDataSaver;
import net.jukoz.me.utils.PlayerMovementData;
import net.jukoz.me.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public class ServerPlayerEntityMixin extends Player {
    @Shadow public MinecraftServer server;
    @Shadow public ServerPlayerGameMode interactionManager;

    public ServerPlayerEntityMixin(MinecraftServer server, ServerLevel world, GameProfile profile) {
        super(world, world.getSpawnPos(), 0.0F, profile);
    }

    @Inject(method = "getTeleportTarget", at = @At("RETURN"), cancellable = true)
    private void me$getTeleportTarget(ServerLevel targetWorld, CallbackInfoReturnable<PortalInfo> cir) {
        if (!ModDimensions.isInMiddleEarth(this.getWorld())) {
            return;
        }
        if (tryToOverrideSpawn(cir)) {
            return;
        }
        resetSpawn(cir);
    }

    @Unique
    private void resetSpawn(CallbackInfoReturnable<PortalInfo> cir) {
        if (this.getServer() == null) return;

        PlayerList manager = this.getServer().getPlayerManager();
        ServerPlayer foundPlayer = manager.getPlayer(this.getUuid());
        if (foundPlayer == null) return;
        foundPlayer.setSpawnPoint(Level.OVERWORLD, foundPlayer.getServer().getOverworld().getSpawnPos(),
                foundPlayer.getServer().getOverworld().getSpawnAngle(), true, false);

        cir.setReturnValue(new PortalInfo(this.server.getOverworld().getSpawnPos().toCenterPos(), Vec3.ZERO, 0, 0));
    }

    @Unique
    private boolean tryToOverrideSpawn(CallbackInfoReturnable<PortalInfo> cir) {
        MinecraftServer server = this.getServer();

        if (server == null) return false;
        PlayerList manager = server.getPlayerManager();
        ServerPlayer foundPlayer = manager.getPlayer(this.getUuid());

        if (foundPlayer == null) return false;
        SpawnData data = PlayerDataService.getPlayerSpawnData(foundPlayer, getWorld());
        if (ModDimensions.isInMiddleEarth(this.getWorld()) && data != null && data.getIdentifier() != null) {
            BlockPos spawnCoordinates = data.getWorldCoordinateBlockPos();
            if (spawnCoordinates != null) {
                ServerLevel MEWorld = this.server.getWorld(ModDimensions.ME_WORLD_KEY);
                if (MEWorld != null) {
                    Vec3 coordinates = new Vec3(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ());

                    foundPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY,
                            new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), 0, true, false);

                    cir.setReturnValue(new PortalInfo(coordinates, Vec3.ZERO, 0, 0));
                    return true;
                }
            }
        }
        foundPlayer.setSpawnPoint(Level.OVERWORLD, server.getOverworld().getSpawnPos(),
                server.getOverworld().getSpawnAngle(), true, false);

        cir.setReturnValue(new PortalInfo(server.getOverworld().getSpawnPos().toCenterPos(), Vec3.ZERO, 0, 0));
        return false;
    }

    @Override
    public boolean isSpectator() {
        return this.interactionManager.getGameMode() == GameType.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.interactionManager.getGameMode() == GameType.CREATIVE;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
        if(isCreative() || isSpectator()) {
            if(hasStatusEffect(ModStatusEffects.ENSHROUDED) && getStatusEffect(ModStatusEffects.ENSHROUDED).isInfinite()){
                setStatusEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                setStatusEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                setStatusEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);
                setStatusEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 40), this);
            }
            return;
        }

        long currentTick = getWorld().getTickOrder();
        if(currentTick % 5 == 0){
            if(getWorld() == null) return;
            PlayerData data = StateSaverAndLoader.getPlayerState(getWorld().getPlayerByUuid(getUuid()));
            if(data == null) return;

            int currentLightLevel = getWorld().getLightLevel(getBlockPos());

            double delversFearStrenght = getAttributeValue(EntityAttributesME.DELVERS_FEAR_STRENGTH);

            if(delversFearStrenght > 0.0 && currentLightLevel < 3 && !getWorld().isSkyVisible(getBlockPos())) {
                data.addToDelversFearCountInSeconds();

                if(data.getDelversFearCountInSeconds() > delversFearStrenght){
                    addStatusEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, -1));
                    addStatusEffect(new MobEffectInstance(MobEffects.DARKNESS, -1));
                    addStatusEffect(new MobEffectInstance(MobEffects.WEAKNESS, -1));
                }
            } else {
                if(hasStatusEffect(ModStatusEffects.ENSHROUDED) && getStatusEffect(ModStatusEffects.ENSHROUDED).isInfinite()){
                    setStatusEffect(new MobEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                    setStatusEffect(new MobEffectInstance(MobEffects.DARKNESS, 40), this);
                    setStatusEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40), this);

                }
                data.resetDelversFearCount();
            }
        }
    }
}
