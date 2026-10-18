package net.jukoz.me.item.utils;
import net.jukoz.me.compat.METext;import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.jukoz.me.MiddleEarth;import java.util.Map;public class SmithingTrimMaterialsME {
    public static final RegistryKey<ArmorTrimMaterial> BRONZE = of("bronze");
    public static final RegistryKey<ArmorTrimMaterial> BURZUM_STEEL = of("burzum_steel");
    public static final RegistryKey<ArmorTrimMaterial> CRUDE = of("crude");
    public static final RegistryKey<ArmorTrimMaterial> EDHEL_STEEL = of("edhel_steel");
    public static final RegistryKey<ArmorTrimMaterial> JADE = of("jade");
    public static final RegistryKey<ArmorTrimMaterial> KHAZAD_STEEL = of("khazad_steel");
    public static final RegistryKey<ArmorTrimMaterial> LEAD = of("lead");
    public static final RegistryKey<ArmorTrimMaterial> MITHRIL = of("mithril");
    public static final RegistryKey<ArmorTrimMaterial> SILVER = of("silver");
    public static final RegistryKey<ArmorTrimMaterial> STEEL = of("steel");
    public static final RegistryKey<ArmorTrimMaterial> TIN = of("tin");    public static void bootstrap(Registerable<ArmorTrimMaterial> registry) {
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
    }    // 1.20.1 ArmorTrimMaterial carries the item model index + asset name directly
    // instead of the donor's ArmorTrimAssets(AssetId, overrides).
    private static void register(Registerable<ArmorTrimMaterial> registry, RegistryKey<ArmorTrimMaterial> key, float itemModelIndex, String assetName, Style style) {
        Text text = Text.translatable(Util.createTranslationKey("trim_material", key.getValue())).fillStyle(style);
        registry.register(key, new ArmorTrimMaterial(assetName, Registries.ITEM.getEntry(Items.IRON_INGOT), itemModelIndex, Map.<ArmorMaterials, String>of(), text));
    }    private static RegistryKey<ArmorTrimMaterial> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, new Identifier(MiddleEarth.MOD_ID, id));
    }
}
