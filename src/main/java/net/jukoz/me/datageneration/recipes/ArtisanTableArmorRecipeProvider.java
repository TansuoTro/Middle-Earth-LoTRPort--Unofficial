package net.jukoz.me.datageneration.recipes;

import java.util.Optional;
import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.datageneration.content.tags.ArmorTags;
import net.jukoz.me.datageneration.custom.ArtisanTableRecipeJsonBuilder;
import net.jukoz.me.datageneration.custom.InscriptionRecipeJsonBuilder;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.item.EquipmentItemsME;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.item.ToolItemsME;
import net.jukoz.me.item.WeaponItemsME;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.utils.ItemTagsME;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Ported from the 1.21.8 donor package
 * net.sevenstars.middleearth.datageneration.providers.recipes (RecipeGenerator API rewritten as a plain 1.20.1 FabricRecipeProvider).
 */
public class ArtisanTableArmorRecipeProvider extends FabricRecipeProvider {
    private static final int XP_T2 = 1;
    private static final int XP_T3 = 4;
    private static final int XP_T4 = 9;
    private static final int XP_T5 = 12;
    private static final int XP_STEEL_ITEM = 3;
    private static final int XP_NOBLE_ITEM = 5;

    private static CompoundTag trimNbt(ResourceLocation materialId) {
        return ArtisanTableRecipeJsonBuilder.trimNbt(materialId);
    }

    public ArtisanTableArmorRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "ArtisanTableArmorRecipes";
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ItemStack goldArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        goldArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));

        ItemStack silverArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        silverArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.SILVER.getName())));

        ItemStack steelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        steelArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        steelHelmetPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelChainmail = new ItemStack(ResourceItemsME.MAIL);
        steelChainmail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        steelScaleMail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));

        ItemStack edhelSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        edhelSteelArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        edhelSteelHelmetPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        edhelSteelChainmail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        edhelSteelScaleMail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));

        ItemStack khazadSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        khazadSteelArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        khazadSteelHelmetPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        khazadSteelChainmail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        khazadSteelScaleMail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));

        ItemStack burzumSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        burzumSteelArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        burzumSteelHelmetPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        burzumSteelChainmail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        burzumSteelScaleMail.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));

        ItemStack ironArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        ironArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));
        ItemStack ironHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        ironHelmetPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));
        ItemStack ironChainmail = new ItemStack(ResourceItemsME.MAIL);
        ironChainmail.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));

        ItemStack bronzeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        bronzeArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName())));
        ItemStack crudeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        crudeArmorPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName())));

        

                //region MEN

                //region GONDOR
                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CABASSET_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_TABBARD.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_HAUBERK))
                        .offerTo(exporter);

                //T5 PLATE
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .input(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_SOLDIER_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.GONDORIAN_MAIL_COAT)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_MAIL_COAT))
                        .offerTo(exporter);

                //T5 CAPTAIN
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .offerTo(exporter);

                //T5 KING'S GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CHESTKPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelChainmail)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .componentInput(steelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .offerTo(exporter);

                //T5 CITADEL GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_HELMET)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelChainmail)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .componentInput(steelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(goldArmorPlate.getItem()),
                                conditionsFromItem(goldArmorPlate.getItem()))
                        .offerTo(exporter);

                //T5 FOUNTAIN GUARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.MITHRIL_NUGGET)
                        .input(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET)
                        .input(ResourceItemsME.MITHRIL_NUGGET)
                        .input(ResourceItemsME.SWAN_FEATHER)
                        .input(ResourceItemsME.SWAN_FEATHER)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_CAPTAIN_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_LEGGINGS))
                        .offerTo(exporter);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CAPTAIN_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.IRON_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HERO_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.IRON_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_KINGS_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_CITADEL_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //region ROHAN
                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                        .offerTo(exporter);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.ARMING_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_GAMBESON.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GAMBESON)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.GAMBESON),
                                conditionsFromItem(EquipmentItemsME.GAMBESON))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironArmorPlate)
                        .criterion(hasItem(bronzeArmorPlate.getItem()),
                                conditionsFromItem(bronzeArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .criterion(hasItem(ironArmorPlate.getItem()),
                                conditionsFromItem(ironArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironArmorPlate)
                        .criterion(hasItem(bronzeArmorPlate.getItem()),
                                conditionsFromItem(bronzeArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_MILITIA_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .criterion(hasItem(bronzeArmorPlate.getItem()),
                                conditionsFromItem(bronzeArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_REINFORCED_LEATHER_SCALE_VEST.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_BRACED_MAIL_SHIRT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ///T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ORNAMENTED_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_SCALE_JACKET.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                //T5 EORLING MARSHAL
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                        .offerTo(exporter);

                //T5 HORSE LORD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(goldArmorPlate)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK)
                        .componentInput(goldArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.ROHIRRIC_SCALE_JACKET)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET),
                                conditionsFromItem(EquipmentItemsME.ROHIRRIC_SCALE_JACKET))
                        .offerTo(exporter);
                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_ROYAL_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EORLING_MARSHAL_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.HORSE_LORD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //region DALE

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BLACK_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_BROWN_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_ARMING_COAT_TAN_FUR.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BLACK_FUR.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_BROWN_FUR.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HELMET_TAN_FUR.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(ironChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_BURGONET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                //T4 HEYDAY
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.DALISH_HELMET)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.DALISH_HELMET),
                                conditionsFromItem(EquipmentItemsME.DALISH_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .criterion(hasItem(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DALISH_MAIL_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.DALISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.DALISH_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(steelArmorPlate.getItem()),
                                conditionsFromItem(steelArmorPlate.getItem()))
                        .offerTo(exporter);

                //T5 BARDING
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .input(EquipmentItemsME.DALISH_SCALE_HAUBERK)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.DALISH_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DALISH_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.DALISH_MAIL_COAT)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.DALISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.DALISH_MAIL_COAT))
                        .offerTo(exporter);

                //T5 BARDING SERGEANT
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.BARDING_SOLDIER_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.BARDING_SOLDIER_HELMET),
                                conditionsFromItem(EquipmentItemsME.BARDING_SOLDIER_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BARDING_SOLDIER_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.BARDING_SERGEANT_CHESTPLATE))
                        .offerTo(exporter);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SURCOAT.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.PURPLE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BARDING_SERGEANT_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.ORANGE_DYE)
                        .input(Items.PURPLE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.PURPLE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HEYDAY_CLOAK.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //endregion

                //region ELVES

                //region LOTHLORIEN
                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(bronzeArmorPlate.getItem()),
                                conditionsFromItem(bronzeArmorPlate.getItem()))
                        .offerTo(exporter);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ELVEN_ARMING_COAT)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.ELVEN_ARMING_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_ARMING_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ELVEN_ARMING_SKIRT)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_ARMING_SKIRT),
                                conditionsFromItem(EquipmentItemsME.ELVEN_ARMING_SKIRT))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                        .componentInput(ironChainmail)
                        .input(EquipmentItemsME.LORIEN_DIADEM)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SHORT_MAIL_COIF_DIADEM.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.ELVEN_MAIL_COIF)
                        .componentInput(ironChainmail)
                        .input(EquipmentItemsME.LORIEN_DIADEM)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ELVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                //T4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelHelmetPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.LORIEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                conditionsFromItem(edhelSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                conditionsFromItem(edhelSteelScaleMail.getItem()))
                        .offerTo(exporter);

                //T5 GALADHRIM
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .input(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.LORIEN_SOLDIER_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .input(EquipmentItemsME.LORIEN_SCALE_COAT)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.LORIEN_SCALE_COAT),
                                conditionsFromItem(EquipmentItemsME.LORIEN_SCALE_COAT))
                        .offerTo(exporter);

                //T5 EGLADIL & GALADHRIM LORD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(silverArmorPlate)
                        .componentInput(silverArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .input(ResourceItemsME.SILVER_INGOT)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GALADHRIM_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GALADHRIM_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GALADHRIM_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.GALADHRIM_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GALADHRIM_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GALADHRIM_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GALADHRIM_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GALADHRIM_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.GALADHRIM_LEGGINGS))
                        .offerTo(exporter);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_HOOD.getDefaultStack(), "helmet_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_MARCHWARDEN_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EGLADIL_SENTINEL_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .criterion(hasItem(Items.GOLD_NUGGET),
                                conditionsFromItem(Items.GOLD_NUGGET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_SURCOAT.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GALADHRIM_LORD_CLOAK.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .criterion(hasItem(ResourceItemsME.SILVER_NUGGET),
                                conditionsFromItem(ResourceItemsME.SILVER_NUGGET))
                        .offerTo(exporter);
                //endregion

                //region WOODLAND REALM
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_BRONZED_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_LEATHER_SILVER_ARMING_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_ARMING_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_BRONZE_TRIMMED_RANGER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.BRONZE_NUGGET),
                                conditionsFromItem(ResourceItemsME.BRONZE_NUGGET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SILVER_TRIMMED_RANGER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.SILVER_NUGGET),
                                conditionsFromItem(ResourceItemsME.SILVER_NUGGET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_CAVALRY_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelHelmetPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                conditionsFromItem(edhelSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelHelmetPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(edhelSteelHelmetPlate.getItem()),
                                conditionsFromItem(edhelSteelHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.LORIEN_MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(edhelSteelScaleMail.getItem()),
                                conditionsFromItem(edhelSteelScaleMail.getItem()))
                        .offerTo(exporter);

                //T5 WLR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .input(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .input(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_GILDED_SCALE_SKIRT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SCALE_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_THE_NIGHTSHADE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(edhelSteelArmorPlate)
                        .input(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .componentInput(edhelSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SCALE_SKIRT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_COMMANDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(silverArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SOLDIER_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_COMMANDER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.SILVER_INGOT)
                        .input(ResourceItemsME.SILVER_INGOT)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.WOODLAND_REALM_SOLDIER_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_ROYAL_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOODLAND_REALM_SOLDIER_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARDEN_OF_THE_GLADE_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARRIOR_OF_NIGHTSHADE_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //endregion

                //region DWARVES

                //region EREBOR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_SEGMENTED_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .componentInput(bronzeArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(bronzeArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(bronzeArmorPlate.getItem()),
                                conditionsFromItem(bronzeArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_WANDERER_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_PARTISAN_OUTFIT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.IRON_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LONGBEARD_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRACED_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_NASAL_LEATHER_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GILDED_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(EquipmentItemsME.EREBOR_MAIL_COIF)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PADDED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_MAIL_CHAUSSES.getDefaultStack(), "boots", DispositionType.GOOD, XP_T5)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T5)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_INGOT)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                //t4
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelHelmetPlate)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelChainmail)
                        .componentInput(khazadSteelChainmail)
                        .criterion(hasItem(khazadSteelHelmetPlate.getItem()),
                                conditionsFromItem(khazadSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_GILDED_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T4)
                        .input(EquipmentItemsME.EREBOR_GILDED_MAIL_COIF)
                        .input(EquipmentItemsME.RAVENHILL_HELMET)
                        .criterion(hasItem(EquipmentItemsME.RAVENHILL_HELMET),
                                conditionsFromItem(EquipmentItemsME.RAVENHILL_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                conditionsFromItem(khazadSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_LONG_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelChainmail)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .componentInput(khazadSteelChainmail)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_BRAWLER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.BLUE_DYE)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(khazadSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(khazadSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.BLUE_DYE)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_REINFORCED_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelChainmail)
                        .input(EquipmentItemsME.EREBOR_MAIL_HAUBERK)
                        .componentInput(khazadSteelChainmail)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelChainmail)
                        .componentInput(khazadSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                conditionsFromItem(khazadSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_SCALE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .criterion(hasItem(khazadSteelScaleMail.getItem()),
                                conditionsFromItem(khazadSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_BOOTS.getDefaultStack(), "boots", DispositionType.GOOD, XP_T4)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                conditionsFromItem(khazadSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelScaleMail)
                        .input(EquipmentItemsME.EREBOR_SCALE_HAUBERK)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_SCALE_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.EREBOR_SCALE_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(khazadSteelArmorPlate)
                        .input(EquipmentItemsME.EREBOR_SCALE_LEGGINGS)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .componentInput(khazadSteelScaleMail)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_SCALE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.EREBOR_SCALE_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(goldArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_GATEWARDEN_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .input(EquipmentItemsME.EREBOR_PLATE_HELMET)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(khazadSteelChainmail)
                        .componentInput(khazadSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(bronzeArmorPlate)
                        .input(EquipmentItemsME.EREBOR_PLATE_LEGGINGS)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.EREBOR_PLATE_LEGGINGS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPTAIN_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.GOAT_HORN)
                        .input(EquipmentItemsME.EREBOR_GUARD_HELMET)
                        .input(Items.GOAT_HORN)
                        .criterion(hasItem(EquipmentItemsME.EREBOR_GUARD_HELMET),
                                conditionsFromItem(EquipmentItemsME.EREBOR_GUARD_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_HELMET.getDefaultStack(), "helmet", DispositionType.GOOD, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET),
                                conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.GOOD, XP_T5)
                        .componentInput(goldArmorPlate)
                        .componentInput(goldArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(bronzeArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_LEGGINGS.getDefaultStack(), "leggings", DispositionType.GOOD, XP_T5)
                        .componentInput(khazadSteelArmorPlate)
                        .input(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS)
                        .componentInput(khazadSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS),
                                conditionsFromItem(EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS))
                        .offerTo(exporter);
                //capes
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.EREBOR_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLUE_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RAVENHILL_SENTINEL_CAPE.getDefaultStack(), "back_attachment", DispositionType.GOOD)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GRAY_DYE)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.YELLOW_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                //endregion

                //endregion

                //region ORCS

                //region MORDOR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                        .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_KETTLE_HAT_WITH_COIF.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.ORCISH_MAIL_COIF)
                        .input(EquipmentItemsME.MORDOR_KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_LEATHER_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CREST_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CREST_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SALLET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GORGET_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.MORDOR_LEATHER_CUIRASS)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_LEATHER_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_PLATE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_GREAT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelHelmetPlate)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_MORDOR_SNOUT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelHelmetPlate)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_DEGRADED_GONDORIAN_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.RED_DYE)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.GONDORIAN_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_PAINTED_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.MORDOR_CUIRASS)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.MORDOR_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_SCALE_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                conditionsFromItem(burzumSteelScaleMail.getItem()))
                        .offerTo(exporter);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.MORDOR_CHESTPLATE)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MORDOR_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.MORDOR_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.SKELETON_SKULL)
                        .input(EquipmentItemsME.BLACK_URUK_PLATE_HELMET)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.RED_DYE)
                        .input(Items.RED_DYE)
                        .criterion(hasItem(EquipmentItemsME.BLACK_URUK_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.BLACK_URUK_PLATE_HELMET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_URUK_COMMANDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.RED_DYE)
                        .input(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.BLACK_URUK_PLATE_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MAIL_SKIRT),
                                conditionsFromItem(EquipmentItemsME.MAIL_SKIRT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORDOR_BLACK_NUMENOREAN_CAPE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //region DOL GULDUR
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                            conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                    .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_JAILER_COLLAR.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .input(ResourceItemsME.CRUDE_INGOT)
                    .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                            conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                    .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_EXECUTIONER_HOOD.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .input(Items.LEATHER)
                    .criterion(hasItem(Items.LEATHER),
                            conditionsFromItem(Items.LEATHER))
                    .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_EXECUTIONER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_EXECUTIONER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_PADDED_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_HUNTER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_RAIDER_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                conditionsFromItem(burzumSteelScaleMail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_MARAUDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.ORCISH_SCALE_VEST)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_SCALE_VEST),
                                conditionsFromItem(EquipmentItemsME.ORCISH_SCALE_VEST))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_MARAUDER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);
                // Weathered
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.ORCISH_SCALE_VEST)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_SCALE_VEST),
                                conditionsFromItem(EquipmentItemsME.ORCISH_SCALE_VEST))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_MARAUDER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_STALKER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.DOL_GULDUR_RAIDER_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DOL_GULDUR_STALKER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);
                // Weathered
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.WEATHERED_DOL_GULDUR_RAIDER_CHESTPLATE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WEATHERED_DOL_GULDUR_STALKER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_REAVER_SHOULDER_CAPE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);
                //endregion

                //region ISENGARD
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_SCOUT_CAP.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LEATHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_LEATHER_CHESTPLATE))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LEATHER_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_LIGHT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_LIGHT_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.URUK_HAI_LIGHT_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_LIGHT_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_LIGHT_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_REINFORCED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_REINFORCED_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_REINFORCED_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_MAIL_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_SOLDIER_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.URUK_HAI_CUIRASS)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_CUIRASS),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_CUIRASS))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PLATE_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.URUK_HAI_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_PLATE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_SAPPER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_SAPPER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_SAPPER_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_SAPPER_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_BERSERKER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_BERSERKER_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.URUK_HAI_PLATE_HELMET)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_PLATE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.URUK_HAI_PAINTED_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET),
                                conditionsFromItem(EquipmentItemsME.URUK_HAI_COMMANDER_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.MAIL_HAUBERK)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(EquipmentItemsME.MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(steelArmorPlate)
                        .input(EquipmentItemsME.MAIL_SKIRT)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MAIL_SKIRT),
                                conditionsFromItem(EquipmentItemsME.MAIL_SKIRT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_COMMANDER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.FEATHER)
                        .input(EquipmentItemsME.ORTHANC_GUARD_HELMET)
                        .input(Items.FEATHER)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelArmorPlate)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORTHANC_GUARD_CAPE.getDefaultStack(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LIGHT_GRAY_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                //endregion

                //region MISTIES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_BONE_PAULDRON.getDefaultStack(), "chestplate", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SEEKER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .componentInput(crudeArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LEATHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_LACED_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SKULLCAP_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COIF.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SOLDIER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_SCREECHER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_SCALE_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT),
                                conditionsFromItem(EquipmentItemsME.GUNDABAD_LEATHER_SCALE_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST),
                                conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_PLATE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST),
                                conditionsFromItem(EquipmentItemsME.GUNDABAD_REINFORCED_LEATHER_VEST))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GUNDABAD_HOBGOBLIN_MAIL_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.GUNDABAD_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);
                //endregion

                //region MORIA
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_CUIRASS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(crudeArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_BELLY_PLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(crudeArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CLOTH_COAT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.CRUDE_NUGGET),
                                conditionsFromItem(ResourceItemsME.CRUDE_NUGGET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SNAGA_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_NUGGET),
                                conditionsFromItem(ResourceItemsME.CRUDE_NUGGET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CRUDE_BOOTS.getDefaultStack(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHARGER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_RUINED_DWARVEN_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_RUINED_DWARVEN_HAUBERK.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(EquipmentItemsME.ORCISH_MAIL_HAUBERK)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_BITER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelHelmetPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE)
                        .componentInput(burzumSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE),
                                conditionsFromItem(EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE))
                        .offerTo(exporter);

                //T5
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT),
                                conditionsFromItem(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_SCREECHER_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT),
                                conditionsFromItem(EquipmentItemsME.MORIA_GOBLIN_REINFORCED_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_LEGGINGS.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T5)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.ORCISH_MAIL_COAT)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(EquipmentItemsME.ORCISH_MAIL_COAT),
                                conditionsFromItem(EquipmentItemsME.ORCISH_MAIL_COAT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MORIA_GOBLIN_CHIEF_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T5)
                        .input(Items.GOAT_HORN)
                        .input(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET)
                        .input(Items.GOAT_HORN)
                        .criterion(hasItem(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET),
                                conditionsFromItem(EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_KING_CROWN.getDefaultStack(), "helmet", DispositionType.EVIL)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .input(ResourceItemsME.DIRTY_BONE)
                        .criterion(hasItem(ResourceItemsME.DIRTY_BONE),
                                conditionsFromItem(ResourceItemsME.DIRTY_BONE))
                        .offerTo(exporter);
                //endregion

                //region GOBLIN TOWN
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CAP.getDefaultStack(), "helmet", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_MANDIBLE_CAP.getDefaultStack(), "helmet", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CAP)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_CAP),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_CAP))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CROSSBONES_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CAP)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_CAP),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_CAP))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_RIBCAGE.getDefaultStack(), "chestplate", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .criterion(hasItem(Items.BONE),
                                conditionsFromItem(Items.BONE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_STRAP.getDefaultStack(), "chestplate", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(Items.BONE),
                                conditionsFromItem(Items.BONE))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_LOINCLOTH.getDefaultStack(), "leggings", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SANDALS.getDefaultStack(), "boots", DispositionType.EVIL)
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(crudeArmorPlate)
                        .componentInput(crudeArmorPlate)
                        .componentInput(crudeArmorPlate)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(crudeArmorPlate.getItem()),
                                conditionsFromItem(crudeArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_MANDIBLE_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(crudeArmorPlate)
                        .componentInput(crudeArmorPlate)
                        .componentInput(crudeArmorPlate)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(crudeArmorPlate.getItem()),
                                conditionsFromItem(crudeArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_HEAVY_NASAL_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SKULKER_GUARD_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_SKULL_CAP.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(crudeArmorPlate)
                        .input(Items.SKELETON_SKULL)
                        .componentInput(crudeArmorPlate)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .criterion(hasItem(Items.SKELETON_SKULL),
                                conditionsFromItem(Items.SKELETON_SKULL))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET.getDefaultStack(), "helmet", DispositionType.EVIL, XP_T3)
                        .input(Items.CANDLE)
                        .input(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET)
                        .input(Items.CANDLE)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_NASAL_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BONE_SCALE_COAT.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_NUGGET),
                                conditionsFromItem(ResourceItemsME.CRUDE_NUGGET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CRUDE_SCALE_CHESTPLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(crudeArmorPlate)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ResourceItemsME.CRUDE_NUGGET),
                                conditionsFromItem(ResourceItemsME.CRUDE_NUGGET))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T2)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.CRUDE_NUGGET),
                                conditionsFromItem(ResourceItemsME.CRUDE_NUGGET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_LEATHER_SKIRT.getDefaultStack(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);


                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_BELLY_PLATE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.BONE)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .componentInput(crudeArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(crudeArmorPlate.getItem()),
                                conditionsFromItem(crudeArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GOBLIN_TOWN_REINFORCED_CARAPACE.getDefaultStack(), "chestplate", DispositionType.EVIL, XP_T3)
                        .input(Items.LEATHER)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.BONE)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS),
                                conditionsFromItem(EquipmentItemsME.GOBLIN_TOWN_CARAPACE_HARNESS))
                        .offerTo(exporter);
                //endregion

                for(ArmorTags.RecipeItem recipe : ArmorTags.heavyHelmets) {
                    ItemStack helmetIngredient;
                    ItemStack lowerIngredient;
                    ItemStack mainIngredient;
                    switch(recipe.metalType()) {
                        case EDHEL_STEEL -> {
                            lowerIngredient = edhelSteelArmorPlate;
                            mainIngredient = edhelSteelArmorPlate;
                            helmetIngredient = edhelSteelHelmetPlate;
                        }
                        case KHAZAD_STEEL -> {
                            lowerIngredient = khazadSteelArmorPlate;
                            mainIngredient = khazadSteelArmorPlate;
                            helmetIngredient = khazadSteelHelmetPlate;
                        }
                        case BURZUM_STEEL -> {
                            lowerIngredient = burzumSteelArmorPlate;
                            mainIngredient = burzumSteelArmorPlate;
                            helmetIngredient = burzumSteelHelmetPlate;
                        }
                        default -> {
                            mainIngredient = steelArmorPlate;
                            lowerIngredient = steelArmorPlate;
                            helmetIngredient = steelHelmetPlate;
                        }
                    }

                    if(recipe.noble()) {
                        if(recipe.disposition().equals(DispositionType.GOOD)) {
                            lowerIngredient = Items.GOLD_INGOT.getDefaultStack();
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "helmet", recipe.disposition(), XP_T5)
                                    .componentInput(mainIngredient)
                                    .componentInput(helmetIngredient)
                                    .componentInput(mainIngredient)
                                    .input(lowerIngredient.getItem())
                                    .input(lowerIngredient.getItem())
                                    .criterion(hasItem(lowerIngredient.getItem()),
                                            conditionsFromItem(lowerIngredient.getItem()))
                                    .offerTo(exporter);
                        } else {
                            lowerIngredient = silverArmorPlate;
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "helmet", recipe.disposition(), XP_T5)
                                    .componentInput(mainIngredient)
                                    .componentInput(helmetIngredient)
                                    .componentInput(mainIngredient)
                                    .componentInput(lowerIngredient)
                                    .componentInput(lowerIngredient)
                                    .criterion(hasItem(lowerIngredient.getItem()),
                                            conditionsFromItem(lowerIngredient.getItem()))
                                    .offerTo(exporter);
                        }
                    } else {
                        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "helmet", recipe.disposition(), XP_T5)
                                .componentInput(mainIngredient)
                                .componentInput(helmetIngredient)
                                .componentInput(mainIngredient)
                                .componentInput(lowerIngredient)
                                .componentInput(lowerIngredient)
                                .criterion(hasItem(lowerIngredient.getItem()),
                                        conditionsFromItem(lowerIngredient.getItem()))
                                .offerTo(exporter);
                    }
                }

                for(ArmorTags.RecipeItem recipe : ArmorTags.heavyBoots) {
                    ItemStack upperIngredient;
                    ItemStack mainIngredient = switch(recipe.metalType()) {
                        case EDHEL_STEEL -> edhelSteelArmorPlate;
                        case KHAZAD_STEEL -> khazadSteelArmorPlate;
                        case BURZUM_STEEL -> burzumSteelArmorPlate;
                        default -> steelArmorPlate;
                    };
                    if(recipe.noble()) {
                        if(recipe.disposition().equals(DispositionType.GOOD)) {
                            upperIngredient = Items.GOLD_INGOT.getDefaultStack();
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "boots", recipe.disposition(), XP_T5)
                                    .input(upperIngredient.getItem())
                                    .input(upperIngredient.getItem())
                                    .componentInput(mainIngredient)
                                    .componentInput(mainIngredient)
                                    .criterion(hasItem(upperIngredient.getItem()),
                                            conditionsFromItem(upperIngredient.getItem()))
                                    .offerTo(exporter);
                        } else {
                            upperIngredient = silverArmorPlate;
                            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "boots", recipe.disposition(), XP_T5)
                                    .componentInput(upperIngredient)
                                    .componentInput(upperIngredient)
                                    .componentInput(mainIngredient)
                                    .componentInput(mainIngredient)
                                    .criterion(hasItem(upperIngredient.getItem()),
                                            conditionsFromItem(upperIngredient.getItem()))
                                    .offerTo(exporter);
                        }
                    } else {
                        upperIngredient = mainIngredient;
                        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, recipe.output().getDefaultStack(), "boots", recipe.disposition(), XP_T5)
                                .componentInput(upperIngredient)
                                .componentInput(upperIngredient)
                                .componentInput(mainIngredient)
                                .componentInput(mainIngredient)
                                .criterion(hasItem(upperIngredient.getItem()),
                                        conditionsFromItem(upperIngredient.getItem()))
                                .offerTo(exporter);
                    }
                }
                //endregion
            
    }


}
