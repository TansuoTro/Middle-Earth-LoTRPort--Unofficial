package net.jukoz.me.item.utils;

import net.minecraft.client.Minecraft;
import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.armortrim.ArmorTrimMaterial;
import net.minecraft.world.item.armortrim.ArmorTrimMaterials;
import net.minecraft.world.item.armortrim.ArmorTrimPattern;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.item.armortrim.TrimPattern;

public class ItemGroupsUtil {
    private static final Comparator<Holder<NpcType>> NPC_DATA_COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparing(NpcType::getId));

    public static void addNpcEggs(CreativeModeTab.Entries entries, HolderLookup.Impl<NpcType> registryWrapper, Predicate<Holder<NpcType>> filter, HolderLookup.WrapperLookup lookup, CreativeModeTab.StackVisibility stackVisibility) {
        ResourceLocation randomSpawnEggId = MiddleEarth.of("npc_random_spawn_egg");

        ItemStack randomNpcSpawnEgg = new ItemStack(EggItemsME.NPC_SPAWN_EGG);

        CompoundTag compoundData = new CompoundTag();
        compoundData.putString("id", MiddleEarth.of("npc").toString());

        NpcInitializationData npcInitializationData = new NpcInitializationData(null, true);

        RegistryOps<Tag> ops = RegistryOps.of(
                NbtOps.INSTANCE,
                lookup
        );

        Tag element = NpcInitializationData.CODEC
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

    private static ItemStack addTrim(Item partItem, HolderLookup.WrapperLookup wrapper, ResourceKey<TrimMaterial> reference) {
        ItemStack item = partItem.getDefaultStack();
        Holder.Reference<TrimMaterial> material = wrapper.getOptionalWrapper(Registries.TRIM_MATERIAL).orElseThrow().getOrThrow(reference);
        Holder.Reference<TrimPattern> pattern = wrapper.getOptionalWrapper(Registries.TRIM_PATTERN).orElseThrow().getOptional(ResourceKey.of(Registries.TRIM_PATTERN, MiddleEarth.of( "smithing_part"))).orElse(null);
        MEArmorTrim.set(item, new ArmorTrim(material , pattern));
        return item;
    }

    public static Collection<ItemStack> processResourceItem(ItemStack itemStack, CreativeModeTab.DisplayContext displayContext) {
        if(PART_LIST.contains(itemStack.getItem())) {
            return processSmithingMaterial(itemStack.getItem(), displayContext);
        }
        return List.of(itemStack);
    }

    private static Collection<ItemStack> processSmithingMaterial(Item item, CreativeModeTab.DisplayContext displayContext) {
        HolderLookup.WrapperLookup wrapper = displayContext.lookup();
        List<ItemStack> trimSet = new ArrayList<>();
        for(ResourceKey<TrimMaterial> trimKey : METAL_LIST){
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

    private static final List<ResourceKey<TrimMaterial>> METAL_LIST = List.of(
            SmithingTrimMaterialsME.TIN,
            TrimMaterials.COPPER,
            SmithingTrimMaterialsME.BRONZE,
            SmithingTrimMaterialsME.CRUDE,
            TrimMaterials.IRON,
            SmithingTrimMaterialsME.SILVER,
            SmithingTrimMaterialsME.LEAD,
            TrimMaterials.GOLD,
            SmithingTrimMaterialsME.STEEL,
            SmithingTrimMaterialsME.EDHEL_STEEL,
            SmithingTrimMaterialsME.KHAZAD_STEEL,
            SmithingTrimMaterialsME.BURZUM_STEEL,
            SmithingTrimMaterialsME.MITHRIL
    );

    public static Collection<ItemStack> addFactionBanners(HolderLookup.WrapperLookup lookup) {
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
