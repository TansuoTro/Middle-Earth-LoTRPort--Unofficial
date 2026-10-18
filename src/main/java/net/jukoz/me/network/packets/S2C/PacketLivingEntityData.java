package net.jukoz.me.network.packets.S2C;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class PacketLivingEntityData extends ServerToClientPacket<PacketLivingEntityData> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_living_entity_data");
    public static final PacketType<PacketLivingEntityData> TYPE = PacketType.create(ID, PacketLivingEntityData::new);

    private final int entityId;
    private final StatusEffectInstance statusEffectInstance;

    public PacketLivingEntityData(int entityId, StatusEffectInstance statusEffectInstance) {
        this.entityId = entityId;
        this.statusEffectInstance = statusEffectInstance;
    }



    public PacketLivingEntityData(PacketByteBuf buf) {
        this(buf.readInt(), StatusEffectInstance.fromNbt(buf.readNbt()));
    }

    @Override
    public PacketType<PacketLivingEntityData> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeNbt(this.statusEffectInstance.writeNbt(new NbtCompound()));
    }

    @Override
    public void process(ClientPacketContext context) {
        Entity entity = context.player().getWorld().getEntityById(entityId);
        if(entity instanceof LivingEntity livingEntity) {
            livingEntity.addStatusEffect(statusEffectInstance);
        }
    }
}
