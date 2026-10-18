# Port inventory

- donor: H:\MinecraftMods\_donor\middle-earth\src\main\java
- java files: 1258, lines: 152406

| pattern | hits | files | note |
|---|---:|---:|---|
| identifier-of | 1126 | 171 | 1.21 factory method; 1.20.1 has new Identifier(ns, path) |
| settings-registrykey | 38 | 11 | Settings.registryKey is 1.21+; drop it and register by Identifier |
| data-components | 416 | 64 | DataComponents landed in 1.20.5; 1.20.1 needs NBT / FoodProperties / capabilities |
| component-set | 189 | 51 | component read/write on ItemStack |
| component-package | 179 | 95 | component package does not exist in 1.20.1 |
| registry-entry-armor | 0 | 0 | 1.21 armour material is a registry entry; 1.20.1 uses the ArmorMaterial interface |
| recipe-input | 2 | 1 | 1.20.5+ recipe API; 1.20.1 uses Inventory |
| data-component-type | 0 | 0 | no data component types in 1.20.1 |
| equipment-asset | 3 | 1 | 1.21.2+ equipment assets |
| item-model-definition | 113 | 1 | 1.21.4+ item model definitions (mostly a data concern: assets/<ns>/items/*.json) |

## Component usage (vanilla DataComponentTypes + mod DataComponentTypesME)

| component | uses |
|---|---:|
| FACTION_DATA | 652 |
| DYED_COLOR | 141 |
| BACK_ATTACHMENT_DATA | 117 |
| TRIM | 105 |
| HELMET_ATTACHMENT_DATA | 49 |
| TEMPERATURE_DATA | 25 |
| LORE | 22 |
| PROVIDES_BANNER_PATTERNS | 19 |
| ATTRIBUTE_MODIFIERS | 16 |
| WEAPON | 16 |
| TOOL | 13 |
| FOOD | 12 |
| MOUNT_ARMOR_DATA | 12 |
| BLOCK_AUTHOR_DATA | 12 |
| BANNER_PATTERNS | 11 |
| MAX_DAMAGE | 9 |
| WEAPON_TYPE_DATA | 8 |
| BASE_COLOR | 7 |
| EQUIPPABLE | 7 |
| COOLDOWN | 6 |
| SNEAK_ATTACK_DATA | 6 |
| CONSUMABLE | 5 |
| UNBREAKABLE | 5 |
| GOAT_VARIANT | 4 |
| CHARGED_PROJECTILES | 4 |
| BLOCKS_ATTACKS | 4 |
| ITEM_MODEL | 3 |
| ENTITY_DATA | 3 |
| ITEM_NAME | 3 |
| DAMAGE | 3 |
| USE_REMAINDER | 2 |
| SEASON_DATA | 2 |
| RACE_DATA | 2 |
| CUSTOM_MODEL_DATA | 2 |
| ARTISAN_DATA | 2 |
| ARMOR_TIER_DATA | 2 |
| CONTAINER_LOOT | 1 |
| BLOCK_STATE | 1 |
| ARMOR_VARIANT_DATA | 1 |
| PROFILE | 1 |
| BREAK_SOUND | 1 |

## Worst offenders

### identifier-of (1126)
-  285  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableHandheldRecipeProvider.java
-  135  net/sevenstars/middleearth/datageneration/providers/recipes/RecipeProvider.java
-  102  net/sevenstars/middleearth/datageneration/providers/recipes/InscriptionRecipeProvider.java
-   89  net/sevenstars/middleearth/datageneration/providers/models/BlockModelProvider.java
-   60  net/sevenstars/middleearth/datageneration/providers/tags/ItemTagProvider.java
-   53  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableGenericArmorRecipeProvider.java
-   41  net/sevenstars/middleearth/datageneration/providers/tags/BlockTagProvider.java
-   29  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableArmorRecipeProvider.java
-   19  net/sevenstars/middleearth/gui/playerbook/PlayerBookScreen.java
-   16  net/sevenstars/middleearth/registries/content/structuremanagerdatas/StructureManagerDataPools.java

### settings-registrykey (38)
-    9  net/sevenstars/middleearth/item/EquipmentItemsME.java
-    9  net/sevenstars/middleearth/item/WeaponItemsME.java
-    5  net/sevenstars/middleearth/block/registration/ModNatureBlocks.java
-    3  net/sevenstars/middleearth/block/registration/ModBlocks.java
-    3  net/sevenstars/middleearth/item/ToolItemsME.java
-    2  net/sevenstars/middleearth/block/registration/ModDecorativeBlocks.java
-    2  net/sevenstars/middleearth/item/EggItemsME.java
-    2  net/sevenstars/middleearth/item/ResourceItemsME.java
-    1  net/sevenstars/middleearth/item/DecorativeItemsME.java
-    1  net/sevenstars/middleearth/item/FoodItemsME.java

### data-components (416)
-  126  net/sevenstars/middleearth/item/EquipmentItemsME.java
-   32  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableHandheldRecipeProvider.java
-   25  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableGenericArmorRecipeProvider.java
-   23  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableArmorRecipeProvider.java
-   21  net/sevenstars/middleearth/item/WeaponItemsME.java
-   19  net/sevenstars/middleearth/item/ResourceItemsME.java
-   14  net/sevenstars/middleearth/block/special/forge/ForgeBlockEntity.java
-   11  net/sevenstars/middleearth/registries/RegistriesME.java
-    7  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomLongswordWeaponItem.java
-    7  net/sevenstars/middleearth/item/ToolItemsME.java

### component-set (189)
-   32  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableHandheldRecipeProvider.java
-   25  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableGenericArmorRecipeProvider.java
-   23  net/sevenstars/middleearth/datageneration/providers/recipes/ArtisanTableArmorRecipeProvider.java
-   14  net/sevenstars/middleearth/block/special/forge/ForgeBlockEntity.java
-    6  net/sevenstars/middleearth/block/special/shapingAnvil/ShapingAnvilBlockEntity.java
-    4  net/sevenstars/middleearth/block/special/plate/PlateBlock.java
-    4  net/sevenstars/middleearth/event/ModEvents.java
-    4  net/sevenstars/middleearth/item/utils/ItemGroupsUtil.java
-    4  net/sevenstars/middleearth/resources/datas/npc_types/data/GearItemData.java
-    3  net/sevenstars/middleearth/compat/artisantable/ArtisanTableDisplay.java

### component-package (179)
-    4  net/sevenstars/middleearth/entity/beasts/broadhoof/BroadhoofGoatEntity.java
-    4  net/sevenstars/middleearth/item/FoodItemsME.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomAxeWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomDaggerWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomGlowingDaggerWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomGlowingLongswordWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomLongswordWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomSpearWeaponItem.java
-    4  net/sevenstars/middleearth/item/items/weapons/artefacts/ArtefactCustomSwordWeaponItem.java
-    3  net/sevenstars/middleearth/block/special/forge/ForgeBlockEntity.java

### recipe-input (2)
-    2  net/sevenstars/middleearth/block/special/forge/MultipleStackRecipeInput.java

### equipment-asset (3)
-    3  net/sevenstars/middleearth/item/utils/armor/ArmorMaterialsME.java

### item-model-definition (113)
-  113  net/sevenstars/middleearth/datageneration/providers/models/ItemModelProvider.java
