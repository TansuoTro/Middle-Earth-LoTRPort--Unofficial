package net.jukoz.me.exceptions;

import net.jukoz.me.MiddleEarth;

public class SpawnIdentifierException extends Exception{
    public static final String KEY = "exception.%s.spawn_identifier".formatted(MiddleEarth.MOD_ID);

}
