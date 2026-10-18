package net.jukoz.me.compat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * 1.21.2's dyed-colour component.
 *
 * <p>1.20.1 has no such component: dyeability comes from implementing
 * {@link net.minecraft.item.DyeableItem}, and the colour lives in
 * {@code display.color} NBT. The 1.21 tree instead declares a default colour on the
 * item settings. This class carries that default through the MEComponents shim, and
 * {@link net.jukoz.me.mixin.DyeableItemMixin} makes DyeableItem read it.
 */
public record DyedColorComponent(int rgb, boolean showInTooltip) {
    public static final Codec<DyedColorComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("rgb").forGetter(DyedColorComponent::rgb),
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(DyedColorComponent::showInTooltip)
    ).apply(instance, DyedColorComponent::new));

    public DyedColorComponent(int rgb) {
        this(rgb, true);
    }
}
