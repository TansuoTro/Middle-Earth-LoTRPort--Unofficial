package net.fabricmc.fabric.api.client.rendering.v1;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/** Forge compat shim for Fabric's {@code EntityModelLayerRegistry}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class EntityModelLayerRegistry {
    private record Layer(ModelLayerLocation location, Supplier<LayerDefinition> definition) {}

    private static final List<Layer> LAYERS = new ArrayList<>();

    private EntityModelLayerRegistry() {}

    /** Fabric's callback shape: {@code registerModelLayer(id, () -> LayerDefinition.create(...))}. */
    public interface TexturedModelDataProvider extends Supplier<LayerDefinition> {
    }

    public static void registerModelLayer(ModelLayerLocation location, TexturedModelDataProvider provider) {
        LAYERS.add(new Layer(location, provider));
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LAYERS.forEach(layer -> event.registerLayerDefinition(layer.location(), layer.definition()));
    }
}
