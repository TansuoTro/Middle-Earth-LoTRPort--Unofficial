package net.jukoz.me.api.utils;

import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.api.SevenStarsApi;

public class IdentifierUtil {
    public static ResourceLocation getIdentifierFromString(String id){
        if(id == null)
            return null;
        if(id.contains(":") && id.split(":").length == 2){
            return new ResourceLocation(id.split(":")[0], id.split(":")[1]);
        }
        return new ResourceLocation(SevenStarsApi.MOD_ID, id + "_error");
    }

    public static ResourceLocation build(String key, String name) {
        return new ResourceLocation(key, name);
    }

    public static ResourceLocation ofVanilla(String name) {
        return new ResourceLocation(name);
    }

    public static ResourceLocation buildAggregate(String key, String... names) {
        return build(key, createAggregateValue('.', names));
    }

    public static String createAggregateValue(char character, String... names){
        if(names.length == 0)
            return "not_enough_parameters";
        if(names.length == 1)
            return names[0];

        StringBuilder fullValue = new StringBuilder();
        for(int i = 0; i < names.length; i++){
            fullValue.append(names[i]);
            if(i < names.length - 1)
                fullValue.append(character);
        }
        return fullValue.toString();
    }
}
