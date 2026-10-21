package net.jukoz.me.datageneration.custom;

import java.util.function.Consumer;

import com.google.gson.JsonObject;
import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.resources.ResourceLocation;

/**
 * 1.20.1 stand-in for the donor's 1.21.5 recipe-object exporter.
 *
 * <p>1.21.5 hands a live {@code Recipe<?>} to {@code RecipeExporter}; 1.20.1 datagen only
 * consumes a {@link RecipeJsonProvider} whose {@link #serialize(JsonObject)} fills the raw
 * recipe JSON (the {@code type} field is added by the default {@code toJson()}). The four
 * custom builders hand in their serializer and a JSON writer; the JSON shape must match
 * what the mod's {@code RecipeSerializer#read(ResourceLocation, JsonObject)} implementations
 * parse on the runtime side.
 */
public class CustomRecipeJsonProvider implements RecipeJsonProvider {
    private final ResourceLocation recipeId;
    private final RecipeSerializer<?> serializer;
    private final Consumer<JsonObject> recipeJsonWriter;
    private final Advancement.Builder advancement;
    private final ResourceLocation advancementId;

    public CustomRecipeJsonProvider(ResourceLocation recipeId, RecipeSerializer<?> serializer,
                                    Consumer<JsonObject> recipeJsonWriter,
                                    Advancement.Builder advancement, ResourceLocation advancementId) {
        this.recipeId = recipeId;
        this.serializer = serializer;
        this.recipeJsonWriter = recipeJsonWriter;
        this.advancement = advancement;
        this.advancementId = advancementId;
    }

    @Override
    public void serialize(JsonObject json) {
        this.recipeJsonWriter.accept(json);
    }

    @Override
    public ResourceLocation getRecipeId() {
        return this.recipeId;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public JsonObject toAdvancementJson() {
        return this.advancement.toJson();
    }

    @Override
    public ResourceLocation getAdvancementId() {
        return this.advancementId;
    }
}
