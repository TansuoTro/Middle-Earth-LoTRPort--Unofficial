package net.jukoz.me.entity.npcs.initializer;

import net.jukoz.me.item.MEComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryOps;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.npcs.data.NpcInitializationData;
import net.jukoz.me.item.EggItemsME;
import net.jukoz.me.item.dataComponents.FactionDataComponent;
import net.jukoz.me.item.dataComponents.RaceDataComponent;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.npc_types.NpcType;
import java.util.List;
import net.minecraft.core.HolderLookup;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcType, wrapper);
    }

    public static ItemStack getSpawnEgg(Level world, ResourceLocation identifier) {
        NpcType npcType = world.registryAccess().get(DynamicRegistriesME.NPC_TYPE).get(identifier);
        HolderLookup.Provider wrapperLookup = world.registryAccess();
        return buildItemStackBasedOnNpcData(npcType, wrapperLookup);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcType npcType, HolderLookup.Provider wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;

        ResourceLocation itemId = MiddleEarth.append(npcType.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcType.getId(), false);

        RegistryOps<Tag> ops = RegistryOps.create(
                NbtOps.INSTANCE,
                wrapper
        );

        Tag element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .result().orElse(new CompoundTag());
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        itemStack.getOrCreateSubNbt("EntityTag").copyFrom(compoundData);
        MEComponents.set(itemStack, MEComponents.FACTION_DATA, new FactionDataComponent(npcType.getFactionIdentifier()));
        MEComponents.set(itemStack, MEComponents.RACE_DATA, new RaceDataComponent(npcType.getRace()));
        itemStack.setCustomName(Component.translatable(itemId.toLanguageKey("item")));
        MEComponents.set(itemStack, MEComponents.ITEM_MODEL, new ResourceLocation(MiddleEarth.MOD_ID, npcType.getId().getPath().replaceAll("\\.", "_") + "_spawn_egg"));

        return itemStack;
    }
}
