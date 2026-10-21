package net.fabricmc.fabric.api.object.builder.v1.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;

/** Forge compat shim for Fabric's {@code FabricEntityTypeBuilder}. */
public final class FabricEntityTypeBuilder<T extends Entity> {
    private final EntityType.EntityFactory<T> factory;
    private final MobCategory category;
    private EntityDimensions dimensions = EntityDimensions.fixed(0.6F, 1.8F);
    private int clientTrackingRange = 5;
    private int updateInterval = 3;
    private boolean fireImmune;
    private boolean canSpawnFarFromPlayer;

    private FabricEntityTypeBuilder(EntityType.EntityFactory<T> factory, MobCategory category) {
        this.factory = factory;
        this.category = category;
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> create(EntityType.EntityFactory<T> factory,
                                                                       MobCategory category) {
        return new FabricEntityTypeBuilder<>(factory, category);
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> createLiving(
            EntityType.EntityFactory<T> factory, MobCategory category) {
        return create(factory, category);
    }

    public static <T extends Entity> FabricEntityTypeBuilder<T> createMob(
            EntityType.EntityFactory<T> factory, MobCategory category) {
        return create(factory, category);
    }

    public FabricEntityTypeBuilder<T> dimensions(EntityDimensions dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public FabricEntityTypeBuilder<T> trackedUpdateInterval(int interval) {
        this.updateInterval = interval;
        return this;
    }

    public FabricEntityTypeBuilder<T> trackedUpdateTickInterval(int interval) {
        return trackedUpdateInterval(interval);
    }

    public FabricEntityTypeBuilder<T> trackingRange(int range) {
        this.clientTrackingRange = range;
        return this;
    }

    public FabricEntityTypeBuilder<T> fireImmune() {
        this.fireImmune = true;
        return this;
    }

    public FabricEntityTypeBuilder<T> canSpawnFarFromPlayer() {
        this.canSpawnFarFromPlayer = true;
        return this;
    }

    public EntityType.Builder<T> build() {
        return EntityType.Builder.of(this.factory, this.category)
                .sized(this.dimensions.width, this.dimensions.height)
                .clientTrackingRange(this.clientTrackingRange)
                .updateInterval(this.updateInterval)
                .fireImmune(this.fireImmune)
                .canSpawnFarFromPlayer(this.canSpawnFarFromPlayer);
    }

}
