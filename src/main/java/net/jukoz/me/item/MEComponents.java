package net.jukoz.me.item;

import com.mojang.serialization.Codec;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.item.dataComponents.*;
import net.jukoz.me.item.dataComponents.CooldownDataComponent;
import net.jukoz.me.item.dataComponents.FactionDataComponent;
import net.jukoz.me.item.dataComponents.RaceDataComponent;
import net.jukoz.me.item.dataComponents.SneakAttackDataComponent;
import net.jukoz.me.item.dataComponents.TemperatureDataComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.network.chat.Component;

/**
 * NBT-backed replacement for 1.21's data component API.
 *
 * <p>Porting notes: the 1.21.8 donor stores per-stack state with
 * {@code MEComponents.set(stack, MEComponents.X, value)} / {@code stack.get(...)}.
 * 1.20.1 has no component registry, so the same values are kept in the stack's
 * NBT under {@code MEComponents.<id>} and accessed through this class. Call sites
 * keep their shape: {@code MEComponents.get(stack, MEComponents.FACTION_DATA)}.
 */
public final class MEComponents {
    /** Root compound inside {@code ItemStack.tag}. */
    public static final String ROOT = "MEComponents";

    /**
     * Every registered component, in declaration order; used when building tooltips.
     * Declared before the component fields below because their initializers call
     * {@link #register}, which appends to this list (Java initializes statics in
     * textual order).
     */
    private static final java.util.List<MEComponent<?>> ALL = new java.util.ArrayList<>();

    public static final MEComponent<CooldownDataComponent> COOLDOWN =
            register("cooldown", CooldownDataComponent.CODEC);
    public static final MEComponent<TemperatureDataComponent> TEMPERATURE_DATA =
            register("temperature", TemperatureDataComponent.CODEC);
    public static final MEComponent<SneakAttackDataComponent> SNEAK_ATTACK_DATA =
            register("sneak_attack", SneakAttackDataComponent.CODEC);
    public static final MEComponent<FactionDataComponent> FACTION_DATA =
            register("faction", FactionDataComponent.CODEC);
    public static final MEComponent<RaceDataComponent> RACE_DATA =
            register("race", RaceDataComponent.CODEC);

    /** 1.21.2's default dye colour; read by DyeableItemMixin. */
    public static final MEComponent<DyedColorComponent> DYED_COLOR =
            register("dyed_color", DyedColorComponent.CODEC);

    public static final MEComponent<ArmorVariantDataComponent> ARMOR_VARIANT_DATA =
            register("armor_variant", ArmorVariantDataComponent.CODEC);
    public static final MEComponent<ArtisanDataComponent> ARTISAN_DATA =
            register("artisan", ArtisanDataComponent.CODEC);
    public static final MEComponent<SeasonDataComponent> SEASON_DATA =
            register("season", SeasonDataComponent.CODEC);
    public static final MEComponent<ArmorTierDataComponent> ARMOR_TIER_DATA =
            register("armor_tier", ArmorTierDataComponent.CODEC);
    public static final MEComponent<WeaponTypeDataComponent> WEAPON_TYPE_DATA =
            register("weapon_type", WeaponTypeDataComponent.CODEC);
    public static final MEComponent<BlockAuthorDataComponent> BLOCK_AUTHOR_DATA =
            register("block_author", BlockAuthorDataComponent.CODEC);
    public static final MEComponent<BackAttachmentDataComponent> BACK_ATTACHMENT_DATA =
            register("back_attachment", BackAttachmentDataComponent.CODEC);
    public static final MEComponent<HelmetAttachmentDataComponent> HELMET_ATTACHMENT_DATA =
            register("helmet_attachment", HelmetAttachmentDataComponent.CODEC);
    public static final MEComponent<MountArmorAddonComponent> MOUNT_ARMOR_DATA =
            register("mount_armor_addon", MountArmorAddonComponent.CODEC);

    /**
     * 1.21's ITEM_MODEL override. Only the mod's own renderers read it (PlateEntityRenderer,
     * WoodlandRealmCrownItem), so an NBT-backed ResourceLocation is enough; 1.20.1 has no
     * per-stack model component for the vanilla item renderer to consult.
     */
    public static final MEComponent<ResourceLocation> ITEM_MODEL =
            register("item_model", ResourceLocation.CODEC);

    /** 1.21's EQUIPPABLE asset id; only the mod's own renderers read it. */
    public static final MEComponent<ResourceLocation> EQUIPPABLE_ASSET =
            register("equippable_asset", ResourceLocation.CODEC);
    public static final MEComponent<MountEquippableComponent> MOUNT_EQUIPPABLE =
            register("mount_equippable", MountEquippableComponent.CODEC);

    /** Per-stack durability override; read by {@code ItemStackDurabilityMixin}. */
    public static final MEComponent<Integer> MAX_DAMAGE =
            register("max_damage", Codec.INT);

    /**
     * Components declared on the item itself (1.21's {@code Item.Settings#component}).
     * Keyed by identity because {@link Item} has no equals() worth using.
     */
    private static final Map<Item, Map<MEComponent<?>, Object>> ITEM_DEFAULTS = new IdentityHashMap<>();

    private MEComponents() {
    }

    /**
     * Declares a component that every stack of {@code item} carries unless it overrides it.
     *
     * <p>Replaces the donor's {@code new MESettings().component(key, value)} calls, which
     * have no 1.20.1 equivalent: 1.20.1 items are immutable once registered and carry no
     * component map, so the template is kept here and consulted by {@link #get}.
     */
    public static <T> void defaultFor(Item item, MEComponent<T> key, T value) {
        ITEM_DEFAULTS.computeIfAbsent(item, k -> new IdentityHashMap<>()).put(key, value);
    }

    /** Bulk form used by {@code ItemMixin} when an item is built from {@link MESettings}. */
    public static void captureDefaults(Item item, Map<MEComponent<?>, Object> components) {
        ITEM_DEFAULTS.computeIfAbsent(item, k -> new IdentityHashMap<>()).putAll(components);
    }

    /** Test hook. */
    public static void clearDefaults() {
        ITEM_DEFAULTS.clear();
    }

    private static <T> MEComponent<T> register(String path, Codec<T> codec) {
        MEComponent<T> component = new MEComponent<>(new ResourceLocation(MiddleEarth.MOD_ID, path), codec);
        ALL.add(component);
        return component;
    }

    /**
     * Appends the tooltip lines of every component on the stack that knows how to draw itself.
     * Called from {@code client.ItemStackTooltipMixin}; 1.21 gets this for free from the
     * component registry.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void appendTooltips(ItemStack stack, net.minecraft.world.item.TooltipFlag context, java.util.List<net.minecraft.network.chat.Component> tooltip) {
        appendTooltips(stack, context, tooltip, null);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void appendTooltips(ItemStack stack, net.minecraft.world.item.TooltipFlag context, java.util.List<net.minecraft.network.chat.Component> tooltip, net.minecraft.world.level.Level world) {
        tooltip.addAll(net.jukoz.me.item.ItemExtras.lore(stack.getItem()));
        for (MEComponent<?> key : ALL) {
            Object value = get(stack, (MEComponent) key);
            if (value instanceof net.jukoz.me.compat.TooltipAppender appender) {
                appender.appendTooltip(stack, context, tooltip, world);
            }
        }
    }

    // ------------------------------------------------------------------
    // generic accessors
    // ------------------------------------------------------------------

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T> T get(ItemStack stack, MEComponent<T> key) {
        CompoundTag tag = stack.getNbt();
        if (tag != null && tag.contains(ROOT, Tag.COMPOUND_TYPE)) {
            CompoundTag root = tag.getCompound(ROOT);
            String path = key.id().getPath();
            if (root.contains(path)) {
                T value = decode(key, root.get(path));
                if (value != null) {
                    return value;
                }
            }
        }
        Map<MEComponent<?>, Object> defaults = ITEM_DEFAULTS.get(stack.getItem());
        return defaults == null ? null : (T) defaults.get(key);
    }

    public static <T> T getOrDefault(ItemStack stack, MEComponent<T> key) {
        T value = get(stack, key);
        return value != null ? value : key.defaultValue();
    }

    /** Mirrors 1.21's {@code ItemStack#getOrDefault(ComponentType, T)}. */
    public static <T> T getOrDefault(ItemStack stack, MEComponent<T> key, T fallback) {
        T value = get(stack, key);
        return value != null ? value : fallback;
    }

    public static <T> boolean contains(ItemStack stack, MEComponent<T> key) {
        return get(stack, key) != null;
    }

    public static <T> void set(ItemStack stack, MEComponent<T> key, @Nullable T value) {
        if (value == null) {
            remove(stack, key);
            return;
        }
        Tag encoded = encode(key, value);
        if (encoded == null) {
            return;
        }
        stack.getOrCreateSubNbt(ROOT).put(key.id().getPath(), encoded);
        syncCustomModelData(stack, key, value);
    }

    /**
     * 1.21.4+ swaps hot-metal item models through an item-model condition keyed on the temperature
     * component; 1.20.1 item models can only key overrides off CustomModelData, so the temperature
     * is mirrored into that tag whenever it changes. The generated item models carry the matching
     * {@code custom_model_data} override (see ModelProvider#registerHotMetalModels).
     */
    private static void syncCustomModelData(ItemStack stack, MEComponent<?> key, @Nullable Object value) {
        if (key != TEMPERATURE_DATA) {
            return;
        }
        boolean hot = value instanceof TemperatureDataComponent temperature && temperature.temperature() > 0;
        if (hot) {
            stack.getOrCreateNbt().putInt("CustomModelData", 1);
        } else if (stack.hasNbt()) {
            stack.getNbt().remove("CustomModelData");
        }
    }

    public static <T> void remove(ItemStack stack, MEComponent<T> key) {
        CompoundTag tag = stack.getNbt();
        if (tag == null || !tag.contains(ROOT, Tag.COMPOUND_TYPE)) {
            return;
        }
        CompoundTag root = tag.getCompound(ROOT);
        root.remove(key.id().getPath());
        syncCustomModelData(stack, key, null);
        if (root.isEmpty()) {
            tag.remove(ROOT);
        }
        if (tag.isEmpty()) {
            stack.setNbt(null);
        }
    }

    // ------------------------------------------------------------------
    // codec plumbing (package-visible so it can be unit tested without a Bootstrap)
    // ------------------------------------------------------------------

    @Nullable
    static <T> Tag encode(MEComponent<T> key, T value) {
        return key.codec().encodeStart(NbtOps.INSTANCE, value).result().orElse(null);
    }

    @Nullable
    static <T> T decode(MEComponent<T> key, Tag element) {
        return key.codec().parse(NbtOps.INSTANCE, element).result().orElse(null);
    }
}
