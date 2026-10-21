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
public class InscriptionRecipeProvider extends FabricRecipeProvider {

    private Holder<Enchantment> getEnchantment(Enchantment enchantment) {
        return Holder.of(enchantment);
    }

    private Holder<Enchantment> getEnchantment(ResourceKey<Enchantment> key) {
        return Holder.of(Registries.ENCHANTMENT.get(key.location()));
    }

    public InscriptionRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "InscriptionRecipes";
    }

    @Override
    public void generate(Consumer<FinishedRecipe> exporter) {

                //region UNBREAKING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 1, 1)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_unbreaking_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 2, 3)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_unbreaking_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.UNBREAKING), 3, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("resilient")
                        .addWord("blessing")
                        .criterion(hasItem(Items.LAPIS_LAZULI),
                                conditionsFromItem(Items.LAPIS_LAZULI))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_unbreaking_3"));
                //endregion

                //region THORNS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_thorns_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_thorns_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.THORNS), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_thorns_3"));
                //endregion

                //region PROJECTILE PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_projectile_protection_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_projectile_protection_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_projectile_protection_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PROJECTILE_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("pierce")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_projectile_protection_4"));
                //endregion

                //region FIRE PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fire_protection_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fire_protection_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fire_protection_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FIRE_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fire_protection_4"));
                //endregion

                //region BLAST PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_blast_protection_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_blast_protection_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_blast_protection_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.BLAST_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("flame")
                        .addWord("sturdy")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_blast_protection_4"));
                //endregion

                //region RESPIRATION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_respiration_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_respiration_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.RESPIRATION), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("tidal")
                        .addWord("blessing")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_respiration_3"));
                //endregion

                //region FEATHER FALLING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_feather_falling_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_feather_falling_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_feather_falling_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FEATHER_FALLING), 4, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_feather_falling_4"));
                //endregion

                //region AQUA AFINITY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.AQUA_AFFINITY), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("careful")
                        .addWord("tidal")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_aqua_affinity"));
                // endregion

                //region DEPTH STRIDER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_depth_strider_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_depth_strider_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.DEPTH_STRIDER), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_depth_strider_3"));
                //endregion

                //region LURE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LURE), 1, 2)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_lure_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LURE), 2, 4)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_lure_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LURE), 3, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("tidal")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_lure_3"));
                //endregion

                //region SHARPNESS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sharpness_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sharpness_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 3, 7)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sharpness_3"));

                /*InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sharpness_4"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SHARPNESS), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("forceful")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sharpness_5"));*/
                //endregion

                //region KNOCKBACK
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.KNOCKBACK), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_knockback_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.KNOCKBACK), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_knockback_2"));
                //endregion

                //region LOOTING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_looting_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_looting_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.LOOTING), 3, 7)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_looting_3"));
                //endregion

                //region SMITE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_smite_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_smite_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_smite_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_smite_4"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SMITE), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("spirit")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_smite_5"));
                //endregion

                //region SWEEPING EDGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sweeping_edge_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sweeping_edge_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SWEEPING), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("edge")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_sweeping_edge_3"));
                //endregion

                //region POWER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_power_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_power_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 3, 7)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_power_3"));

                /*InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_power_4"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.POWER), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("fierce")
                        .addWord("point")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_power_5"));*/
                //endregion

                //region PUNCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_punch_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PUNCH), 2, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("forceful")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_punch_2"));
                //endregion

                //region PIERCING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_piercing_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_piercing_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_piercing_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.PIERCING), 4, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("long")
                        .addWord("point")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_piercing_4"));

                //endregion

                //region QUICK CHARGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_quick_charge_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_quick_charge_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.QUICK_CHARGE), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("draw")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_quick_charge_3"));
                //endregion

                //region EFFICIENCY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 1, 2)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_efficiency_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 2, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_efficiency_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 3, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_efficiency_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 4, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_efficiency_4"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.EFFICIENCY), 5, 10)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("swift")
                        .addWord("collector")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_efficiency_5"));
                //endregion

                //region FORTUNE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 1, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fortune_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 2, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fortune_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.FORTUNE), 3, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("gifted")
                        .addWord("blessing")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_fortune_3"));
                //endregion

                //region SILK TOUCH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(Enchantments.SILK_TOUCH), 1, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("careful")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_silk_touch"));
                //endregion

                //region AILMENT PROTECTION
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_ailment_protection_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_ailment_protection_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_ailment_protection_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 4, 10)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("resilient")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_ailment_protection_4"));
                //endregion

                //region AULE BLESSING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AULE_BLESSING), 1, 4)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("gifted")
                        .addWord("core")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_aule_blessing_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 2, 6)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("core")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_aule_blessing_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.AILMENT_PROTECTION), 3, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("gifted")
                        .addWord("core")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_aule_blessing_3"));
                //endregion

                //region BANE OF GIANTS
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_bane_of_giants_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 2, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_bane_of_giants_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_bane_of_giants_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 4, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_bane_of_giants_4"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.BANE_OF_GIANTS), 5, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("giant")
                        .addWord("bane")
                        .addWord("cutter")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_bane_of_giants_5"));
                //endregion

                //region CELERITY
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.CELERITY), 1, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("edge")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_celerity"));
                //endregion

                //region GROUNDED
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_grounded_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_grounded_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 3, 9)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_grounded_3"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.GROUNDED), 4, 11)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("edge")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_grounded_4"));
                //endregion

                //region HEWING
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_hewing_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_hewing_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HEWING), 3, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("broad")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_hewing_3"));
                //endregion

                //region TREE_FELLER
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.TREE_FELLER), 1, 5)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("broad")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_tree_feller_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.TREE_FELLER), 2, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("broad")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_tree_feller_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.TREE_FELLER), 3, 9)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("broad")
                        .addWord("cutter")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_tree_feller_3"));
                //endregion

                //region HIGH STEP
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.HIGH_STEP), 1, 8)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("traveller")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_high_step"));
                //endregion

                //region MINER_REACH
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.MINER_REACH), 1, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("collector")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_miner_reach"));
                //endregion

                //region STEALTHY_TRAIL
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stealthy_trail_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stealthy_trail_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STEALTHY_TRAIL), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("noiseless")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.RUBY),
                                conditionsFromItem(ResourceItemsME.RUBY))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stealthy_trail_3"));
                //endregion

                //region STALWART
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STALWART), 1, 8)
                        .chisel(ItemTagsME.LATE_CHISELS)
                        .addWord("sturdy")
                        .addWord("point")
                        .addWord("warded")
                        .criterion(hasItem(ResourceItemsME.SAPPHIRE),
                                conditionsFromItem(ResourceItemsME.SAPPHIRE))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stalwart"));
                //endregion

                //region STRIDE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 1, 3)
                        .chisel(ItemTagsME.EARLY_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stride_1"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 2, 5)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stride_2"));

                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.STRIDE), 3, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("swift")
                        .addWord("traveller")
                        .criterion(hasItem(ResourceItemsME.ADAMANT),
                                conditionsFromItem(ResourceItemsME.ADAMANT))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_stride_3"));
                //endregion

                //region VANTAGE
                InscriptionRecipeJsonBuilder.createInscriptionRecipe(RecipeCategory.MISC,
                                getEnchantment(EnchantmentsME.VANTAGE), 1, 7)
                        .chisel(ItemTagsME.MID_CHISELS)
                        .addWord("long")
                        .addWord("edge")
                        .criterion(hasItem(Items.EMERALD),
                                conditionsFromItem(Items.EMERALD))
                        .offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, "inscription_vantage"));
                //endregion
            
    }

}
