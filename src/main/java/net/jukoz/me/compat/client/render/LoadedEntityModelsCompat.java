package net.jukoz.me.compat.client.render;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.EntityModelLayer;
import net.minecraft.client.model.EntityModelLoader;

/**
 * R1 shim for 1.21.8 {@code LoadedEntityModelsCompat}: 1.20.1 exposes the same model-part
 * lookup through {@link EntityModelLoader}. The donor package
 * ({@code net.minecraft.client.render.entity.model.LoadedEntityModelsCompat}) does not exist in
 * 1.20.1, so R2/R3 re-point the call sites at this class.
 */
public class LoadedEntityModelsCompat {
    private final EntityModelLoader loader;

    public LoadedEntityModelsCompat(EntityModelLoader loader) {
        this.loader = loader;
    }

    public ModelPart getModelPart(EntityModelLayer layer) {
        return loader.getModelPart(layer);
    }
}
