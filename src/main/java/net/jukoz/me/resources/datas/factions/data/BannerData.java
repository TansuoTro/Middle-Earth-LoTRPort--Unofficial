package net.jukoz.me.resources.datas.factions.data;

import net.jukoz.me.compat.NbtCompat;
import com.google.gson.JsonParser;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;

public class BannerData {
    public static class BannerPatternWithColor {
        public ResourceLocation id;
        public DyeColor color;
        public ResourceKey<BannerPattern> patternRegistryKey;
        public BannerPattern pattern;

        public BannerPatternWithColor(ResourceLocation id, DyeColor dyeColor){
            this.id = id;
            this.color = dyeColor;
            this.patternRegistryKey = null;
            this.pattern = null;
        }

        public BannerPatternWithColor(ResourceKey<BannerPattern> patternRegistryKey, DyeColor dyeColor) {
            this.patternRegistryKey = patternRegistryKey;
            this.id = patternRegistryKey.getValue();
            this.color = dyeColor;
            this.pattern = null;
        }
        public void setPattern(BannerPattern pattern){
            this.pattern = pattern;
        }
        @Override
        public String toString() {
            return id + " in " + color.name();
        }
    }
    private DyeColor baseBannerColor;
    private final List<BannerPatternWithColor> bannerPatternWithColors;
    public static final DyeColor DEFAULT_DYE = DyeColor.PINK;

    public BannerData(DyeColor baseBannerColor, List<BannerPatternWithColor> bannerPatternWithColors){
        this.baseBannerColor = baseBannerColor;

        if (bannerPatternWithColors != null)
            this.bannerPatternWithColors = bannerPatternWithColors;
        else
            this.bannerPatternWithColors = new ArrayList<>();

    }

    public BannerData(Optional<CompoundTag> optionalBannerDataNbt) {
        if(optionalBannerDataNbt.isEmpty()){
            bannerPatternWithColors = null;
            return;
        }
        CompoundTag compound = optionalBannerDataNbt.get();

        baseBannerColor = DyeColor.byName(compound.getString("base_color"), DEFAULT_DYE);

        ListTag patterns = compound.getList("patterns", Tag.COMPOUND_TYPE);
        this.bannerPatternWithColors = new ArrayList<>();

        JsonParser jsonParser = new JsonParser();

        for(Tag element: patterns){
            try{
                CompoundTag elementCompound = NbtCompat.compound(element);
                ResourceLocation id = new ResourceLocation(elementCompound.getString("id"));
                DyeColor color = DyeColor.byName(NbtCompat.getString(elementCompound, "dye_color", DEFAULT_DYE.asString()), DEFAULT_DYE);

                BannerPatternWithColor bannerPatternWithColor = new BannerPatternWithColor(id, color);
                bannerPatternWithColors.add(bannerPatternWithColor);
            } catch (Exception ignored){

            }
        }
    }

    public List<BannerPatternWithColor> getBannerPatternsWithColors(Level world){
        List<BannerPatternWithColor> patterns = new ArrayList<>();
        for(int i = 0; i < bannerPatternWithColors.size(); i++){
            BannerPattern pattern = world.getRegistryManager().getOptional(Registries.BANNER_PATTERN).get().get(bannerPatternWithColors.get(i).id);
            if(pattern == null){
                continue;
            }
            bannerPatternWithColors.get(i).setPattern(pattern);
            patterns.add(bannerPatternWithColors.get(i));
        }
        return patterns;
    }

    public DyeColor getBaseDye() {
        return baseBannerColor;
    }

    public Optional<CompoundTag> getNbt() {
        if(baseBannerColor == null || bannerPatternWithColors == null || bannerPatternWithColors.isEmpty())
            return Optional.empty();

        CompoundTag nbt = new CompoundTag();
        nbt.putString("base_color",  getBaseDye().name().toLowerCase());
        ListTag list = new ListTag();
        for(BannerPatternWithColor pattern : bannerPatternWithColors){
            CompoundTag compound = new CompoundTag();
            compound.putString("id", pattern.id.toString());
            compound.putString("dye_color",  pattern.color.name().toLowerCase());
            list.add(compound);
        }
        nbt.put("patterns", list);

        return Optional.of(nbt);
    }

    /**
     * Not datadriven, only for hardcoded factions
     * @param bannerPatternLookup
     * @return
     */
    public MEBannerPatterns getBannerPatternComponents(HolderGetter<BannerPattern> bannerPatternLookup) {
        MEBannerPatterns.Builder bannerPatternsComponentBuilder = new MEBannerPatterns.Builder();

        bannerPatternsComponentBuilder.add(new MEBannerPatterns.Layer(net.minecraft.core.registries.Registries.BANNER_PATTERN.getEntry(net.minecraft.world.level.block.entity.BannerPatterns.BASE).orElseThrow(), baseBannerColor));
        for(BannerPatternWithColor bannerPatternWithColor :  bannerPatternWithColors){
            bannerPatternsComponentBuilder.add(bannerPatternLookup.getOrThrow(bannerPatternWithColor.patternRegistryKey), bannerPatternWithColor.color);
        }

        return bannerPatternsComponentBuilder.build();
    }

    public ItemStack getBannerItem(HolderLookup.WrapperLookup wrapperLookup, Component text) {
        MEBannerPatterns.Builder builder = new MEBannerPatterns.Builder();
        HolderLookup.Impl<BannerPattern> registry = wrapperLookup.getWrapperOrThrow(Registries.BANNER_PATTERN);
        for (BannerPatternWithColor bannerPatternWithColor : bannerPatternWithColors) {
            ResourceKey<BannerPattern> key = ResourceKey.of(
                    Registries.BANNER_PATTERN,
                    bannerPatternWithColor.id
            );

            Holder<BannerPattern> pattern = registry.getOrThrow(key);

            builder.add(new MEBannerPatterns.Layer(
                    pattern,
                    bannerPatternWithColor.color
            ));
        }

        return formatBanner(new ItemStack(Items.WHITE_BANNER), builder.build(), text);
    }

    public static ItemStack formatBanner(ItemStack itemStack, MEBannerPatterns bannerPatternsComponent, Component translationKey) {
        MEBannerPatterns.set(itemStack, bannerPatternsComponent);
        // itemStack.set(DataComponentTypes.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE); // TODO : Not existing
        itemStack.setCustomName(translationKey);
        return itemStack;
    }
}
