package net.jukoz.me.world.features.tree;

import com.mojang.serialization.Codec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.features.tree.roots.MirkwoodRootPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;

public class ModRootPlacerType {
    public static final RootPlacerType<MirkwoodRootPlacer> MIRKWOOD_ROOT_PLACER = register(
            "mirkwood_root_placer", MirkwoodRootPlacer.CODEC);

    private static <P extends RootPlacer> RootPlacerType register(String id, Codec<P> codec) {
        return Registry.register(Registries.ROOT_PLACER_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, id), new RootPlacerType(codec));
    }
}
