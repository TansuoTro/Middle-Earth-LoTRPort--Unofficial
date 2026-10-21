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
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Ported from the 1.21.8 donor package
 * net.sevenstars.middleearth.datageneration.providers.recipes (RecipeGenerator API rewritten as a plain 1.20.1 FabricRecipeProvider).
 */
public class ArtisanTableGenericArmorRecipeProvider extends FabricRecipeProvider {
    private static final int XP_T2 = 1;
    private static final int XP_T3 = 4;
    private static final int XP_T4 = 9;

    private static CompoundTag trimNbt(ResourceLocation materialId) {
        return ArtisanTableRecipeJsonBuilder.trimNbt(materialId);
    }

    public ArtisanTableGenericArmorRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "ArtisanTableGenericArmorRecipes";
    }

    @Override
    public void generate(Consumer<FinishedRecipe> exporter) {

        ItemStack goldArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        goldArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));

        ItemStack silverArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        silverArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.SILVER.getName())));

        ItemStack bronzeChainmail = new ItemStack(ResourceItemsME.MAIL);
        bronzeChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName())));
        ItemStack crudeChainmail = new ItemStack(ResourceItemsME.MAIL);
        crudeChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName())));

        ItemStack steelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        steelArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        steelHelmetPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelChainmail = new ItemStack(ResourceItemsME.MAIL);
        steelChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));
        ItemStack steelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        steelScaleMail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));

        ItemStack edhelSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        edhelSteelArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        edhelSteelHelmetPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        edhelSteelChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));
        ItemStack edhelSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        edhelSteelScaleMail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));

        ItemStack khazadSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        khazadSteelArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        khazadSteelHelmetPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        khazadSteelChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));
        ItemStack khazadSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        khazadSteelScaleMail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));

        ItemStack burzumSteelArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        burzumSteelArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        burzumSteelHelmetPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelChainmail = new ItemStack(ResourceItemsME.MAIL);
        burzumSteelChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));
        ItemStack burzumSteelScaleMail = new ItemStack(ResourceItemsME.SCALE_MAIL);
        burzumSteelScaleMail.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));

        ItemStack ironArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        ironArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));
        ItemStack ironHelmetPlate = new ItemStack(ResourceItemsME.HELMET_PLATE);
        ironHelmetPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));
        ItemStack ironChainmail = new ItemStack(ResourceItemsME.MAIL);
        ironChainmail.addTagElement("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));

        ItemStack bronzeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        bronzeArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName())));
        ItemStack crudeArmorPlate = new ItemStack(ResourceItemsME.ARMOR_PLATE);
        crudeArmorPlate.addTagElement("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName())));

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SHIRRIFF_HAT.getDefaultInstance(), "hat", DispositionType.GOOD)
                        .input(Items.STRING)
                        .input(Items.LEATHER)
                        .input(Items.FEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                //region GENERIC

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BAGGY_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.CHAPERON.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GLASSES.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.GLASS)
                        .input(Items.GLASS)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MONOCLE.getDefaultInstance(), "hat", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.GLASS)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.BRONZE_INGOT),
                                conditionsFromItem(ResourceItemsME.BRONZE_INGOT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.STRAW_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STRAW)
                        .input(Items.STRING)
                        .input(ResourceItemsME.STRAW)
                        .input(ResourceItemsME.STRAW)
                        .criterion(hasItem(ResourceItemsME.STRAW),
                                conditionsFromItem(ResourceItemsME.STRAW))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WOVEN_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(Items.WHITE_WOOL)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .criterion(hasItem(Items.WHITE_WOOL),
                                conditionsFromItem(Items.WHITE_WOOL))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BYCOCKET.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .criterion(hasItem(Items.WHITE_WOOL),
                                conditionsFromItem(Items.WHITE_WOOL))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BEEKEEPER_MASK.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.STRAW)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_HAT.getDefaultInstance(), "hat", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SHOES.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WORK_SHOES.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SKULLCAP.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_CAP.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON_COWL.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT.getDefaultInstance(), "helmet", DispositionType.NEUTRAL)
                        .input(Items.IRON_NUGGET)
                        .componentInput(ironArmorPlate)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(ironArmorPlate.getItem()),
                                conditionsFromItem(ironArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GAMBESON.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACKSMITH_APRON.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(ResourceItemsME.TIN_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.TIN_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_VEST.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.STURDY_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.TRAVELLING_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.HIGH_CUT_BOOTS.getDefaultInstance(), "boots", DispositionType.NEUTRAL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.COOKING_POT_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(Items.IRON_INGOT)
                        .componentInput(ironHelmetPlate)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_NUGGET)
                        .input(Items.IRON_NUGGET)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.CLOSED_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(EquipmentItemsME.CLOSED_MAIL_COIF)
                        .input(EquipmentItemsME.KETTLE_HAT)
                        .criterion(hasItem(EquipmentItemsME.CLOSED_MAIL_COIF),
                                conditionsFromItem(EquipmentItemsME.CLOSED_MAIL_COIF))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.OPEN_FACE_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WINGED_HELMET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T3)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("feathers")))
                        .input(EquipmentItemsME.OPEN_FACE_HELMET)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("feathers")))
                        .criterion(hasItem(EquipmentItemsME.OPEN_FACE_HELMET),
                                conditionsFromItem(EquipmentItemsME.OPEN_FACE_HELMET))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.NEUTRAL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.NEUTRAL, XP_T3)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SALLET.getDefaultInstance(), "helmet", DispositionType.NEUTRAL, XP_T4)
                        .componentInput(steelScaleMail)
                        .componentInput(steelHelmetPlate)
                        .componentInput(steelScaleMail)
                        .componentInput(steelChainmail)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelHelmetPlate.getItem()),
                                conditionsFromItem(steelHelmetPlate.getItem()))
                        .offerTo(exporter);

                //HOODS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.TALL_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_HOOD.getDefaultInstance(), "helmet_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(EquipmentItemsME.HOOD)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(EquipmentItemsME.HOOD),
                                conditionsFromItem(EquipmentItemsME.HOOD))
                        .offerTo(exporter);

                //CAPES
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
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
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_LEFT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SHOULDER_CAPE_RIGHT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.SURCOAT.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.STRING)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WANDERER_ROBES.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LEAF_LITTER_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation("leaves")))
                        .criterion("has_leaves", conditionsFromTag(TagKey.create(Registries.ITEM, new ResourceLocation("leaves"))))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MIRK_LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .input(WoodBlockSets.MIRKWOOD_SET.leaves)
                        .criterion(hasItem(WoodBlockSets.MIRKWOOD_SET.leaves),
                                conditionsFromItem(WoodBlockSets.MIRKWOOD_SET.leaves))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.MIRK_BARK_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(Items.STRING)
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .input(WoodBlockSets.MIRKWOOD_SET.logBlocks.log())
                        .criterion(hasItem(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()),
                                conditionsFromItem(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.AUTUMN_LEAF_CAPE.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .input(TagKey.create(Registries.ITEM, MiddleEarth.of("autumn_leaves")))
                        .criterion(hasItem(Items.STRING),
                                conditionsFromItem(Items.STRING))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BLACK_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.BROWN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.GRAY_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.TAN_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR_CLOAK.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.WHITE_FUR)
                        .input(Items.STRING)
                        .input(Items.STRING)
                        .input(EquipmentItemsME.CLOAK)
                        .input(Items.STRING)
                        .criterion(hasItem(EquipmentItemsME.CLOAK),
                                conditionsFromItem(EquipmentItemsME.CLOAK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BLACK_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROWN_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GRAY_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.TAN_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WHITE_FUR.getDefaultInstance(), "back_attachment", DispositionType.NEUTRAL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(ResourceItemsME.FUR)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);
                //endregion

                //region ELVES

                //T1
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_COAT.getDefaultInstance(), "chestplate", DispositionType.GOOD)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_OPEN_ARMING_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD)
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

                //T2
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T2)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T2)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .componentInput(bronzeChainmail)
                        .criterion(hasItem(bronzeChainmail.getItem()),
                                conditionsFromItem(bronzeChainmail.getItem()))
                        .offerTo(exporter);

                //T3
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_MAIL_SKIRT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_PADDED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BRONZED_ELVEN_GORGET_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_PADDED_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ELVEN_SILVER_GORGET_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(Items.LEATHER)
                        .input(EquipmentItemsME.BRONZED_ELVEN_MAIL_HAUBERK)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                //endregion

                //region DWARVES

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_HELMET.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T2)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.CANDLE)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_GAMBESON.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T2)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(Items.WHITE_WOOL)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MINER_GAMBESON.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T3)
                        .input(EquipmentItemsME.DWARVEN_MAIL_HAUBERK)
                        .input(EquipmentItemsME.DWARVEN_GAMBESON)
                        .criterion(hasItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK),
                                conditionsFromItem(EquipmentItemsME.DWARVEN_MAIL_HAUBERK))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_MAIL_CHAUSSES.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T3)
                        .input(Items.IRON_INGOT)
                        .input(Items.IRON_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_SCALE_COAT.getDefaultInstance(), "leggings", DispositionType.GOOD, XP_T4)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DWARVEN_REINFORCED_BOOTS.getDefaultInstance(), "boots", DispositionType.GOOD, XP_T4)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.IRON_INGOT),
                                conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);
                //endregion

                //region ORCS
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_STRAP.getDefaultInstance(), "chestplate", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SANDALS.getDefaultInstance(), "boots", DispositionType.EVIL)
                        .input(Items.STICK)
                        .input(Items.STICK)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T2)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T2)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LEG_BRACER.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.RUSTED_ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T2)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .componentInput(crudeChainmail)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(crudeChainmail.getItem()),
                                conditionsFromItem(crudeChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_BOOTS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T2)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_HELMET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironHelmetPlate)
                        .componentInput(ironArmorPlate)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironHelmetPlate.getItem()),
                                conditionsFromItem(ironHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COIF.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_HAUBERK.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_SHIRT.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_MAIL_COAT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_STRIP_LEATHER_SKIRT.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T3)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SALLET.getDefaultInstance(), "helmet", DispositionType.EVIL, XP_T4)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelHelmetPlate)
                        .input(ResourceItemsME.BURZUM_STEEL_INGOT)
                        .componentInput(burzumSteelChainmail)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(burzumSteelHelmetPlate.getItem()),
                                conditionsFromItem(burzumSteelHelmetPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BELLY_PLATE_CHESTPLATE.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelScaleMail)
                        .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                conditionsFromItem(burzumSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SCALE_VEST.getDefaultInstance(), "chestplate", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .componentInput(burzumSteelScaleMail)
                        .criterion(hasItem(burzumSteelScaleMail.getItem()),
                                conditionsFromItem(burzumSteelScaleMail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_REINFORCED_LEG_BRACER.getDefaultInstance(), "leggings", DispositionType.EVIL, XP_T4)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BRACED_SANDALS.getDefaultInstance(), "boots", DispositionType.EVIL, XP_T4)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate).input(Items.LEATHER)
                        .input(Items.LEATHER)
                        .input(Items.LEATHER)

                        .criterion(hasItem(burzumSteelArmorPlate.getItem()),
                                conditionsFromItem(burzumSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_LONG_CAPE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_SHOULDERS.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.RED_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .criterion(hasItem(ResourceItemsME.FABRIC),
                                conditionsFromItem(ResourceItemsME.FABRIC))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BLACK_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_BROWN_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_GRAY_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_TAN_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ORCISH_WHITE_FUR_SURCOAT_WITH_BONE.getDefaultInstance(), "back_attachment", DispositionType.EVIL)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(ResourceItemsME.FUR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(ResourceItemsME.FUR)
                        .input(Items.STRING)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.STRING)
                        .criterion(hasItem(ResourceItemsME.FUR),
                                conditionsFromItem(ResourceItemsME.FUR))
                        .offerTo(exporter);
                //endregion

                //region MOUNT ARMORS

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GONDORIAN_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelArmorPlate.getItem()),
                                conditionsFromItem(steelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.ROHIRRIC_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelArmorPlate.getItem()),
                                conditionsFromItem(steelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.DALISH_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(steelChainmail)
                        .componentInput(steelArmorPlate)
                        .componentInput(steelChainmail)
                        .criterion(hasItem(steelArmorPlate.getItem()),
                                conditionsFromItem(steelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.LORIEN_HORSE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                conditionsFromItem(edhelSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.CYAN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(edhelSteelChainmail)
                        .componentInput(edhelSteelArmorPlate)
                        .componentInput(edhelSteelChainmail)
                        .criterion(hasItem(edhelSteelArmorPlate.getItem()),
                                conditionsFromItem(edhelSteelArmorPlate.getItem()))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                conditionsFromItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
                        .offerTo(exporter);
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.FABRIC)
                        .input(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR),
                                conditionsFromItem(EquipmentItemsME.GREAT_HORN_PLATE_ARMOR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(Items.LEATHER),
                                conditionsFromItem(Items.LEATHER))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_INGOT)
                        .input(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR)
                        .input(Items.GOLD_INGOT)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.FABRIC)
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR),
                                conditionsFromItem(EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.GOOD)
                        .componentInput(khazadSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(khazadSteelArmorPlate)
                        .input(ResourceItemsME.FABRIC)
                        .componentInput(khazadSteelChainmail)
                        .componentInput(khazadSteelArmorPlate)
                        .componentInput(khazadSteelChainmail)
                        .criterion(hasItem(khazadSteelArmorPlate.getItem()),
                                conditionsFromItem(khazadSteelArmorPlate.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_LEATHER_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
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

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(EquipmentItemsME.WARG_LEATHER_ARMOR)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .input(TagKey.create(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")))
                        .input(Items.LEATHER)
                        .criterion(hasItem(EquipmentItemsME.WARG_LEATHER_ARMOR),
                                conditionsFromItem(EquipmentItemsME.WARG_LEATHER_ARMOR))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .input(Items.LEATHER)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .componentInput(ironChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR.getDefaultInstance(), "mount_armor", DispositionType.EVIL)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelArmorPlate)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelArmorPlate)
                        .componentInput(burzumSteelChainmail)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelChainmail)
                        .criterion(hasItem(ironChainmail.getItem()),
                                conditionsFromItem(ironChainmail.getItem()))
                        .offerTo(exporter);
                //endregion
            
    }

}
