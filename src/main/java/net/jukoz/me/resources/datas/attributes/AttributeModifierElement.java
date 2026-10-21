package net.jukoz.me.resources.datas.attributes;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class AttributeModifierElement {
    private AttributeModifier modifier;

    public AttributeModifierElement(CompoundTag modifierNbt) {
        this.modifier = new AttributeModifier(
                modifierNbt.getString("id"),
                modifierNbt.getDouble("value"),
                AttributeModifier.Operation.valueOf(modifierNbt.getString("operation"))
        );
    }

    public AttributeModifierElement(ResourceLocation identifier, double value, AttributeModifier.Operation operation) {
        this.modifier = new AttributeModifier(identifier.toString(), value, operation);
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

    public AttributeModifier.Operation getOperation() {
        return modifier.getOperation();
    }

    public AttributeModifier getEntityAttributeModifier() {
        return modifier;
    }
}
