// Rosetta exec method body. Run after datagen, processResources and world load/reload.
com.google.gson.JsonObject report = new com.google.gson.JsonObject();
com.google.gson.JsonObject sizes = new com.google.gson.JsonObject();
java.util.List<String> failures = new java.util.ArrayList<>();
String[][] memberships = {
    {"me:repairs_leather_armor", "minecraft:leather"},
    {"me:repairs_iron_armor", "minecraft:iron_ingot"},
    {"me:repairs_straw_armor", "me:straw"},
    {"me:repairs_wool_armor", "minecraft:red_wool"},
    {"me:repairs_bone_armor", "me:fang"},
    {"me:repairs_fur_armor", "me:fur"},
    {"me:repairs_fabric_armor", "me:fabric"},
    {"me:repairs_bronze_armor", "me:bronze_ingot"},
    {"me:repairs_crude_armor", "me:crude_ingot"},
    {"me:repairs_steel_armor", "me:steel_ingot"},
    {"me:repairs_khazad_steel_armor", "me:khazad_steel_ingot"},
    {"me:repairs_edhel_steel_armor", "me:edhel_steel_ingot"},
    {"me:repairs_burzum_steel_armor", "me:burzum_steel_ingot"},
    {"me:bone_tool_materials", "me:dirty_bone"},
    {"me:copper_tool_materials", "minecraft:copper_ingot"},
    {"me:bronze_tool_materials", "me:bronze_ingot"},
    {"me:crude_tool_materials", "me:crude_ingot"},
    {"me:steel_tool_materials", "me:steel_ingot"},
    {"me:khazad_steel_tool_materials", "me:khazad_steel_ingot"},
    {"me:edhel_steel_tool_materials", "me:edhel_steel_ingot"},
    {"me:burzum_steel_tool_materials", "me:burzum_steel_ingot"},
    {"me:mithril_tool_materials", "me:mithril_ingot"},
    {"me:spider_tool_materials", "me:spider_stinger"},
    {"me:early_chisels", "me:iron_chisel"},
    {"me:early_chisels", "me:mithril_chisel"},
    {"me:mid_chisels", "me:steel_chisel"},
    {"me:mid_chisels", "me:mithril_chisel"},
    {"me:late_chisels", "me:mithril_chisel"},
    {"me:inscription_catalysts", "minecraft:lapis_lazuli"},
    {"me:inscription_catalysts", "me:adamant"},
    {"me:character_helmet_should_show_ears", "me:lorien_diadem"},
    {"me:character_helmet_should_hide_hair", "me:mail_coif"},
    {"me:character_helmet_should_hide_beard", "me:closed_mail_coif"},
    {"me:elk_food", "me:clovers"},
    {"minecraft:cow_food", "minecraft:wheat"},
    {"minecraft:goat_food", "minecraft:wheat"},
    {"me:warg_food", "me:raw_horse"},
    {"me:troll_food", "minecraft:rotten_flesh"},
    {"me:troll_weapons", "me:troll_mace"},
    {"me:chains", "minecraft:chain"},
    {"me:warg_armor", "me:warg_mordor_plate_armor"},
    {"me:horse_armor", "me:gondorian_horse_armor"},
    {"me:anvil_items", "me:rod"},
    {"me:anvil_items", "me:steel_ingot"},
    {"me:steel_ingots", "me:edhel_steel_ingot"},
    {"minecraft:enchantable/bow", "minecraft:bow"},
    {"minecraft:enchantable/crossbow", "minecraft:crossbow"}
};
for (String[] check : memberships) {
    net.minecraft.util.Identifier tagId = new net.minecraft.util.Identifier(check[0]);
    net.minecraft.registry.tag.TagKey<net.minecraft.item.Item> tag = net.minecraft.registry.tag.TagKey.of(net.minecraft.registry.RegistryKeys.ITEM, tagId);
    int size = net.minecraft.registry.Registries.ITEM.getEntryList(tag).map(entries -> entries.size()).orElse(0);
    sizes.addProperty(check[0], size);
    net.minecraft.item.Item item = net.minecraft.registry.Registries.ITEM.getOrEmpty(new net.minecraft.util.Identifier(check[1])).orElse(null);
    if (item == null || !item.getDefaultStack().isIn(tag)) failures.add(check[0] + " does not contain " + check[1]);
}
// A nested early-tier chisel tag must not accidentally make iron qualify for later tiers.
if (net.jukoz.me.item.ToolItemsME.IRON_CHISEL.getDefaultStack().isIn(net.jukoz.me.utils.ItemTagsME.LATE_CHISELS)) {
    failures.add("late_chisels incorrectly accepts iron_chisel");
}
if (!net.jukoz.me.item.utils.ToolMaterialsME.MITHRIL.getRepairIngredient().test(net.jukoz.me.item.ResourceItemsME.MITHRIL_INGOT.getDefaultStack())) {
    failures.add("mithril tool repair ingredient is not usable");
}
if (!net.jukoz.me.item.utils.armor.ArmorMaterialsME.LEATHER_T1.material().getRepairIngredient().test(net.minecraft.item.Items.LEATHER.getDefaultStack())) {
    failures.add("leather armor repair ingredient is not usable");
}
report.addProperty("membership_checks", memberships.length);
report.add("tag_sizes", sizes);
report.add("failures", new com.google.gson.Gson().toJsonTree(failures));
java.nio.file.Files.writeString(java.nio.file.Paths.get("../build/tags-smoke.json"), new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(report));
if (!failures.isEmpty()) throw new AssertionError(report.toString());
return report.toString();
