package net.jukoz.me.world.biomes;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.jukoz.me.MiddleEarth;

public class BiomeTagsME {
    public static TagKey<Biome> SPRING = TagKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, "is_spring"));
    public static TagKey<Biome> AUTUMN = TagKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, "is_autumn"));
    public static TagKey<Biome> WINTER = TagKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, "is_winter"));
    public static TagKey<Biome> DEAD = TagKey.of(RegistryKeys.BIOME, new Identifier(MiddleEarth.MOD_ID, "is_dead"));
}
