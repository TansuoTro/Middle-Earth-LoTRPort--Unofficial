package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Consumer;

public class VariantsModelProvider implements ExtraModelProvider {

    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<ResourceLocation> out) {
        for(Item item : SimpleBigItemModel.items) {
            out.accept(getInventoryModelIdentifierVariant(item));
        }
    }

    public static ModelResourceLocation getInventoryModelIdentifierVariant(Item item) {
        return new ModelResourceLocation(
                MiddleEarth.MOD_ID, VariantsModelProvider.getKey(item.getTranslationKey()) + "_inventory", "inventory");
    }

    public static String getKey(String path) {
        return path.substring(path.lastIndexOf('.') + 1).trim();
    }
}
