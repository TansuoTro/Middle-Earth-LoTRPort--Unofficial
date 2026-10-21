package net.jukoz.me.item.utils;

import net.minecraft.client.MinecraftClient;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.ArmorTrimMaterial;
import net.minecraft.world.item.armortrim.ArmorTrimMaterials;
import net.minecraft.world.item.armortrim.ArmorTrimPattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.MEArmorTrim;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.npcs.data.NpcInitializationData;
import net.jukoz.me.entity.npcs.initializer.NpcSpawnEggHelper;
import net.jukoz.me.item.EggItemsME;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class ItemGroupsUtil {
    private static final Comparator<Holder<NpcType>> NPC_DATA_COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparing(NpcType::getId));

    public static void addNpcEggs(ItemGroup.Entries entries, RegistryWrapper.Impl<NpcType> registryWrapper, Predicate<Holder<NpcType>> filter, RegistryWrapper.WrapperLookup lookup, ItemGroup.StackVisibility stackVisibility) {
        ResourceLocation randomSpawnEggId = MiddleEarth.of("npc_random_spawn_egg");

        ItemStack randomNpcSpawnEgg = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(null, true);

        RegistryOps<NbtElement> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                lookup
        );

        NbtElement element = NpcInitializationData.CODEC
                .encodeStart(ops, npcInitializationData)
                .result().orElse(new CompoundTag());
        compoundData.put(NpcEntity.KeyStrings.INITIALIZATION_DATA, element);

        randomNpcSpawnEgg.setCustomName(Component.translatable(randomSpawnEggId.toTranslationKey("item")));
        MEComponents.set(randomNpcSpawnEgg, MEComponents.ITEM_MODEL, new ResourceLocation(MiddleEarth.MOD_ID, randomSpawnEggId.getPath().replaceAll("\\.", "_")));

        entries.add(randomNpcSpawnEgg);

        registryWrapper.streamEntries().filter(filter).sorted(NPC_DATA_COMPARATOR).forEach(reference -> {
            ItemStack spawnEgg = NpcSpawnEggHelper.getSpawnEgg(reference.value(), lookup);
            entries.add(spawnEgg, stackVisibility);
        });
    }

    private static ItemStack addTrim(Item partItem, RegistryWrapper.WrapperLookup wrapper, RegistryKey<ArmorTrimMaterial> reference) {
        ItemStack item = partItem.getDefaultStack();
        Holder.Reference<ArmorTrimMaterial> material = wrapper.getOptionalWrapper(RegistryKeys.TRIM_MATERIAL).orElseThrow().getOrThrow(reference);
        Holder.Reference<ArmorTrimPattern> pattern = wrapper.getOptionalWrapper(RegistryKeys.TRIM_PATTERN).orElseThrow().getOptional(RegistryKey.of(RegistryKeys.TRIM_PATTERN, MiddleEarth.of( "smithing_part"))).orElse(null);
        MEArmorTrim.set(item, new ArmorTrim(material , pattern));
        return item;
    }

    public static Collection<ItemStack> processResourceItem(ItemStack itemStack, ItemGroup.DisplayContext displayContext) {
        if(PART_LIST.contains(itemStack.getItem())) {
            return processSmithingMaterial(itemStack.getItem(), displayContext);
        }
        return List.of(itemStack);
    }

    private static Collection<ItemStack> processSmithingMaterial(Item item, ItemGroup.DisplayContext displayContext) {
        RegistryWrapper.WrapperLookup wrapper = displayContext.lookup();
        List<ItemStack> trimSet = new ArrayList<>();
        for(RegistryKey<ArmorTrimMaterial> trimKey : METAL_LIST){
            trimSet.add(addTrim(item, wrapper, trimKey));
        }
        return trimSet;
    }

    private static final List<Item> PART_LIST = List.of(
            ResourceItemsME.ROD,
            ResourceItemsME.LARGE_ROD,
            ResourceItemsME.PICKAXE_HEAD,
            ResourceItemsME.AXE_HEAD,
            ResourceItemsME.SHOVEL_HEAD,
            ResourceItemsME.HOE_HEAD,
            ResourceItemsME.SHORT_BLADE,
            ResourceItemsME.BLADE,
            ResourceItemsME.LONG_BLADE,
            ResourceItemsME.SWORD_HILT,
            ResourceItemsME.MAIL_RING,
            ResourceItemsME.MAIL,
            ResourceItemsME.SCALE,
            ResourceItemsME.SCALE_MAIL,
            ResourceItemsME.ARMOR_PLATE,
            ResourceItemsME.HELMET_PLATE,
            ResourceItemsME.SHIELD_BORDER,
            ResourceItemsME.SHIELD_PLATE
    );

    private static final List<RegistryKey<ArmorTrimMaterial>> METAL_LIST = List.of(
            SmithingTrimMaterialsME.TIN,
            ArmorTrimMaterials.COPPER,
            SmithingTrimMaterialsME.BRONZE,
            SmithingTrimMaterialsME.CRUDE,
            ArmorTrimMaterials.IRON,
            SmithingTrimMaterialsME.SILVER,
            SmithingTrimMaterialsME.LEAD,
            ArmorTrimMaterials.GOLD,
            SmithingTrimMaterialsME.STEEL,
            SmithingTrimMaterialsME.EDHEL_STEEL,
            SmithingTrimMaterialsME.KHAZAD_STEEL,
            SmithingTrimMaterialsME.BURZUM_STEEL,
            SmithingTrimMaterialsME.MITHRIL
    );

    public static Collection<ItemStack> addFactionBanners(RegistryWrapper.WrapperLookup lookup) {
        List<ItemStack> bannerList = new ArrayList<>();
        List<Holder.Reference<Faction>> factions = lookup.getWrapperOrThrow(DynamicRegistriesME.FACTION).streamEntries().toList();
        for(Holder.Reference<Faction> factionReference : factions){
            ItemStack bannerItem = factionReference.value().getBannerItem(lookup);
            if(!bannerItem.isEmpty())
                bannerList.add(bannerItem);
        }
        return bannerList;
    }
}
