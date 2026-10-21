package net.jukoz.me.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class AttributeModifierElement {
    private EntityAttributeModifier modifier;

    public AttributeModifierElement(CompoundTag modifierNbt) {
        this.modifier = new EntityAttributeModifier(
                modifierNbt.getString("id"),
                modifierNbt.getDouble("value"),
                EntityAttributeModifier.Operation.valueOf(modifierNbt.getString("operation"))
        );
    }

    public AttributeModifierElement(ResourceLocation identifier, double value, EntityAttributeModifier.Operation operation) {
        this.modifier = new EntityAttributeModifier(identifier.toString(), value, operation);
    }


    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("id", modifier.getName());
        nbt.putDouble("value", modifier.getValue());
        nbt.putString("operation", modifier.getOperation().name());
        return nbt;
    }

    public ResourceLocation getIdentifier() {
        return MiddleEarth.fetchId(modifier.getName());
    }
    public double getValue() {
        return modifier.getValue();
    }

    public EntityAttributeModifier.Operation getOperation() {
        return modifier.getOperation();
    }

    public EntityAttributeModifier getEntityAttributeModifier() {
        return modifier;
    }
}
