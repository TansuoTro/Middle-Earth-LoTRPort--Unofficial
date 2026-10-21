package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Forge compat shim for Fabric's {@code FabricRecipeProvider}. Fabric's variant wraps a
 * {@link Consumer} based {@code generate} entry point and adds the {@code has} /
 * {@code conditionsFromItem} helpers; vanilla 1.20.1 uses {@code build} plus
 * {@link RecipeProvider#has(ItemLike)}.
 */
public abstract class FabricRecipeProvider extends RecipeProvider {
    private final List<Consumer<FinishedRecipe>> exporters = new ArrayList<>();

    public FabricRecipeProvider(PackOutput output) {
        super(output);
    }

    public FabricRecipeProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output) {
        this(output.getOutput());
    }

    @Override
    protected final void build(Consumer<FinishedRecipe> consumer) {
        exporters.clear();
        exporters.add(consumer);
        generate(consumer);
    }

    /** Fabric's entry point; ported providers override this. */
    protected void generate(Consumer<FinishedRecipe> exporter) {
    }

    @Override
    public String getName() {
        return "Recipes";
    }

    /** Fabric spelling of the vanilla {@code has} helper. */
    public static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance hasItem(ItemLike item) {
        return has(item);
    }

    public static net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance hasItem(ItemLike... items) {
        return has(items);
    }
    /** Vanilla 1.20.1 helper, exposed under Fabric's name. */
    public static InventoryChangeTrigger.TriggerInstance has(ItemLike item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    public static InventoryChangeTrigger.TriggerInstance has(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    /** Fabric's helper that also attaches the "has the ingredient" advancement. */
    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike item) {
        return has(item);
    }

    public static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike... items) {
        return has(items);
    }

    public static ResourceLocation id(ResourceLocation parent, String suffix) {
        return new ResourceLocation(parent.getNamespace(), parent.getPath() + suffix);
    }
}
