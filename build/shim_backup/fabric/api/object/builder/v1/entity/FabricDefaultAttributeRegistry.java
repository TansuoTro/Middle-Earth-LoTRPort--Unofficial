package net.fabricmc.fabric.api.object.builder.v1.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Forge compat shim for Fabric's {@code FabricDefaultAttributeRegistry}. Fabric registers the
 * suppliers immediately; Forge does it in {@link EntityAttributeCreationEvent}, so the pairs are
 * buffered here and replayed when that event fires.
 */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FabricDefaultAttributeRegistry {
    private static final Map<EntityType<? extends LivingEntity>, AttributeSupplier> SUPPLIERS =
            new LinkedHashMap<>();

    private FabricDefaultAttributeRegistry() {}

    public static void register(EntityType<? extends LivingEntity> type, AttributeSupplier supplier) {
        SUPPLIERS.put(type, supplier);
    }

    @SubscribeEvent
    public static void onCreateAttributes(EntityAttributeCreationEvent event) {
        SUPPLIERS.forEach(event::put);
    }
}
