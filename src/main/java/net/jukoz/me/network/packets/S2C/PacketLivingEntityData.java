package net.jukoz.me.network.packets.S2C;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class PacketLivingEntityData extends ServerToClientPacket<PacketLivingEntityData> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_living_entity_data");
    public static final PacketType<PacketLivingEntityData> TYPE = PacketType.create(ID, PacketLivingEntityData::new);

    private final int entityId;
    private final StatusEffectInstance statusEffectInstance;

    public PacketLivingEntityData(int entityId, StatusEffectInstance statusEffectInstance) {
        this.entityId = entityId;
        this.statusEffectInstance = statusEffectInstance;
    }



    public PacketLivingEntityData(FriendlyByteBuf buf) {
        this(buf.readInt(), StatusEffectInstance.fromNbt(buf.readNbt()));
    }

    @Override
    public PacketType<PacketLivingEntityData> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeNbt(this.statusEffectInstance.writeNbt(new CompoundTag()));
    }

    @Override
    public void process(ClientPacketContext context) {
        Entity entity = context.player().getWorld().getEntityById(entityId);
        if(entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(statusEffectInstance);
        }
    }
}
