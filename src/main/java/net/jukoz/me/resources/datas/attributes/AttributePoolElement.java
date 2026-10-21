package net.jukoz.me.resources.datas.attributes;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import java.util.*;
import net.minecraft.util.RandomSource;

public class AttributePoolElement {
    private ResourceLocation identifier;
    private Double value;
    private Double valueMax;
    private List<AttributeModifierElement> modifiers;

    public static AttributePoolElement create(Attribute attribute, double defineValue){
        return new AttributePoolElement()
                .withIdentifier(Registries.ATTRIBUTE.getId(attribute))
                .withDefineValue(defineValue);
    }

    public static AttributePoolElement create(Holder<Attribute> attributeEntry, double defineValue){
        return create(attributeEntry.value(), defineValue);
    }
    public static AttributePoolElement create(Attribute attribute, double min, double max){
        return new AttributePoolElement()
                .withIdentifier(Registries.ATTRIBUTE.getId(attribute))
                .withMinMaxValue(min, max);
    }

    public static AttributePoolElement create(Holder<Attribute> attributeEntry, double min, double max){
        return create(attributeEntry.value(), min, max);
    }

    public static AttributePoolElement createFromNbt(CompoundTag nbtCompound){
        var newElement = new AttributePoolElement();
        newElement.withIdentifier(new ResourceLocation(nbtCompound.getString("id")));

        if(nbtCompound.contains("value"))
            newElement.withDefineValue(nbtCompound.getDouble("value"));
        else if(nbtCompound.contains("min") && nbtCompound.contains("max"))
            newElement.withMinMaxValue(nbtCompound.getDouble("min"), nbtCompound.getDouble("max"));

        if(nbtCompound.contains("modifiers")){
            newElement.withModifiers(nbtCompound.getList("modifiers", Tag.COMPOUND_TYPE));
        }

        return newElement;
    }

    private void withModifiers(ListTag modifierList) {
        this.modifiers = new ArrayList<>();
        modifierList.forEach(modifierNbt -> {
            modifiers.add(new AttributeModifierElement(NbtCompat.compound(modifierNbt)));
        });
    }

    public CompoundTag createNbt(){
        var nbtCompound = new CompoundTag();

        nbtCompound.putString("id", this.identifier.toString());
        if(this.valueMax == null)
            nbtCompound.putDouble("value", this.value);
        else{
            nbtCompound.putDouble("min", this.value);
            nbtCompound.putDouble("max", this.valueMax);
        }

        if(this.modifiers != null && !this.modifiers.isEmpty()){
            ListTag modifiersList = new ListTag();
            for(AttributeModifierElement modifier : this.modifiers){
                modifiersList.add(modifier.toNbt());
            }
            nbtCompound.put("modifiers", modifiersList);
        }
        return nbtCompound;
    }

    public AttributePoolElement withIdentifier(ResourceLocation newIdentifier) {
        this.identifier = newIdentifier;
        return this;
    }

    public AttributePoolElement withDefineValue(Double newDefineValue) {
        this.value = newDefineValue;
        return this;
    }

    public AttributePoolElement withMinMaxValue(Double newMinValue, Double newMaxValue) {
        this.value = newMinValue;
        this.valueMax = newMaxValue;
        return this;
    }

    public AttributePoolElement withModifier(ResourceLocation identifier, double value) {
        withModifier(identifier, value, AttributeModifier.Operation.MULTIPLY_TOTAL);
        return this;
    }

    public AttributePoolElement withModifier(ResourceLocation identifier, double value, AttributeModifier.Operation operation) {
        if(modifiers == null)
            modifiers = new ArrayList<>();

        modifiers.add(new AttributeModifierElement(identifier, value, operation));
        return this;
    }

    public ResourceLocation getIdentifier(){
        return this.identifier;
    }

    public double getValue(){
        if(this.valueMax == null){
            return this.value;
        }
        RandomSource random = new RandomSource();
        return random.nextDouble(value, valueMax);
    }

    public boolean hasModifiers(){
        return this.modifiers != null && !this.modifiers.isEmpty();
    }

    public List<AttributeModifierElement> getModifiers() {
        if(modifiers == null)
            return new ArrayList<>();
        return modifiers;
    }

    public static CompoundTag createAttributeNbtListFromPlayer(Player player) {
        ListTag attributeList = new ListTag();
        var registry = player.level().registryAccess().getOptional(Registries.ATTRIBUTE).get();
        Collection<AttributeInstance> attributes = new ArrayList<>();
        var entries = registry.streamEntries().toList();
        for(var entry : entries){
            AttributeInstance instance = player.getAttributeInstance(entry.value());
            if(instance == null)
                continue;
            attributes.add(instance);
        }
        attributes.forEach(attribute -> {
            AttributePoolElement attributePoolElement = new AttributePoolElement();
            attributePoolElement.withIdentifier(MiddleEarth.fetchId(net.minecraft.core.registries.Registries.ATTRIBUTE.getId(attribute.getAttribute()).toString()));
            attributePoolElement.withDefineValue(attribute.getBaseValue());
            for(var modifier : attribute.getModifiers())
                attributePoolElement.withModifier(MiddleEarth.fetchId(modifier.getName()), modifier.getValue(), modifier.getOperation());
            attributeList.add(attributePoolElement.createNbt());
        });
        CompoundTag compound = new CompoundTag();
        compound.put("attributes", attributeList);
        return compound;
    }

    public static List<AttributePoolElement> obtainAttributeList(CompoundTag nbtCompound) {
        List<AttributePoolElement> attributePoolElementList = new ArrayList<>();

        ListTag nbtList = nbtCompound.getList("attributes", Tag.COMPOUND_TYPE);

        nbtList.forEach(attribute -> {
            attributePoolElementList.add(AttributePoolElement.createFromNbt(NbtCompat.compound(attribute)));
        });
        return attributePoolElementList;
    }
}
