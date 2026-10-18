package net.jukoz.me.resources.datas.attributes;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

public class AttributeModifierElement {
    private EntityAttributeModifier modifier;

    public AttributeModifierElement(NbtCompound modifierNbt) {
        this.modifier = new EntityAttributeModifier(
                modifierNbt.getString("id"),
                modifierNbt.getDouble("value"),
                EntityAttributeModifier.Operation.valueOf(modifierNbt.getString("operation"))
        );
    }

    public AttributeModifierElement(Identifier identifier, double value, EntityAttributeModifier.Operation operation) {
        this.modifier = new EntityAttributeModifier(identifier, value, operation);
    }


    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putString("id", modifier.getName());
        nbt.putDouble("value", modifier.getValue());
        nbt.putString("operation", modifier.getOperation().name());
        return nbt;
    }

    public Identifier getIdentifier() {
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
