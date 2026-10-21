package net.jukoz.me.item.dataComponents;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/** The body-equipment properties that 1.20.1 does not store on Item.Settings. */
public record MountEquippableComponent(Identifier entityType, int armor, float toughness,
                                       float knockbackResistance, Identifier equipSound) {
    public static final Codec<MountEquippableComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("entity_type").forGetter(MountEquippableComponent::entityType),
            Codec.INT.fieldOf("armor").forGetter(MountEquippableComponent::armor),
            Codec.FLOAT.fieldOf("toughness").forGetter(MountEquippableComponent::toughness),
            Codec.FLOAT.fieldOf("knockback_resistance").forGetter(MountEquippableComponent::knockbackResistance),
            Identifier.CODEC.fieldOf("equip_sound").forGetter(MountEquippableComponent::equipSound)
    ).apply(instance, MountEquippableComponent::new));

    public boolean allows(Entity entity) {
        return entityType.equals(Registries.ENTITY_TYPE.getId(entity.getType()));
    }
}
