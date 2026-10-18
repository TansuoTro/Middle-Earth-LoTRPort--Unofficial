package net.jukoz.me.entity.npcs.initializer;

import net.jukoz.me.item.MEComponents;
import net.minecraft.client.MinecraftClient;

import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.npcs.data.NpcInitializationData;
import net.jukoz.me.item.EggItemsME;
import net.jukoz.me.item.dataComponents.FactionDataComponent;
import net.jukoz.me.item.dataComponents.RaceDataComponent;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.npc_types.NpcType;

import java.util.List;

public class NpcSpawnEggHelper {
    public static ItemStack getSpawnEgg(NpcType npcType, RegistryWrapper.WrapperLookup wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;
        return buildItemStackBasedOnNpcData(npcType, wrapper);
    }

    public static ItemStack getSpawnEgg(World world, Identifier identifier) {
        NpcType npcType = world.getRegistryManager().get(DynamicRegistriesME.NPC_TYPE).get(identifier);
        RegistryWrapper.WrapperLookup wrapperLookup = world.getRegistryManager();
        return buildItemStackBasedOnNpcData(npcType, wrapperLookup);
    }

    private static ItemStack buildItemStackBasedOnNpcData(NpcType npcType, RegistryWrapper.WrapperLookup wrapper) {
        if(npcType == null)
            return ItemStack.EMPTY;

        Identifier itemId = MiddleEarth.append(npcType.getId(), "_spawn_egg");
        ItemStack itemStack = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        NbtCompound compoundData = new NbtCompound();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(npcType.getId(), false);

        RegistryOps<NbtElement> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                wrapper
        );

        NbtElement element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .getOrThrow();
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        itemStack.getOrCreateSubNbt("EntityTag").copyFrom(compoundData);
        MEComponents.set(itemStack, MEComponents.FACTION_DATA, new FactionDataComponent(npcType.getFactionIdentifier()));
        MEComponents.set(itemStack, MEComponents.RACE_DATA, new RaceDataComponent(npcType.getRace()));
        itemStack.setCustomName(Text.translatable(itemId.toTranslationKey("item")));
        MEComponents.set(itemStack, MEComponents.ITEM_MODEL, new Identifier(MiddleEarth.MOD_ID, npcType.getId().getPath().replaceAll("\\.", "_") + "_spawn_egg"));

        return itemStack;
    }
}
