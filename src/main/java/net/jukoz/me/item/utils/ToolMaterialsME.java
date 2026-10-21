package net.jukoz.me.item.utils;

import net.jukoz.me.compat.SimpleToolMaterial;
import net.minecraft.world.item.Tier;
import net.jukoz.me.utils.ItemTagsME;

public class ToolMaterialsME {    // Mining levels replace the donor's incorrect_for_*_tool tags:
    // INCORRECT_FOR_WOODEN_TOOL -> 0, STONE -> 1, IRON -> 2, DIAMOND -> 3, NETHERITE -> 4.
    public static final Tier BONE = new SimpleToolMaterial(1, 150, 4.0F, 0.0F, 4, ItemTagsME.BONE_TOOL_MATERIALS);
    public static final Tier BRONZE = new SimpleToolMaterial(2, 200, 5.0f, 1.0f, 5, ItemTagsME.BRONZE_TOOL_MATERIALS);
    public static final Tier CRUDE = new SimpleToolMaterial(2, 200, 5.0f, 1.0f, 5, ItemTagsME.CRUDE_TOOL_MATERIALS);
    public static final Tier STEEL = new SimpleToolMaterial(3, 1561, 7.0f, 2.0f, 10, ItemTagsME.STEEL_TOOL_MATERIALS);
    public static final Tier BURZUM_STEEL = new SimpleToolMaterial(3, 1561, 7.0f, 2.0f, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);
    public static final Tier EDHEL_STEEL = new SimpleToolMaterial(3, 1561, 7.0f, 2.0f, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);
    public static final Tier KHAZAD_STEEL = new SimpleToolMaterial(3, 1561, 7.0f, 2.0f, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final Tier MITHRIL = new SimpleToolMaterial(4, 2031, 10.0f, 0.0f, 15, ItemTagsME.MITHRIL_TOOL_MATERIALS);    public static final Tier WEAVER_STING = new SimpleToolMaterial(1, 1561, 4.0f, 1.0f, 5, ItemTagsME.SPIDER_TOOL_MATERIALS);
    public static final Tier MORGUL_KNIFE = new SimpleToolMaterial(3, 2, 7.0f, 2.0f, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);    public static final Tier COPPER_HAMMER = new SimpleToolMaterial(0, 32, 7.0f, 2.0f, 10, ItemTagsME.COPPER_TOOL_MATERIALS);
    public static final Tier BRONZE_HAMMER = new SimpleToolMaterial(0, 128, 7.0f, 2.0f, 10, ItemTagsME.BRONZE_TOOL_MATERIALS);
    public static final Tier CRUDE_HAMMER = new SimpleToolMaterial(0, 128, 7.0f, 2.0f, 10, ItemTagsME.CRUDE_TOOL_MATERIALS);    public static final Tier STEEL_HAMMER = new SimpleToolMaterial(0, 256, 7.0f, 2.0f, 10, ItemTagsME.STEEL_TOOL_MATERIALS);
    public static final Tier NOBLE_STEEL_HAMMER = new SimpleToolMaterial(0, 512, 7.0f, 2.0f, 10, ItemTagsME.STEEL_TOOL_MATERIALS);    public static final Tier KHAZAD_STEEL_HAMMER = new SimpleToolMaterial(0, 256, 7.0f, 2.0f, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);
    public static final Tier KHAZAD_NOBLE_STEEL_HAMMER = new SimpleToolMaterial(0, 512, 7.0f, 2.0f, 10, ItemTagsME.KHAZAD_STEEL_TOOL_MATERIALS);    public static final Tier EDHEL_STEEL_HAMMER = new SimpleToolMaterial(0, 256, 7.0f, 2.0f, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);
    public static final Tier EDHEL_NOBLE_STEEL_HAMMER = new SimpleToolMaterial(0, 512, 7.0f, 2.0f, 10, ItemTagsME.EDHEL_STEEL_TOOL_MATERIALS);    public static final Tier BURZUM_STEEL_HAMMER = new SimpleToolMaterial(0, 256, 7.0f, 2.0f, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);
    public static final Tier BURZUM_NOBLE_STEEL_HAMMER = new SimpleToolMaterial(0, 512, 7.0f, 2.0f, 10, ItemTagsME.BURZUM_STEEL_TOOL_MATERIALS);    public static final Tier MITHRIL_HAMMER = new SimpleToolMaterial(0, 1024, 7.0f, 0.0f, 10, ItemTagsME.MITHRIL_TOOL_MATERIALS);
}
