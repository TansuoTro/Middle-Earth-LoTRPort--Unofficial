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
public class ArtisanTableHandheldRecipeProvider extends FabricRecipeProvider {
    private final int XP_MEDIUM_SHIELD = 2;
    private final int XP_HEAVY_SHIELD = 4;

    private ResourceLocation getMetalIdentifier(MetalTypes metal) {
        if (metal.isVanilla()) {
            return new ResourceLocation(metal.getName());
        }
        return new ResourceLocation(MiddleEarth.MOD_ID, metal.getName());
    }

    private static CompoundTag trimNbt(ResourceLocation materialId) {
        return ArtisanTableRecipeJsonBuilder.trimNbt(materialId);
    }

    public ArtisanTableHandheldRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "ArtisanTableHandheldRecipes";
    }

    @Override
    public void generate(Consumer<FinishedRecipe> exporter) {

                //region WEAPONS
                createArtisanTableSwordRecipe(exporter, MetalTypes.IRON, Items.IRON_SWORD.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.IRON, WeaponItemsME.IRON_DAGGER.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.IRON, WeaponItemsME.IRON_SPEAR.getDefaultStack(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.GOLD, Items.GOLDEN_SWORD.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_DAGGER.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.GOLD, WeaponItemsME.GOLDEN_SPEAR.getDefaultStack(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.NETHERITE, Items.NETHERITE_SWORD.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_DAGGER.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.NETHERITE, WeaponItemsME.NETHERITE_SPEAR.getDefaultStack(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SWORD.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_DAGGER.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BRONZE, WeaponItemsME.BRONZE_SPEAR.getDefaultStack(), false, DispositionType.NEUTRAL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_FALCHION.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_DAGGER.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.CRUDE, WeaponItemsME.CRUDE_LONGBLADE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_FALCHION.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SHANK.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SCIMITAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.CRUDE, Items.BONE, WeaponItemsME.GOBLIN_TOWN_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SWORD.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SWORD.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_DAGGER.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_DAGGER.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_LONGSWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_LONGSWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_LONGSWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SCIMITAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_LONGSWORD.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.GONDORIAN_FOUNTAIN_GUARD_SPEAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.ROHIRRIC_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_HEYDAY_SPEAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.DALISH_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.STEEL_SPEAR.getDefaultStack(), false, DispositionType.NEUTRAL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.STEEL, WeaponItemsME.BLACK_NUMENOREAN_SPEAR.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SWORD.getDefaultStack(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_LONGSWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.KHAZAD_STEEL_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.KHAZAD_STEEL, WeaponItemsME.EREBOR_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SWORD.getDefaultStack(), false, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_GLAIVE.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_GLAIVE.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.EDHEL_STEEL_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.LORIEN_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSwordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SWORD.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_DAGGER.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_DAGGER.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_LONGSWORD.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGSWORD.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.GOOD);
                createArtisanTableAxeRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.GOOD);

                createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_SPEAR.getDefaultStack(), false, DispositionType.GOOD);
                createArtisanTableSpearRecipe(exporter, MetalTypes.EDHEL_STEEL, WeaponItemsME.WOODLAND_REALM_NOBLE_SPEAR.getDefaultStack(), true, DispositionType.GOOD);

                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SWORD.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SWORD.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_FALCHION.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_CLEAVER.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_MACHETE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_CLEAVER.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_FALCHION.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_FALCHION.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_CLEAVER.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSwordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_FALCHION.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_KNIFE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_DAGGER.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_DAGGER.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_DAGGER.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_KNIFE.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SHANK.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_DAGGER.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableDaggerRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SHANK.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_BROADBLADE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SCIMITAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_WARBLADE.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_WARBLADE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_WARBLADE.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_WARBLADE.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SCIMITAR.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableLongswordRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKBLADE.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.DOL_GULDUR_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_AXE.getDefaultStack(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_AXE.getDefaultStack(), false, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_BATTLEAXE.getDefaultStack(), true, Optional.empty(), DispositionType.EVIL);
                createArtisanTableAxeRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_HOOKAXE.getDefaultStack(), true, Optional.empty(), DispositionType.EVIL);

                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.BURZUM_STEEL_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ORC_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORDOR_ELITE_SPEAR.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.ISENGARD_ORC_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.URUK_HAI_SPEAR.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_SPEAR.getDefaultStack(), false, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.GUNDABAD_ELITE_SPEAR.getDefaultStack(), true, DispositionType.EVIL);
                createArtisanTableSpearRecipe(exporter, MetalTypes.BURZUM_STEEL, WeaponItemsME.MORIA_GOBLIN_SPEAR.getDefaultStack(), true, DispositionType.EVIL);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.GONDORIAN_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.GONDORIAN_LONGBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.GONDORIAN_NOBLE_LONGBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.ROHIRRIC_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(exporter, WeaponItemsME.ROHIRRIC_NOBLE_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.ROHIRRIC_LONGBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.DALISH_LONGBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.DALISH_HEYDAY_LONGBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.DALISH_NOBLE_LONGBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.LORIEN_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.LORIEN_LONGBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.LORIEN_NOBLE_LONGBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.WOODLAND_REALM_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.WOODLAND_REALM_LONGBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(exporter, WeaponItemsME.WOODLAND_REALM_NOBLE_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.WOODLAND_REALM_NOBLE_LONGBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.EREBOR_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleBowRecipe(exporter, WeaponItemsME.EREBOR_NOBLE_BOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableCrossbowRecipe(exporter, WeaponItemsME.EREBOR_CROSSBOW.getDefaultStack(), DispositionType.GOOD);
                createArtisanTableNobleCrossbowRecipe(exporter, WeaponItemsME.EREBOR_NOBLE_CROSSBOW.getDefaultStack(), DispositionType.GOOD);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.ORCISH_BOW.getDefaultStack(), DispositionType.EVIL);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.MORDOR_BOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableNobleLongbowRecipe(exporter, WeaponItemsME.MORDOR_ELITE_LONGBOW.getDefaultStack(), DispositionType.EVIL);

                createArtisanTableNobleBowRecipe(exporter, WeaponItemsME.URUK_HAI_BOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableNobleCrossbowRecipe(exporter, WeaponItemsME.URUK_HAI_CROSSBOW.getDefaultStack(), DispositionType.EVIL);

                createArtisanTableBowRecipe(exporter, WeaponItemsME.GUNDABAD_BOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableLongbowRecipe(exporter, WeaponItemsME.GUNDABAD_LONGBOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableCrossbowRecipe(exporter, WeaponItemsME.GOBLIN_CROSSBOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableBowRecipe(exporter, WeaponItemsME.GOBLIN_TOWN_BOW.getDefaultStack(), DispositionType.EVIL);
                createArtisanTableBowRecipe(exporter, WeaponItemsME.MORIA_GOBLIN_BOW.getDefaultStack(), DispositionType.EVIL);

                createArtisanTableBowRecipe(exporter, Items.BOW.getDefaultStack(), DispositionType.NEUTRAL);
                createArtisanTableCrossbowRecipe(exporter, Items.CROSSBOW.getDefaultStack(), DispositionType.NEUTRAL);
                //endregion

                //region TOOLS
                createToolSet(exporter, MetalTypes.BRONZE, ToolItemsME.BRONZE_PICKAXE.getDefaultStack(),
                        ToolItemsME.BRONZE_AXE.getDefaultStack(),
                        ToolItemsME.BRONZE_SHOVEL.getDefaultStack(),
                        ToolItemsME.BRONZE_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.CRUDE, ToolItemsME.CRUDE_PICKAXE.getDefaultStack(),
                        ToolItemsME.CRUDE_AXE.getDefaultStack(),
                        ToolItemsME.CRUDE_SHOVEL.getDefaultStack(),
                        ToolItemsME.CRUDE_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(exporter, MetalTypes.IRON, Items.IRON_PICKAXE.getDefaultStack(),
                        Items.IRON_AXE.getDefaultStack(),
                        Items.IRON_SHOVEL.getDefaultStack(),
                        Items.IRON_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.GOLD, Items.GOLDEN_PICKAXE.getDefaultStack(),
                        Items.GOLDEN_AXE.getDefaultStack(),
                        Items.GOLDEN_SHOVEL.getDefaultStack(),
                        Items.GOLDEN_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.STEEL, ToolItemsME.STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.STEEL_AXE.getDefaultStack(),
                        ToolItemsME.STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.STEEL_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.KHAZAD_STEEL, ToolItemsME.KHAZAD_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.KHAZAD_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(exporter, MetalTypes.EDHEL_STEEL, ToolItemsME.EDHEL_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.EDHEL_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.GOOD);

                createToolSet(exporter, MetalTypes.BURZUM_STEEL, ToolItemsME.BURZUM_STEEL_PICKAXE.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_AXE.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_SHOVEL.getDefaultStack(),
                        ToolItemsME.BURZUM_STEEL_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.EVIL);

                createToolSet(exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.STEEL), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.KHAZAD_STEEL), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.EDHEL_STEEL), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.MITHRIL, ToolItemsME.MITHRIL_PICKAXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_AXE.getDefaultStack(),
                        ToolItemsME.MITHRIL_SHOVEL.getDefaultStack(),
                        ToolItemsME.MITHRIL_HOE.getDefaultStack(),
                        Optional.of(MetalTypes.BURZUM_STEEL), DispositionType.NEUTRAL);

                createToolSet(exporter, MetalTypes.NETHERITE, Items.NETHERITE_PICKAXE.getDefaultStack(),
                        Items.NETHERITE_AXE.getDefaultStack(),
                        Items.NETHERITE_SHOVEL.getDefaultStack(),
                        Items.NETHERITE_HOE.getDefaultStack(),
                        Optional.empty(), DispositionType.NEUTRAL);

                createArtisanTableChiselRecipe(exporter, MetalTypes.IRON, Items.IRON_NUGGET, ToolItemsME.IRON_CHISEL.getDefaultStack());
                createArtisanTableChiselRecipe(exporter, MetalTypes.STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultStack());
                createArtisanTableChiselRecipe(exporter, MetalTypes.KHAZAD_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultStack());
                createArtisanTableChiselRecipe(exporter, MetalTypes.EDHEL_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultStack());
                createArtisanTableChiselRecipe(exporter, MetalTypes.BURZUM_STEEL, Items.GOLD_NUGGET, ToolItemsME.STEEL_CHISEL.getDefaultStack());
                createArtisanTableChiselRecipe(exporter, MetalTypes.MITHRIL, ResourceItemsME.MITHRIL_NUGGET, ToolItemsME.MITHRIL_CHISEL.getDefaultStack());
                        
                //endregion

                //region SHIELDS
                ItemStack ironShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                ironShieldBorder.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));

                ItemStack bronzeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                bronzeShieldBorder.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BRONZE.getName())));

                ItemStack crudeShieldBorder = new ItemStack(ResourceItemsME.SHIELD_BORDER);
                crudeShieldBorder.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.CRUDE.getName())));

                ItemStack steelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                steelShieldPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.STEEL.getName())));

                ItemStack edhelSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                edhelSteelShieldPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.EDHEL_STEEL.getName())));

                ItemStack khazadSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                khazadSteelShieldPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.KHAZAD_STEEL.getName())));

                ItemStack burzumSteelShieldPlate = new ItemStack(ResourceItemsME.SHIELD_PLATE);
                burzumSteelShieldPlate.setSubNbt("Trim", trimNbt(new ResourceLocation(MiddleEarth.MOD_ID, MetalTypes.BURZUM_STEEL.getName())));

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, Items.SHIELD.getDefaultStack(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(ironShieldBorder)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(Items.SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROUND_SHIELD.getDefaultStack(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(ironShieldBorder)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.HEATER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(ironShieldBorder)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.KITE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.NEUTRAL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(ironShieldBorder)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.KITE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_KINGS_GUARD_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LAST_ALLIANCE_HEIRLOOM_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_HERO_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_HERO_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.GONDOR_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_ORNAMENTED_KNIGHT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.WHITE_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_BUCKING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_GALLOPING_HORSE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_HORSE_HEAD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_PLAINSMAN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GREEN_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.GREEN_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_TWIN_HORSES_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_EORLING_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_EORLING_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(ResourceItemsME.ROHAN_BANNER_PATTERN)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.ROHIRRIC_ROYAL_GUARD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_OVAL_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BLUE_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_OVAL_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(ironShieldBorder)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_OVAL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_BLUE_BRACED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLUE_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLUE_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BLUE_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_BRACED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .componentInput(ironShieldBorder)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.WHITE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_BARDING_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_ROYAL_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LIGHT_BLUE_DYE)
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_ROYAL_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.ORANGE_DYE)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(steelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DALISH_HEYDAY_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.LORIEN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.LORIEN_LAURELS_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_LAURELS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.LORIEN_MALLORN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.YELLOW_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.LORIEN_MALLORN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GALADHRIM_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_LORD_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.YELLOW_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(Items.LEATHER)
                        .input(Items.GOLD_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GALADHRIM_LORD_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.COPPER_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(bronzeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.SILVER_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_SCOUT_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(ironShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_SCOUT_BRONZE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GALADHRIM_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_HEAVY_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_HEAVY_GREEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLUE_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_HEAVY_BLUE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BROWN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_HEAVY_ORNAMENTED_GREEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GREEN_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_GLADE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.BLACK_DYE)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(edhelSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .input(Items.GOLD_INGOT)
                        .input(ResourceItemsME.EDHEL_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.WOODLAND_REALM_NIGHTSHADE_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(bronzeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_CROSS_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(bronzeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_CROSS_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_PLATED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(bronzeShieldBorder)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_PLATED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.GOLD_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(bronzeShieldBorder)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_ORNAMENTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.GOOD, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .componentInput(bronzeShieldBorder)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_BUCKLER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.BRONZE_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_BUCKLER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.EREBOR_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_INGOT)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.GOOD, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(khazadSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .input(Items.GOLD_NUGGET)
                        .input(ResourceItemsME.KHAZAD_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RAVENHILL_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(WeaponItemsME.MORDOR_WOODEN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_WOODEN_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_WOODEN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_ROUND_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.MORDOR_BANNER_PATTERN),
                                conditionsFromItem(ResourceItemsME.MORDOR_BANNER_PATTERN))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_ROUND_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_ROUND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BRACED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_BRACED_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_BRACED_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_BRACED_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_BRACED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_LARGE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_LARGE_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_LARGE_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD)
                        .input(Items.BLACK_DYE)
                        .criterion(hasItem(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_PAINTED_LARGE_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_BLACK_LARGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.IRON_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DOL_GULDUR_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_PAVISE.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DOL_GULDUR_PAVISE).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_ARMRUST_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DOL_GULDUR_ARMRUST_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DOL_GULDUR_HEAVY_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.DOL_GULDUR_HEAVY_SKULL_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ModDecorativeBlocks.OLD_SKULL.asItem())
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.DOL_GULDUR_HEAVY_SKULL_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GONDORIAN_CONVERTED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(WeaponItemsME.GONDORIAN_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.GONDORIAN_SHIELD),
                                conditionsFromItem(WeaponItemsME.GONDORIAN_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GONDORIAN_CONVERTED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.MORDOR_HEAVY_SHIELD)
                        .input(ResourceItemsME.MORDOR_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.MORDOR_HEAVY_SHIELD),
                                conditionsFromItem(WeaponItemsME.MORDOR_HEAVY_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORDOR_PAINTED_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.RED_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BLACK_DYE)
                        .componentInput(ironShieldBorder)
                        .input(Items.BLACK_DYE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.BLACK_NUMENOREAN_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_HEATER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_HEATER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_WHITE_PALMPRINT_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_S_RUNE_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(WeaponItemsME.URUK_HAI_SHIELD)
                        .input(ResourceItemsME.ISENGARD_BANNER_PATTERN)
                        .criterion(hasItem(WeaponItemsME.URUK_HAI_SHIELD),
                                conditionsFromItem(WeaponItemsME.URUK_HAI_SHIELD))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_S_RUNE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.URUK_HAI_SIEGE_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(burzumSteelShieldPlate)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .componentInput(burzumSteelShieldPlate)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.URUK_HAI_SIEGE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_GREAT_EYE_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.MISTY_MOUNTAINS_ORCS_BANNER_PATTERN)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_PEAKS_PAINTED_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GUNDABAD_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .componentInput(burzumSteelShieldPlate)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GUNDABAD_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .componentInput(crudeShieldBorder)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORIA_GOBLINS_BUCKLER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.MORIA_GOBLINS_HEAVY_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(crudeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(crudeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_CROSS_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(crudeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .componentInput(crudeShieldBorder)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_ORNAMENTED_TOWER_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(WeaponItemsME.RUINED_DWARVEN_REINFORCED_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.RUINED_DWARVEN_REINFORCED_TOWER_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD.getDefaultStack(), "light_shield", DispositionType.EVIL)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_INGOT)
                        .input(Items.BONE)
                        .input(Items.BONE)
                        .criterion(hasItem(ResourceItemsME.CRUDE_INGOT),
                                conditionsFromItem(ResourceItemsME.CRUDE_INGOT))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GOBLIN_TOWN_BONE_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.BONE)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.BONE)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.BONE)
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GOBLIN_TOWN_BONE_WOODEN_SHIELD).getPath() + "_artisan");
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD.getDefaultStack(), "medium_shield", DispositionType.EVIL, XP_MEDIUM_SHIELD)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.CRUDE_NUGGET)
                        .input(Items.LEATHER)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(WeaponItemsME.GOBLIN_TOWN_WOODEN_SHIELD)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(Items.LEATHER)
                        .criterion(hasItem(ResourceItemsME.SHIELD_BORDER),
                                conditionsFromItem(ResourceItemsME.SHIELD_BORDER))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GOBLIN_TOWN_LEATHER_WOODEN_SHIELD).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD.getDefaultStack(), "heavy_shield", DispositionType.EVIL, XP_HEAVY_SHIELD)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .componentInput(burzumSteelShieldPlate)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .input(Items.LEATHER)
                        .input(ResourceItemsME.BURZUM_STEEL_NUGGET)
                        .criterion(hasItem(ResourceItemsME.SHIELD_PLATE),
                                conditionsFromItem(ResourceItemsME.SHIELD_PLATE))
                        .offerTo(exporter, Registries.ITEM.getId(WeaponItemsME.GOBLIN_TOWN_HEAVY_SHIELD).getPath() + "_artisan");
                //endregion

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ToolItemsME.PIPE.getDefaultStack(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ToolItemsME.CLAY_PIPE.getDefaultStack(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(Items.TERRACOTTA)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(Items.TERRACOTTA)
                        .input(Items.TERRACOTTA)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.CLAY_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ToolItemsME.RIVERBEND_PIPE.getDefaultStack(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.RIVERBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ToolItemsME.BRIMMINGBEND_PIPE.getDefaultStack(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(ResourceItemsME.BRONZE_NUGGET)
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.BRIMMINGBEND_PIPE).getPath() + "_artisan");

                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, ToolItemsME.LONGBOTTOM_PIPE.getDefaultStack(), "pipe", DispositionType.NEUTRAL)
                        .input(Items.STICK)
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_slabs")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .input(TagKey.of(Registries.ITEM, new ResourceLocation("planks")))
                        .criterion(hasItem(Items.STICK),
                                conditionsFromItem(Items.STICK))
                        .offerTo(exporter, Registries.ITEM.getId(ToolItemsME.LONGBOTTOM_PIPE).getPath() + "_artisan");

    }

    private int getXpFor(MetalTypes metalTypes) {
        return switch (metalTypes) {
            default -> 0;
            case EMPTY -> 0;
            case COPPER, TIN -> 2;
            case BRONZE, CRUDE -> 3;
            case IRON, SILVER, LEAD -> 4;
            case STEEL, EDHEL_STEEL, KHAZAD_STEEL, BURZUM_STEEL -> 5;
            case GOLD, NETHERITE -> 7;
            case MITHRIL -> 12;
        };
    }

    private void createToolSet(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack outputPickaxe, ItemStack outputAxe, ItemStack outputShovel, ItemStack outputHoe, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTablePickaxeRecipe(exporter, metal, outputPickaxe, rodMetal, dispositionType);
        createArtisanTableAxeRecipe(exporter, metal, outputAxe, false, rodMetal, dispositionType);
        createArtisanTableShovelRecipe(exporter, metal, outputShovel, rodMetal, dispositionType);
        createArtisanTableHoeRecipe(exporter, metal, outputHoe, rodMetal, dispositionType);
    }

    private void createArtisanTableSwordRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSwordRecipe(exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSwordRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.BLADE);
        blade.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = getXpFor(metal);

        if (!noble) {
            swordHilt.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        } else {
            swordHilt.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(blade)
                .componentInput(swordHilt)
                .input(stick)
                .criterion(hasItem(blade.getItem()),
                        conditionsFromItem(blade.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableLongswordRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableLongswordRecipe(exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableLongswordRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack longBlade = new ItemStack(ResourceItemsME.LONG_BLADE);
        longBlade.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble) {
            swordHilt.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        } else {
            swordHilt.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(longBlade)
                .componentInput(swordHilt)
                .input(stick)
                .criterion(hasItem(longBlade.getItem()),
                        conditionsFromItem(longBlade.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableDaggerRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableDaggerRecipe(exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableDaggerRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));

        ItemStack swordHilt = new ItemStack(ResourceItemsME.SWORD_HILT);
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            swordHilt.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        } else {
            swordHilt.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
            xp++;
        }

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "sword", dispositionType, xp)
                .componentInput(shortBlade)
                .componentInput(swordHilt)
                .input(stick)
                .criterion(hasItem(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem()))
                .offerTo(exporter);
    }

    private void createArtisanTableSpearRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, boolean noble, DispositionType dispositionType) {
        createArtisanTableSpearRecipe(exporter, metal, Items.STICK, output, noble, dispositionType);
    }

    private void createArtisanTableSpearRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, DispositionType dispositionType) {
        ItemStack blade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        blade.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (!noble) {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(blade)
                    .input(stick)
                    .input(stick)
                    .criterion(hasItem(blade.getItem()),
                            conditionsFromItem(blade.getItem()))
                    .offerTo(exporter);
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            xp++;
            rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "spear", dispositionType, xp)
                    .componentInput(blade)
                    .componentInput(rod)
                    .input(stick)
                    .criterion(hasItem(blade.getItem()),
                            conditionsFromItem(blade.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableBowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleBowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "bow", dispositionType, XP_MEDIUM_SHIELD)
                .input(Items.STICK)
                .input(Items.STRING)
                .componentInput(rod)
                .input(Items.STRING)
                .input(Items.STICK)
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTableLongbowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "bow", dispositionType)
                .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_fences")))
                .input(Items.STRING)
                .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_fences")))
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleLongbowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "bow", dispositionType, 2)
                .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_fences")))
                .input(Items.STRING)
                .componentInput(rod)
                .input(Items.STRING)
                .input(TagKey.of(Registries.ITEM, new ResourceLocation("wooden_fences")))
                .input(Items.STRING)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTableCrossbowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.IRON.getName())));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(rod)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTableNobleCrossbowRecipe(Consumer<FinishedRecipe> exporter, ItemStack output, DispositionType dispositionType) {
        ItemStack rod = new ItemStack(ResourceItemsME.ROD);
        rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "crossbow", dispositionType, 2)
                .input(Items.STICK)
                .componentInput(rod)
                .input(Items.STICK)
                .input(Items.STRING)
                .input(Blocks.TRIPWIRE_HOOK)
                .input(Items.STRING)
                .input(Items.STICK)
                .criterion(hasItem(Items.STRING),
                        conditionsFromItem(Items.STRING))
                .offerTo(exporter);
    }

    private void createArtisanTablePickaxeRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack pickaxeHead = new ItemStack(ResourceItemsME.PICKAXE_HEAD);
        pickaxeHead.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.setSubNbt("Trim", trimNbt(getMetalIdentifier(rodMetal.get())));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(pickaxeHead)
                    .componentInput(rod)
                    .componentInput(rod)
                    .criterion(hasItem(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "pickaxe", dispositionType, xp)
                    .componentInput(pickaxeHead)
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(pickaxeHead.getItem()),
                            conditionsFromItem(pickaxeHead.getItem()))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_artisan");
        }
    }

    private void createArtisanTableAxeRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        createArtisanTableAxeRecipe(exporter, metal, Items.STICK, output, noble, rodMetal, dispositionType);
    }

    private void createArtisanTableAxeRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item stick, ItemStack output, boolean noble, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack axeHead = new ItemStack(ResourceItemsME.AXE_HEAD);
        axeHead.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = (int) (getXpFor(metal) * 1.5f);

        if (!noble){
            if (rodMetal.isPresent()){
                ItemStack rod = new ItemStack(ResourceItemsME.ROD);
                rod.setSubNbt("Trim", trimNbt(getMetalIdentifier(rodMetal.get())));
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(axeHead)
                        .componentInput(rod)
                        .componentInput(rod)
                        .criterion(hasItem(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem()))
                        .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
            } else {
                ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", dispositionType, xp)
                        .componentInput(axeHead)
                        .input(stick)
                        .input(stick)
                        .criterion(hasItem(axeHead.getItem()),
                                conditionsFromItem(axeHead.getItem()))
                        .offerTo(exporter);
            }
        } else {
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.setSubNbt("Trim", trimNbt(new ResourceLocation(MetalTypes.GOLD.getName())));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "axe", dispositionType, xp + 1)
                    .componentInput(axeHead)
                    .componentInput(rod)
                    .input(stick)
                    .criterion(hasItem(axeHead.getItem()),
                            conditionsFromItem(axeHead.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableShovelRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack shovelHead = new ItemStack(ResourceItemsME.SHOVEL_HEAD);
        shovelHead.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = (int) (getXpFor(metal) * 0.5f);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.setSubNbt("Trim", trimNbt(getMetalIdentifier(rodMetal.get())));
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(shovelHead)
                    .componentInput(rod)
                    .componentInput(rod)
                    .criterion(hasItem(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem()))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "shovel", dispositionType, xp)
                    .componentInput(shovelHead)
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(shovelHead.getItem()),
                            conditionsFromItem(shovelHead.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableHoeRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, ItemStack output, Optional<MetalTypes> rodMetal, DispositionType dispositionType) {
        ItemStack hoeHead = new ItemStack(ResourceItemsME.HOE_HEAD);
        hoeHead.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = getXpFor(metal);

        if (rodMetal.isPresent()){
            ItemStack rod = new ItemStack(ResourceItemsME.ROD);
            rod.setSubNbt("Trim", trimNbt(getMetalIdentifier(rodMetal.get())));
            
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(hoeHead)
                    .componentInput(rod)
                    .componentInput(rod)
                    .criterion(hasItem(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem()))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + rodMetal.get().getName() + "_artisan");
        } else {
            ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "hoe", dispositionType, xp)
                    .componentInput(hoeHead)
                    .input(Items.STICK)
                    .input(Items.STICK)
                    .criterion(hasItem(hoeHead.getItem()),
                            conditionsFromItem(hoeHead.getItem()))
                    .offerTo(exporter);
        }
    }

    private void createArtisanTableChiselRecipe(Consumer<FinishedRecipe> exporter, MetalTypes metal, Item nugget, ItemStack output) {
        ItemStack shortBlade = new ItemStack(ResourceItemsME.SHORT_BLADE);
        shortBlade.setSubNbt("Trim", trimNbt(getMetalIdentifier(metal)));
        int xp = (int) (getXpFor(metal) * 0.5f);

        ArtisanTableRecipeJsonBuilder.createArtisanRecipe(RecipeCategory.COMBAT, output, "chisel", DispositionType.NEUTRAL, xp)
                .componentInput(shortBlade)
                .input(nugget)
                .input(Items.STICK)
                .criterion(hasItem(shortBlade.getItem()),
                        conditionsFromItem(shortBlade.getItem()))
                    .offerTo(exporter, Registries.ITEM.getId(output.getItem()).getPath() + "_" + metal.getName() + "_artisan");
    }

}
