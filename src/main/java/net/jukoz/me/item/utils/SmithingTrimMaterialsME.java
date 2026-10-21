package net.jukoz.me.item.utils;

import net.jukoz.me.compat.METext;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.world.item.Items;
import net.jukoz.me.MiddleEarth;
import java.util.Map;
import net.minecraft.world.item.armortrim.TrimMaterial;

public class SmithingTrimMaterialsME {

    public static final ResourceKey<TrimMaterial> BRONZE = of("bronze");
    public static final ResourceKey<TrimMaterial> BURZUM_STEEL = of("burzum_steel");
    public static final ResourceKey<TrimMaterial> CRUDE = of("crude");
    public static final ResourceKey<TrimMaterial> EDHEL_STEEL = of("edhel_steel");
    public static final ResourceKey<TrimMaterial> JADE = of("jade");
    public static final ResourceKey<TrimMaterial> KHAZAD_STEEL = of("khazad_steel");
    public static final ResourceKey<TrimMaterial> LEAD = of("lead");
    public static final ResourceKey<TrimMaterial> MITHRIL = of("mithril");
    public static final ResourceKey<TrimMaterial> SILVER = of("silver");
    public static final ResourceKey<TrimMaterial> STEEL = of("steel");
    public static final ResourceKey<TrimMaterial> TIN = of("tin");    public static void bootstrap(BootstrapContext<TrimMaterial> registry) {
        register(registry, BRONZE, 0.1f, "bronze", Style.EMPTY.withColor(13151627));
        register(registry, BURZUM_STEEL, 0.2f, "burzum_steel", Style.EMPTY.withColor(5985355));
        register(registry, CRUDE, 0.3f, "crude", Style.EMPTY.withColor(7560021));
        register(registry, EDHEL_STEEL, 0.4f, "edhel_steel", Style.EMPTY.withColor(15921385));
        register(registry, JADE, 0.5f, "jade", Style.EMPTY.withColor(5869927));
        register(registry, KHAZAD_STEEL, 0.6f, "khazad_steel", Style.EMPTY.withColor(6778743));
        register(registry, LEAD, 0.7f, "lead", Style.EMPTY.withColor(6384761));
        register(registry, MITHRIL, 0.8f, "mithril", Style.EMPTY.withColor(14278631));
        register(registry, SILVER, 0.9f, "silver", Style.EMPTY.withColor(15397618));
        register(registry, STEEL, 1.0f, "steel", Style.EMPTY.withColor(0xECECEC));
        register(registry, TIN, 1.1f, "tin", Style.EMPTY.withColor(13026492));
    }    // 1.20.1 TrimMaterial carries the item model index + asset name directly
    // instead of the donor's ArmorTrimAssets(AssetId, overrides).
    private static void register(BootstrapContext<TrimMaterial> registry, ResourceKey<TrimMaterial> key, float itemModelIndex, String assetName, Style style) {
        Component text = Component.translatable(Util.createTranslationKey("trim_material", key.location())).fillStyle(style);
        registry.register(key, new TrimMaterial(assetName, Registries.ITEM.getEntry(Items.IRON_INGOT), itemModelIndex, Map.<ArmorMaterials, String>of(), text));
    }    private static ResourceKey<TrimMaterial> of(String id) {
        return ResourceKey.create(Registries.TRIM_MATERIAL, new ResourceLocation(MiddleEarth.MOD_ID, id));
    }
}
