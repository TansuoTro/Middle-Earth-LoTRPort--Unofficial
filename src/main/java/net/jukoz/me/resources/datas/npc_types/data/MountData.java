package net.jukoz.me.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.compat.DyedColorComponent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;

public class MountData {
    public static class Fields {
        public static final String ENTITY_TYPE = "entity_type";
        public static final String NPC_TYPE = "npc_type";
        public static final String ARMOR = "armor_id";
        public static final String PASSENGER_SLOTS = "passenger_slots";
    }

    public static final Codec<MountData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf(Fields.ENTITY_TYPE).forGetter(MountData::getEntityType),
            ResourceLocation.CODEC.optionalFieldOf(Fields.NPC_TYPE).forGetter(MountData::getOptionalNpcType),
            ItemStack.CODEC.optionalFieldOf(Fields.ARMOR).forGetter(MountData::getOptionalArmor),
            MountPassengerSlotData.CODEC.listOf().fieldOf(Fields.PASSENGER_SLOTS).forGetter(MountData::getPassengerSlots)
    ).apply(instance, MountData::new));

    private ResourceLocation entityType;
    private ResourceLocation npcType;
    private ItemStack armor;
    private List<MountPassengerSlotData> passengerSlots;

    private MountData(
            ResourceLocation entityType,
            Optional<ResourceLocation> npcType,
            Optional<ItemStack> armor,
            List<MountPassengerSlotData> passengerSlots
    ) {
        this.entityType = entityType;
        this.npcType = npcType.orElse(null);
        this.armor = armor.orElse(null);
        this.passengerSlots = passengerSlots;
    }

    public MountData(EntityType<?> entity) {
        this.entityType = Registries.ENTITY_TYPE.getId(entity);
        this.armor = null;
    }

    public MountData(ResourceKey<NpcType> npcType){
        this.entityType = Registries.ENTITY_TYPE.getId(EntitiesME.NPC);
        this.npcType = npcType.getValue();
    }

    public MountData withArmor(ItemStack armorItem){
        this.armor = armorItem;
        return this;
    }

    public MountData withArmor(Item armorItem){
        this.armor = armorItem.getDefaultStack();
        return this;
    }

    public MountData withColor(DyedColorComponent color){
        if(this.armor == null)
            return this;
        MEComponents.set(this.armor, MEComponents.DYED_COLOR, color);
        return this;
    }

    private ResourceLocation getEntityType() {
        return entityType;
    }
    private Optional<ResourceLocation> getOptionalNpcType() {
        return Optional.ofNullable(npcType);
    }
    private Optional<ItemStack> getOptionalArmor() {
        return Optional.ofNullable(armor);
    }

    private List<MountPassengerSlotData> getPassengerSlots() {
        if(passengerSlots == null)
            return new ArrayList<>();
        return passengerSlots;
    }

    public MountData withPassengerSlots(MountPassengerSlotData... passengerSlots) {
        this.passengerSlots = Arrays.asList(passengerSlots);
        return this;
    }

    public void createEntity(ServerLevel world, LivingEntity owner) {
        if(this.entityType == null || owner.hasVehicle() || owner.hasPassengers())
            return;
        EntityType<?> type = Registries.ENTITY_TYPE.get(this.entityType);
        var notLiving = type.create(world);
        if(notLiving == null)
            return;
        if(notLiving instanceof LivingEntity entity){
            entity.setPosition(owner.getPos());
            entity.equipStack(EquipmentSlot.CHEST, Items.SADDLE.asItem().getDefaultStack());
            if(armor != null)
                entity.equipStack(EquipmentSlot.CHEST, this.armor);

            if (entity instanceof Mob mob) {
                mob.initialize(
                        world,
                        world.getLocalDifficulty(owner.getBlockPos()),
                        MobSpawnType.EVENT,
                        null,
                        null
                );
            }
            if(entity instanceof AbstractHorse horse){
                horse.setTame(true);
                horse.setOwnerUuid(owner.getUuid());
                if(horse instanceof AbstractBeastEntity beast){
                    beast.tameBeast(owner);
                }

            }

            owner.startRiding(entity, true);
            world.spawnEntity(entity);

            if(entity instanceof NpcEntity npc && npcType != null){
                npc.prepareNpcIdentifier(npcType);
                npc.prepare();
            }

            // Set other passengers
            passengerSlots.forEach(slot -> {
               LivingEntity passengerEntity = slot.createRandom(world, owner);
               if(passengerEntity != null)
                   passengerEntity.startRiding(entity, true);
            });

        }
    }
}
