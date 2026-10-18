package net.jukoz.me.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
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

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow public MinecraftServer server;
    @Shadow public ServerPlayerInteractionManager interactionManager;

    public ServerPlayerEntityMixin(MinecraftServer server, ServerWorld world, GameProfile profile) {
        super(world, world.getSpawnPos(), 0.0F, profile);
    }

    @Inject(method = "getTeleportTarget", at = @At("RETURN"), cancellable = true)
    private void me$getTeleportTarget(ServerWorld targetWorld, CallbackInfoReturnable<TeleportTarget> cir) {
        if (!ModDimensions.isInMiddleEarth(this.getWorld())) {
            return;
        }
        if (tryToOverrideSpawn(cir)) {
            return;
        }
        resetSpawn(cir);
    }

    @Unique
    private void resetSpawn(CallbackInfoReturnable<TeleportTarget> cir) {
        if (this.getServer() == null) return;

        PlayerManager manager = this.getServer().getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());
        if (foundPlayer == null) return;
        foundPlayer.setSpawnPoint(World.OVERWORLD, foundPlayer.getServer().getOverworld().getSpawnPos(),
                foundPlayer.getServer().getOverworld().getSpawnAngle(), true, false);

        cir.setReturnValue(new TeleportTarget(this.server.getOverworld().getSpawnPos().toCenterPos(), Vec3d.ZERO, 0, 0));
    }

    @Unique
    private boolean tryToOverrideSpawn(CallbackInfoReturnable<TeleportTarget> cir) {
        MinecraftServer server = this.getServer();

        if (server == null) return false;
        PlayerManager manager = server.getPlayerManager();
        ServerPlayerEntity foundPlayer = manager.getPlayer(this.getUuid());

        if (foundPlayer == null) return false;
        SpawnData data = PlayerDataService.getPlayerSpawnData(foundPlayer, getWorld());
        if (ModDimensions.isInMiddleEarth(this.getWorld()) && data != null && data.getIdentifier() != null) {
            BlockPos spawnCoordinates = data.getWorldCoordinateBlockPos();
            if (spawnCoordinates != null) {
                ServerWorld MEWorld = this.server.getWorld(ModDimensions.ME_WORLD_KEY);
                if (MEWorld != null) {
                    Vec3d coordinates = new Vec3d(spawnCoordinates.getX(), spawnCoordinates.getY() + 1, spawnCoordinates.getZ());

                    foundPlayer.setSpawnPoint(ModDimensions.ME_WORLD_KEY,
                            new BlockPos((int) coordinates.x, (int) coordinates.y, (int) coordinates.z), 0, true, false);

                    cir.setReturnValue(new TeleportTarget(coordinates, Vec3d.ZERO, 0, 0));
                    return true;
                }
            }
        }
        foundPlayer.setSpawnPoint(World.OVERWORLD, server.getOverworld().getSpawnPos(),
                server.getOverworld().getSpawnAngle(), true, false);

        cir.setReturnValue(new TeleportTarget(server.getOverworld().getSpawnPos().toCenterPos(), Vec3d.ZERO, 0, 0));
        return false;
    }

    @Override
    public boolean isSpectator() {
        return this.interactionManager.getGameMode() == GameMode.SPECTATOR;
    }

    @Override
    public boolean isCreative() {
        return this.interactionManager.getGameMode() == GameMode.CREATIVE;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        PlayerMovementData.addAFKTime((IEntityDataSaver) this,1);
        if(isCreative() || isSpectator()) {
            if(hasStatusEffect(ModStatusEffects.ENSHROUDED) && getStatusEffect(ModStatusEffects.ENSHROUDED).isInfinite()){
                setStatusEffect(new StatusEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40), this);
                setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 40), this);
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
                    addStatusEffect(new StatusEffectInstance(ModStatusEffects.ENSHROUDED, -1));
                    addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, -1));
                    addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, -1));
                }
            } else {
                if(hasStatusEffect(ModStatusEffects.ENSHROUDED) && getStatusEffect(ModStatusEffects.ENSHROUDED).isInfinite()){
                    setStatusEffect(new StatusEffectInstance(ModStatusEffects.ENSHROUDED, 40), this);
                    setStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 40), this);
                    setStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40), this);

                }
                data.resetDelversFearCount();
            }
        }
    }
}