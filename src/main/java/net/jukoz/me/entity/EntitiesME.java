package net.jukoz.me.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.fire_of_orthanc.FireOfOrthancEntity;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.entity.barrel.BarrelEntity;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.entity.beasts.trolls.petrified.PetrifiedTrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.projectile.WebbedEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.projectile.pinecone.LitPineconeEntity;
import net.jukoz.me.entity.projectile.pinecone.PineconeEntity;
import net.jukoz.me.entity.projectile.smoke.SmokeRingProjectileEntity;
import net.jukoz.me.entity.projectile.spear.SpearEntity;
import net.jukoz.me.entity.seat.SeatEntity;
import net.jukoz.me.entity.spider.larva.ShelobiteLarvaEntity;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.jukoz.me.entity.spider.spawn.SpawnOfShelobEntity;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.registries.RegistryAliasesME;

import java.lang.reflect.Method;
import java.util.function.Supplier;
public class EntitiesME {
    // Npc
    public static final EntityType<NpcEntity> NPC = register("npc",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, NpcEntity::new).dimensions(EntityDimensions.fixed(0.8f, 1.8f)));

    // Mounts
    public static final EntityType<WargEntity> WARG = register("warg",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, WargEntity::new).dimensions(EntityDimensions.fixed(1.4f, 1.4f)));
    public static final EntityType<BroadhoofGoatEntity> BROADHOOF_GOAT = register("broadhoof_goat",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, BroadhoofGoatEntity::new).dimensions(EntityDimensions.fixed(1.4f, 1.4f)));
    public static final EntityType<GreatHornEntity> GREAT_HORN = register("great_horn",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, GreatHornEntity::new).dimensions(EntityDimensions.fixed(1.3f, 1.8f)));

    public static final EntityType<BarrelEntity> REINFORCED_BARREL;

    // Spiders
    public static final EntityType<ShelobiteLarvaEntity> SHELOBITE_LARVA = register("shelobite_larva",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShelobiteLarvaEntity::new).dimensions(EntityDimensions.fixed(0.4f, 0.3f)));
    public static final EntityType<ShelobiteScuttlerEntity> SHELOBITE_SCUTTLER = register("shelobite_scuttler",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShelobiteScuttlerEntity::new).dimensions(EntityDimensions.fixed(1.15f, 0.9f)));
    public static final EntityType<SpawnOfShelobEntity> SPAWN_OF_SHELOB = register("spawn_of_shelob",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SpawnOfShelobEntity::new).dimensions(EntityDimensions.fixed(1.75f, 1.4f)));

    // Trolls
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = register("snow_troll",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SnowTrollEntity::new).dimensions(EntityDimensions.fixed(2.2f, 2.5f)));
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = register("cave_troll",
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, CaveTrollEntity::new).dimensions(EntityDimensions.fixed(2.2f, 3.5f)));

    public static final EntityType<StoneTrollEntity> STONE_TROLL = register("stone_troll",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, StoneTrollEntity::new).dimensions(EntityDimensions.fixed(1.4f, 3.4f)));
    public static final EntityType<PetrifiedTrollEntity> PETRIFIED_TROLL = register("petrified_troll",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PetrifiedTrollEntity::new).dimensions(EntityDimensions.fixed(1.4f, 3.4f)));

    ///* Weapons *///
    public static final EntityType<FireOfOrthancEntity> FIRE_OF_ORTHANC = registerEntity("fire_of_orthanc", FireOfOrthancEntity::new, SpawnGroup.MISC, 0.65F, 0.65F);
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<PineconeEntity> PINECONE = registerEntity("pinecone", PineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<LitPineconeEntity> LIT_PINECONE = registerEntity("lit_pinecone", LitPineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<BoulderEntity> BOULDER = registerEntity("boulder", BoulderEntity::new, SpawnGroup.MISC, 1f, 1f);
    public static final EntityType<WebbedEntity> WEB = registerEntity("web", WebbedEntity::new, SpawnGroup.MISC, 0.5F, 0.5F);

    /// * Projectiles *///
    public static final EntityType<SmokeRingProjectileEntity> SMOKE_RING_PROJECTILE = registerEntity("smoke_ring_projectile", SmokeRingProjectileEntity::new, SpawnGroup.MISC, 0.5F, 0.5F);

    public static final EntityType<SpearEntity> SPEAR = registerEntity("spear", SpearEntity::new, SpawnGroup.MISC, 1f, 1f);

    // Seat
    public static final EntityType<SeatEntity> SEAT_ENTITY = register("seat_entity",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SeatEntity::new).dimensions(EntityDimensions.fixed(0.1F, 0.1F)));


    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE, MiddleEarth.of(name),
                FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.fixed(width, height)).build());
    }

    private static <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType<T> type) {
        EntityType<T> entityType = Registry.register(Registries.ENTITY_TYPE, key, type);
        TranslationEntries.entityEntries.add(entityType);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ENTITY_TYPE, entityType.getUntranslatedName()));
        return entityType;
    }

    private static EntityType.EntityFactory<BarrelEntity> getBoatFactory(Supplier<net.minecraft.world.item.Item> itemSupplier) {
        return (type, world) -> new BarrelEntity(type, world, itemSupplier);
    }

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> builder) {
        return register(keyOf(id), builder.build());
    }

    private static ResourceKey<EntityType<?>> keyOf(String id) {
        return ResourceKey.of(Registries.ENTITY_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, id));
    }


    public static void registerModEntities() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);

        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_TROLL, CaveTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(BROADHOOF_GOAT, BroadhoofGoatEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GREAT_HORN, GreatHornEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(WARG, WargEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(SHELOBITE_LARVA, ShelobiteLarvaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SHELOBITE_SCUTTLER, ShelobiteScuttlerEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SPAWN_OF_SHELOB, SpawnOfShelobEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(NPC, NpcEntity.setAttributes());
        registerSpawnRestrictions();
    }

    private static void registerSpawnRestrictions(){
        SpawnRestriction.Location onGroundLocation = SpawnRestriction.Location.ON_GROUND;
        Heightmap.Type heightmapType = Heightmap.Type.MOTION_BLOCKING_NO_LEAVES;

        registerSpawnRestriction(SHELOBITE_LARVA, onGroundLocation, heightmapType, ShelobiteLarvaEntity::canSpawn);
        registerSpawnRestriction(SHELOBITE_SCUTTLER, onGroundLocation, heightmapType, ShelobiteScuttlerEntity::canSpawn);
        registerSpawnRestriction(SPAWN_OF_SHELOB, onGroundLocation, heightmapType, SpawnOfShelobEntity::canSpawn);

        registerSpawnRestriction(CAVE_TROLL, onGroundLocation, heightmapType, CaveTrollEntity::canSpawn);
        registerSpawnRestriction(NPC, onGroundLocation, heightmapType, NpcEntity::canSpawn);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Mob> void registerSpawnRestriction(EntityType<T> type, SpawnRestriction.Location location, Heightmap.Type heightmap,
                                                                       SpawnRestriction.SpawnPredicate<T> predicate) {
        try {
            Method method = SpawnRestriction.class.getDeclaredMethod("register", EntityType.class,
                    SpawnRestriction.Location.class, Heightmap.Type.class, SpawnRestriction.SpawnPredicate.class);
            method.setAccessible(true);
            method.invoke(null, type, location, heightmap, predicate);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to register spawn restriction for " + type, e);
        }
    }

    static {
        REINFORCED_BARREL = register("reinforced_barrel", FabricEntityTypeBuilder.create(SpawnGroup.MISC, getBoatFactory(() -> {
            return ResourceItemsME.REINFORCED_BARREL;
        })).dimensions(EntityDimensions.fixed(1.2F, 1.2F)).trackRangeChunks(10));

    }
}
