package net.jukoz.me.compat.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.EntityModelLayer;
import java.util.HashSet;
import java.util.Set;

/**
 * Port shim: the 1.21 source tree carried two parallel model-layer registries
 * ({@code net.jukoz.me.entity.EntityModelLayersME} and
 * {@code net.jukoz.me.entity.model.ModEntityModelLayers}) that declare the same layer ids.
 * Fabric 1.20.1 throws {@code IllegalArgumentException: Cannot replace registration for entity
 * model layer ...} on the second declaration, which aborts {@code EntityModelLayersME.<clinit>}
 * and kills the initial resource reload (the client then crashes in LevelRenderer with a null
 * shader program). Registering through here makes the second declaration a no-op.
 */
@OnlyIn(Dist.CLIENT)
public final class MEModelLayers {
    private static final Set<ModelLayerLocation> REGISTERED = new HashSet<>();

    private MEModelLayers() {
    }

    public static ModelLayerLocation register(ModelLayerLocation layer,
                                           EntityModelLayerRegistry.TexturedModelDataProvider modelData) {
        if (REGISTERED.add(layer)) {
            EntityModelLayerRegistry.registerModelLayer(layer, modelData);
        }
        return layer;
    }
}
