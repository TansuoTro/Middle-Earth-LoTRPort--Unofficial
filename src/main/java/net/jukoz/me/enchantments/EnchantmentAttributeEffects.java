package net.jukoz.me.enchantments;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;

/** Equipment effects from the donor's committed enchantment JSON, including NPC equipment. */
public final class EnchantmentAttributeEffects {
    private static final UUID MINER_REACH = UUID.nameUUIDFromBytes("me:enchantment.miner_reach".getBytes(StandardCharsets.UTF_8));
    private static final UUID STRIDE = UUID.nameUUIDFromBytes("me:enchantment.stride".getBytes(StandardCharsets.UTF_8));
    private EnchantmentAttributeEffects() { }

    public static int level(RegistryKey<Enchantment> key, ItemStack stack) {
        Enchantment enchantment = EnchantmentsME.get(key);
        return enchantment == null ? 0 : EnchantmentHelper.getLevel(enchantment, stack);
    }

    public static void apply(LivingEntity entity) {
        ItemStack hand = entity.getMainHandStack();
        int grounded = 0, stalwart = 0;
        for (ItemStack armor : entity.getArmorItems()) {
            grounded += level(EnchantmentsME.GROUNDED, armor);
            stalwart += level(EnchantmentsME.STALWART, armor);
        }
        setBonus(entity, EntityAttributes.GENERIC_ATTACK_SPEED, EnchantmentsME.CELERITY_MODIFIER_ID,
                "me:enchantment.celerity", level(EnchantmentsME.CELERITY, hand) * 0.2);
        setBonus(entity, EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), EnchantmentsME.VANTAGE_MODIFIER_ID,
                "me:enchantment.vantage", level(EnchantmentsME.VANTAGE, hand) * 0.5);
        setBonus(entity, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, EnchantmentsME.GROUNDED_MODIFIER_ID,
                "me:enchantment.grounded", grounded * 0.025);
        setBonus(entity, EntityAttributes.GENERIC_ARMOR, EnchantmentsME.STALWART_MODIFIER_ID,
                "me:enchantment.stalwart", stalwart);
        int miner = level(EnchantmentsME.MINER_REACH, entity.getEquippedStack(EquipmentSlot.CHEST));
        setBonus(entity, EntityAttributesME.BLOCK_INTERACTION_RANGE.value(), MINER_REACH,
                "me:enchantment.miner_reach", miner == 0 ? 0 : 1.0 + (miner - 1) * 0.5);
        setBonus(entity, EntityAttributes.GENERIC_MOVEMENT_SPEED, STRIDE,
                "me:enchantment.stride", level(EnchantmentsME.STRIDE, entity.getEquippedStack(EquipmentSlot.LEGS)) * 0.005);
    }

    private static void setBonus(LivingEntity entity, EntityAttribute attribute, UUID id, String name, double value) {
        var instance = entity.getAttributeInstance(attribute);
        if (instance == null) return;
        var existing = instance.getModifier(id);
        if (existing != null && existing.getValue() == value) return;
        instance.removeModifier(id);
        if (value != 0.0) instance.addTemporaryModifier(new EntityAttributeModifier(id, name, value, EntityAttributeModifier.Operation.ADDITION));
    }
}
