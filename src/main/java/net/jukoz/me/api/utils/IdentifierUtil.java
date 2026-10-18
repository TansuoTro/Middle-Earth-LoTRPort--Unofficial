package net.jukoz.me.api.utils;

import net.minecraft.util.Identifier;
import net.jukoz.me.api.SevenStarsApi;

public class IdentifierUtil {
    public static Identifier getIdentifierFromString(String id){
        if(id == null)
            return null;
        if(id.contains(":") && id.split(":").length == 2){
            return new Identifier(id.split(":")[0], id.split(":")[1]);
        }
        return new Identifier(SevenStarsApi.MOD_ID, id + "_error");
    }

    public static Identifier build(String key, String name) {
        return new Identifier(key, name);
    }

    public static Identifier ofVanilla(String name) {
        return new Identifier(name);
    }

    public static Identifier buildAggregate(String key, String... names) {
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
