package net.jukoz.me.compat;

import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.DyeColor;

import java.util.ArrayList;
import java.util.List;

/**
 * NBT-backed replacement for 1.21's {@code BannerPatternsComponent}.
 *
 * <p>1.21 keeps the applied banner layers as a stack component; 1.20.1 stores the same data in
 * the stack's {@code BlockEntityTag.Patterns} list, where every entry holds the pattern's
 * short id ({@code Pattern}) and a dye index ({@code Color}). Call sites keep the donor shape
 * through {@link #get}/{@link #set} and the nested {@link Builder}.
 */
public record MEBannerPatterns(List<Layer> layers) {

    public static final MEBannerPatterns DEFAULT = new MEBannerPatterns(List.of());

    /** One applied banner layer: the pattern and the dye it was applied with. */
    public record Layer(BannerPattern pattern, DyeColor color) {
    }

    /** Donor-style builder mirroring {@code MEBannerPatterns.Builder}. */
    public static class Builder {
        private final List<Layer> layers = new ArrayList<>();

        public Builder add(BannerPattern pattern, DyeColor color) {
            layers.add(new Layer(pattern, color));
            return this;
        }

        public Builder add(RegistryEntry<BannerPattern> pattern, DyeColor color) {
            return add(pattern.value(), color);
        }

        public MEBannerPatterns build() {
            return new MEBannerPatterns(List.copyOf(layers));
        }
    }

    /** The layers currently applied to {@code stack}, never null. */
    public static MEBannerPatterns get(ItemStack stack) {
        List<Layer> layers = new ArrayList<>();
        NbtCompound blockEntityTag = stack.getSubNbt("BlockEntityTag");
        if (blockEntityTag != null) {
            NbtList patterns = blockEntityTag.getList(BannerBlockEntity.PATTERNS_KEY, NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < patterns.size(); i++) {
                NbtCompound entry = patterns.getCompound(i);
                BannerPattern pattern = BannerPattern.byId(entry.getString(BannerBlockEntity.PATTERN_KEY));
                if (pattern == null) {
                    continue;
                }
                layers.add(new Layer(pattern, DyeColor.byId(entry.getInt(BannerBlockEntity.COLOR_KEY))));
            }
        }
        return new MEBannerPatterns(List.copyOf(layers));
    }

    /** Replaces the applied layers; an empty list removes the {@code Patterns} key. */
    public static void set(ItemStack stack, MEBannerPatterns patterns) {
        NbtCompound blockEntityTag = stack.getOrCreateSubNbt("BlockEntityTag");
        if (patterns.layers().isEmpty()) {
            blockEntityTag.remove(BannerBlockEntity.PATTERNS_KEY);
        } else {
            NbtList list = new NbtList();
            for (Layer layer : patterns.layers()) {
                NbtCompound entry = new NbtCompound();
                entry.putString(BannerBlockEntity.PATTERN_KEY, layer.pattern().getId());
                entry.putInt(BannerBlockEntity.COLOR_KEY, layer.color().getId());
                list.add(entry);
            }
            blockEntityTag.put(BannerBlockEntity.PATTERNS_KEY, list);
        }
        if (blockEntityTag.isEmpty()) {
            stack.removeSubNbt("BlockEntityTag");
        }
    }

    /** The base colour of a banner stack, or {@link DyeColor#WHITE} for anything else. */
    public static DyeColor getBaseColor(ItemStack stack) {
        return stack.getItem() instanceof BannerItem banner ? banner.getColor() : DyeColor.WHITE;
    }
}
