package net.jukoz.me.datageneration.content.tags;

import net.minecraft.item.ArmorItem;

import java.util.ArrayList;
import java.util.List;

public class ArmorTags {
    public static List<ArmorItem> armors = new ArrayList<>();
    public static List<Object> basicArmors = new ArrayList<>();
    public static List<Object> lightArmors = new ArrayList<>();
    public static List<Object> mediumArmors = new ArrayList<>();
    public static List<Object> sturdyArmors = new ArrayList<>();
    public static List<Object> heavyArmors = new ArrayList<>();
    public static List<Object> headArmors = new ArrayList<>();
    public static List<Object> chestArmors = new ArrayList<>();
    public static List<Object> legArmors = new ArrayList<>();
    public static List<Object> footArmors = new ArrayList<>();
    public static List<Object> incompleteArmors = new ArrayList<>();

    /** Donor built the smithing-template recipe helper entries here; none are needed on 1.20.1 yet. */
    public static void registerRecipesTemplates() {
    }
}
