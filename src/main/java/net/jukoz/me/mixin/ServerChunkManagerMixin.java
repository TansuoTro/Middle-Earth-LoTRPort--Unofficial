package net.jukoz.me.mixin;

import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerChunkCache.class)
public class ServerChunkManagerMixin {

    /* // Keep this method in case of CPR for spawners chunk tick
    @Shadow private ServerLevel world;
    @Inject(method = "tickChunks", locals = LocalCapture.CAPTURE_FAILHARD, at= @At(value = "INVOKE", shift = At.Shift.AFTER,
            target = "Lnet/minecraft/util/Util;shuffle(Ljava/util/List;Lnet/minecraft/util/math/random/RandomSource;)V"))
    public void tickChunks(CallbackInfo ci, long l, long m, ProfilerFiller profiler, List list, int i, NaturalSpawner.SpawnState info, boolean bl) {
        System.out.println("Can mob spawn: " + bl);
        boolean inMiddleEarth = ModDimensions.isInMiddleEarth(this.world);
        System.out.println("Is in Middle-earth: " + inMiddleEarth);
        if(bl && inMiddleEarth) {
            System.out.println("tick spawners");
            this.world.tickSpawners(false, false);
        }
    }*/
}
