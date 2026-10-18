package net.jukoz.me.recipe;


import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.jukoz.me.MiddleEarth;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializer<T extends Recipe<?>> {

    public static final SpecialRecipeSerializer<BackAttachmentRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT = register("custom_armor_back_attachment", new SpecialRecipeSerializer<>(BackAttachmentRecipe::new));
    public static final SpecialRecipeSerializer<BackAttachmentRemovalRecipe> CUSTOM_ARMOR_BACK_ATTACHMENT_REMOVAL = register("custom_armor_back_attachment_removal", new SpecialRecipeSerializer<>(BackAttachmentRemovalRecipe::new));
    public static final SpecialRecipeSerializer<HelmetAttachmentRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT = register("custom_armor_helmet_attachment", new SpecialRecipeSerializer<>(HelmetAttachmentRecipe::new));
    public static final SpecialRecipeSerializer<HelmetAttachmentRemovalRecipe> CUSTOM_ARMOR_HELMET_ATTACHMENT_REMOVAL = register("custom_armor_helmet_attachment_removal", new SpecialRecipeSerializer<>(HelmetAttachmentRemovalRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorAddonRemovalRecipe> CUSTOM_MOUNT_ARMOR_ADDON_REMOVAL = register("custom_mount_armor_addon_removal", new SpecialRecipeSerializer<>(MountArmorAddonRemovalRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorSideSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_SIDE_SKULL_ADDON = register("custom_mount_armor_side_skull_addon", new SpecialRecipeSerializer<>(MountArmorSideSkullAddonRecipe::new));
    public static final SpecialRecipeSerializer<MountArmorTopSkullAddonRecipe> CUSTOM_MOUNT_ARMOR_TOP_SKULL_ADDON = register("custom_mount_armor_top_skull_addon", new SpecialRecipeSerializer<>(MountArmorTopSkullAddonRecipe::new));

    public static final SpecialRecipeSerializer<CustomItemDecorationRecipe> CUSTOM_ITEM_DECORATION = register("custom_item_decoration", new SpecialRecipeSerializer<>(CustomItemDecorationRecipe::new));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(MiddleEarth.MOD_ID, id), serializer);
    }

    public static void registerRecipeSerializers(){
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Recipe Serializers for " + MiddleEarth.MOD_ID);
    }
}
