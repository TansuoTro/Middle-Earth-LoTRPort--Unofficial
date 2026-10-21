package net.jukoz.me.datageneration.content;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.jukoz.me.MiddleEarth;
import net.minecraft.resources.ResourceLocation;
import java.util.Optional;
import net.minecraft.client.model.Model;

public class CustomItemModels {
    public static final Model BIG_WEAPON = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon")), Optional.empty(), TextureSlot.LAYER0);
    public static final Model BIG_WEAPON_STAFF = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon_staff")), Optional.empty(), TextureSlot.LAYER0);
    public static final Model BIG_WEAPON_BLOCKING = new Model(Optional.of(
            MiddleEarth.of("item/big_weapon_blocking")), Optional.of("_blocking"), TextureSlot.LAYER0);
    public static final Model BOW = new Model(Optional.of(
            MiddleEarth.of("item/bow")), Optional.empty(), TextureSlot.LAYER0);
    public static final Model LONGBOW = new Model(Optional.of(
            MiddleEarth.of("item/longbow")), Optional.empty(), TextureSlot.LAYER0);
    public static final Model DAGGER_STRIKE = new Model(Optional.of(
            MiddleEarth.of("item/dagger_strike")), Optional.empty(), TextureSlot.LAYER0);

    public static final Model CROSSBOW = new Model(Optional.of(
            new ResourceLocation("item/crossbow")), Optional.empty(), TextureSlot.LAYER0);

    /** 1.20.1 has no {@code ModelTemplates.TEMPLATE_SPAWN_EGG}; the parent is enough. */
    public static final Model TEMPLATE_SPAWN_EGG = new Model(Optional.of(
            new ResourceLocation("item/template_spawn_egg")), Optional.empty());
}
