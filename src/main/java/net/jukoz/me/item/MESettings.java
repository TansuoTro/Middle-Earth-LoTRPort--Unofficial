package net.jukoz.me.item;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Rarity;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * {@link Item.Settings} that also carries data components, so the 1.21.8 donor's
 * {@code new MESettings().maxCount(1).component(KEY, VALUE)} chains port verbatim.
 *
 * <p>1.20.1's {@code Item.Settings} has no component support, and a mixin-added method
 * would be invisible to javac, so the whole fluent surface is re-declared here with
 * covariant returns. Every vanilla method just delegates to super.
 *
 * <p>The collected components are handed to {@link MEComponents} when the {@link Item} is
 * constructed (see {@code ItemMixin}).
 */
public class MESettings extends Item.Settings {
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

    // --- covariant re-declarations so the chain keeps returning MESettings ---

    @Override
    public MESettings food(FoodComponent food) {
        super.food(food);
        return this;
    }

    @Override
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
