package net.jukoz.me.resources.datas.attributes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.attribute.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class AttributePool {
    private List<AttributePoolElement> pool;

    public AttributePool(){
        pool = new ArrayList<>();
    }

    public AttributePool addElement(AttributePoolElement element){
        pool.add(element);
        return this;
    }
    public AttributePool addElements(List<AttributePoolElement> elements){
        pool.addAll(elements);
        return this;
    }

    public AttributePool(CompoundTag compound) {
        if(compound == null) return;
        if(compound.contains("pool")){
            pool = new ArrayList<>();
            var list =  compound.getList("pool", NbtElement.COMPOUND_TYPE);
            for(int i = 0; i < list.size(); i++){
                pool.add(AttributePoolElement.createFromNbt(list.getCompound(i)));
            }
        }
    }

    public CompoundTag getNbt() {
        CompoundTag nbt = new CompoundTag();
        NbtList list = new NbtList();

        for(var element : pool){
            list.add(element.createNbt());
        }

        nbt.put("pool", list);
        return nbt;
    }

    public boolean apply(LivingEntity entity){
        boolean couldResolveOneAttribute = false;

        for(var element : pool){
            var optAttributeEntry = Registries.ATTRIBUTE.getEntry(net.minecraft.resources.ResourceKey.of(net.minecraft.core.registries.Registries.ATTRIBUTE, element.getIdentifier()));
            if(optAttributeEntry.isPresent()){
                var attributeEntry = optAttributeEntry.get();

                var attributeInstance = entity.getAttributeInstance(attributeEntry.value());
                if(attributeInstance != null){
                    attributeInstance.clearModifiers();
                    attributeInstance.setBaseValue(element.getValue());
                    if(element.hasModifiers()){
                        for(AttributeModifierElement modifier : element.getModifiers()){
                            attributeInstance.addPersistentModifier(new EntityAttributeModifier(modifier.getIdentifier().toString(), modifier.getValue(), modifier.getOperation()));
                        }
                    }
                    couldResolveOneAttribute = true;
                }
            }
        }
        return couldResolveOneAttribute;
    }

    public static boolean reverse(LivingEntity entity){
        for (var identifier : Registries.ATTRIBUTE.getIds()) {
            var attributeInstance = entity.getAttributeInstance(Registries.ATTRIBUTE.getEntry(net.minecraft.resources.ResourceKey.of(net.minecraft.core.registries.Registries.ATTRIBUTE, identifier)).get().value());
            if (attributeInstance == null) {
                continue;
            }

            double defaultBaseValue = getDefaultAttributeValue(identifier, entity);
            if(defaultBaseValue == -99)
                continue;

            attributeInstance.setBaseValue(defaultBaseValue);
            attributeInstance.clearModifiers();
        }
        return true;
    }

    public static double getDefaultAttributeValue(ResourceLocation identifier, LivingEntity entity) {
        var defaultAttribute = Registries.ATTRIBUTE.get(identifier);
        if (defaultAttribute == null) {
            return -99;
        }

        var defaultAttributeEntry = Registries.ATTRIBUTE.getEntry(net.minecraft.resources.ResourceKey.of(net.minecraft.core.registries.Registries.ATTRIBUTE, identifier));
        if (defaultAttributeEntry.isEmpty()) {
            return -99;
        }

        var defaultAttributeContainer = DefaultAttributeRegistry.get((EntityType<? extends LivingEntity>) entity.getType());
        return defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get().value());
    }
    public static double getDefaultAttributeModifiers(ResourceLocation identifier, LivingEntity entity) {
        var defaultAttribute = Registries.ATTRIBUTE.get(identifier);
        if (defaultAttribute == null) {
            return -99;
        }

        var defaultAttributeEntry = Registries.ATTRIBUTE.getEntry(net.minecraft.resources.ResourceKey.of(net.minecraft.core.registries.Registries.ATTRIBUTE, identifier));
        if (defaultAttributeEntry.isEmpty()) {
            return -99;
        }

        var defaultAttributeContainer = DefaultAttributeRegistry.get((EntityType<? extends LivingEntity>) entity.getType());
        return defaultAttributeContainer.getBaseValue(defaultAttributeEntry.get().value());
    }

    public List<AttributePoolElement> getPool() {
        return pool;
    }
}
