package net.jukoz.me.exceptions;

import net.jukoz.me.MiddleEarth;

public class FactionIdentifierException extends Exception{
    public static final String KEY = "exception.%s.faction_identifier".formatted(MiddleEarth.MOD_ID);

}
