package net.jukoz.me.block.special.plate;

import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

import java.util.HashMap;

public class PlateFoodModels {
    public static HashMap<ResourceLocation, ResourceLocation> plateModels;

    public static ResourceLocation getPlateIdentifier(ResourceLocation itemIdentifier) {
        if(plateModels.containsKey(itemIdentifier)) {
            return plateModels.get(itemIdentifier);
        }
        return itemIdentifier;
    }

    public static void addVanillaModel(String name) {
        plateModels.put(new ResourceLocation("minecraft", name), new ResourceLocation(MiddleEarth.MOD_ID, "plate_" + name));
    }
    public static void addModel(String name) {
        plateModels.put(MiddleEarth.of(name), MiddleEarth.of('_', "plate", name));
    }

    private static void initMap() {
        plateModels = new HashMap<>();
        addVanillaModel("apple");
        addVanillaModel("bowl");
        addVanillaModel("pumpkin_pie");
        addVanillaModel("rabbit_stew");
        addVanillaModel("beetroot_soup");
        addVanillaModel("melon_slice");
        addVanillaModel("mushroom_stew");
        addVanillaModel("suspicious_stew");
        addModel("cram");
        addModel("berry_pie");
        addModel("meat_skewer");
        addModel("cooked_meat_skewer");
        addModel("vegetable_skewer");
        addModel("cooked_vegetable_skewer");
        addModel("tomato");
    }

    static {
        initMap();
    }
}
