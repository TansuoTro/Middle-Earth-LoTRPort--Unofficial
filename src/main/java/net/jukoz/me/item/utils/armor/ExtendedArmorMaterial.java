package net.jukoz.me.item.utils.armor;

import net.minecraft.world.item.ArmorMaterial;

public record ExtendedArmorMaterial(ArmorMaterial material, int durabilityModifier, ArmorMaterialsME.Tiers tier) {
}
