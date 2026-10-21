package net.jukoz.me.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.Rarity;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.Component;

/**
 * {@link Item.Properties} that also carries data components, so the 1.21.8 donor's
 * {@code new MESettings().maxCount(1).component(KEY, VALUE)} chains port verbatim.
 *
 * <p>1.20.1's {@code Item.Properties} has no component support, and a mixin-added method
 * would be invisible to javac, so the whole fluent surface is re-declared here with
 * covariant returns. Every vanilla method just delegates to super.
 *
 * <p>The collected components are handed to {@link MEComponents} when the {@link Item} is
 * constructed (see {@code ItemMixin}).
 */
public class MESettings extends Item.Properties {
    private final Map<MEComponent<?>, Object> components = new IdentityHashMap<>();

    public MESettings() {
        super();
    }

    /** Records a component that every stack of the resulting item will report. */
    public <T> MESettings component(MEComponent<T> key, T value) {
        this.components.put(key, value);
        return this;
    }

    public Map<MEComponent<?>, Object> components() {
        return Collections.unmodifiableMap(this.components);
    }

    private boolean unbreakable;

    private int enchantability = -1;

    /**
     * 1.21's {@code enchantable(int)} settings method. 1.20.1 reports enchantability from
     * the item, not the settings, so the value is captured here and handed to
     * {@link ItemExtras} by {@code ItemMixin}, which serves it from
     * {@code Item#getEnchantability()}.
     */
    public MESettings enchantable(int value) {
        this.enchantability = value;
        return this;
    }

    public int enchantability() {
        return this.enchantability;
    }

    /**
     * 1.21's {@code UNBREAKABLE} component. 1.20.1 has no settings-level equivalent, so the
     * flag is carried to {@link UnbreakableItems} once the item is constructed.
     */
    public MESettings unbreakable() {
        this.unbreakable = true;
        return this;
    }

    public boolean isUnbreakable() {
        return this.unbreakable;
    }

    private java.util.List<net.minecraft.network.chat.Component> lore;
    private String bannerPatternTag;

    /** 1.21's LORE component: lines every stack of the item shows in its tooltip. */
    public MESettings lore(java.util.List<net.minecraft.network.chat.Component> lines) {
        this.lore = lines;
        return this;
    }

    public java.util.List<net.minecraft.network.chat.Component> lore() {
        return this.lore;
    }

    /** 1.21's PROVIDES_BANNER_PATTERNS component; the tag id is kept for the loom integration. */
    public MESettings providesBannerPatterns(net.minecraft.tags.TagKey<?> tag) {
        this.bannerPatternTag = tag.id().toString();
        return this;
    }

    public String bannerPatternTag() {
        return this.bannerPatternTag;
    }

    // --- covariant re-declarations so the chain keeps returning MESettings ---

    @Override
    public MESettings food(FoodProperties food) {
        super.food(food);
        return this;
    }

    /** 1.21's {@code horseArmor(ArmorMaterial)}: 1.20.1 sets horse armour on the item class. */
    public MESettings horseArmor(net.minecraft.world.item.ArmorMaterial material) {
        return this;
    }

    public MESettings maxCount(int maxCount) {
        super.maxCount(maxCount);
        return this;
    }

    @Override
    public MESettings maxDamageIfAbsent(int maxDamage) {
        super.maxDamageIfAbsent(maxDamage);
        return this;
    }

    @Override
    public MESettings maxDamage(int maxDamage) {
        super.maxDamage(maxDamage);
        return this;
    }

    @Override
    public MESettings recipeRemainder(Item recipeRemainder) {
        super.recipeRemainder(recipeRemainder);
        return this;
    }

    @Override
    public MESettings rarity(Rarity rarity) {
        super.rarity(rarity);
        return this;
    }

    @Override
    public MESettings fireproof() {
        super.fireproof();
        return this;
    }

    @Override
    public MESettings requires(FeatureFlag... requiredFeatures) {
        super.requires(requiredFeatures);
        return this;
    }
}
