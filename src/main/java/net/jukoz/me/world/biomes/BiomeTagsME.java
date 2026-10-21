package net.jukoz.me.world.biomes;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;

public class BiomeTagsME {
    public static TagKey<Biome> SPRING = TagKey.create(Registries.BIOME, new ResourceLocation(MiddleEarth.MOD_ID, "is_spring"));
    public static TagKey<Biome> AUTUMN = TagKey.create(Registries.BIOME, new ResourceLocation(MiddleEarth.MOD_ID, "is_autumn"));
    public static TagKey<Biome> WINTER = TagKey.create(Registries.BIOME, new ResourceLocation(MiddleEarth.MOD_ID, "is_winter"));
    public static TagKey<Biome> DEAD = TagKey.create(Registries.BIOME, new ResourceLocation(MiddleEarth.MOD_ID, "is_dead"));
}
