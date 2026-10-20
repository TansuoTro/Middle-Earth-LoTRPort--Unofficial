package net.jukoz.me.mixin;

import net.jukoz.me.enchantments.EnchantmentsME;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.item.items.weapons.CustomDaggerWeaponItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
	@Inject(method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At(value = "HEAD"))
	private void dropItem0(ItemStack stack, boolean throwRandomly, boolean retainOwnership, CallbackInfoReturnable<ItemEntity> cir) {
		PlayerEntity player = (PlayerEntity) (Object) this;
	}

	/**
	 * Donor {@code PlayerEntityMixin#attack}: multiplies the attack damage by the first-strike
	 * (target above 90% health), dagger backstab and sneak-attack factors before it is applied.
	 * The injection point matches the donor exactly: the first float of {@code attack} right
	 * after {@code resetLastAttackedTicks()}.
	 */
	@ModifyVariable(method = "attack(Lnet/minecraft/entity/Entity;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V",
					shift = At.Shift.AFTER), ordinal = 0)
	private float me$modifyAttackDamage(float damage, Entity target) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		float newDamage = damage;
		ItemStack mainStack = player.getMainHandStack();

		Enchantment firstStrike = EnchantmentsME.get(EnchantmentsME.FIRST_STRIKE);
		if (firstStrike != null && EnchantmentHelper.getLevel(firstStrike, mainStack) > 0
				&& target instanceof LivingEntity livingEntity) {
			float healthRatio = livingEntity.getHealth() / livingEntity.getMaxHealth();
			if (healthRatio > 0.9f) {
				newDamage *= 1.5f;
			}
		}
		if (mainStack.getItem() instanceof CustomDaggerWeaponItem) {
			if (CustomDaggerWeaponItem.canBackStab(target, player)) {
				newDamage *= 1.75f;
			}
			if (CustomDaggerWeaponItem.canSneakAttack(mainStack)) {
				newDamage *= 1.5f;
			}
		}
		return newDamage;
	}

	/**
	 * Donor {@code EnchantmentsME} hands these bonuses to the enchantment data as
	 * {@code ATTRIBUTES} effects. 1.20.1 has no per-enchantment attribute list, so the same
	 * values are applied here as attribute modifiers on the player: celerity +0.2 attack
	 * speed, vantage +0.5 entity interaction range, grounded +0.025 knockback resistance,
	 * stalwart +1 armour — each per level and per piece, like the donor's
	 * {@code AttributeModifierSlot.MAINHAND} / {@code ARMOR} effects.
	 */
	@Inject(method = "tick", at = @At("HEAD"))
	private void me$applyEnchantmentAttributes(CallbackInfo ci) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		ItemStack mainStack = player.getMainHandStack();
		int celerity = me$level(EnchantmentsME.CELERITY, mainStack);
		int vantage = me$level(EnchantmentsME.VANTAGE, mainStack);
		int grounded = 0;
		int stalwart = 0;
		for (ItemStack armor : player.getArmorItems()) {
			grounded += me$level(EnchantmentsME.GROUNDED, armor);
			stalwart += me$level(EnchantmentsME.STALWART, armor);
		}

		me$setBonus(player, EntityAttributes.GENERIC_ATTACK_SPEED, EnchantmentsME.CELERITY_MODIFIER_ID,
				"me:enchantment.celerity", celerity * 0.2);
		me$setBonus(player, EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), EnchantmentsME.VANTAGE_MODIFIER_ID,
				"me:enchantment.vantage", vantage * 0.5);
		me$setBonus(player, EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, EnchantmentsME.GROUNDED_MODIFIER_ID,
				"me:enchantment.grounded", grounded * 0.025);
		me$setBonus(player, EntityAttributes.GENERIC_ARMOR, EnchantmentsME.STALWART_MODIFIER_ID,
				"me:enchantment.stalwart", stalwart * 1.0);
	}

	@Unique
	private static int me$level(RegistryKey<Enchantment> key, ItemStack stack) {
		Enchantment enchantment = EnchantmentsME.get(key);
		return enchantment == null ? 0 : EnchantmentHelper.getLevel(enchantment, stack);
	}

	@Unique
	private static void me$setBonus(PlayerEntity player, EntityAttribute attribute, UUID id, String name, double value) {
		EntityAttributeInstance instance = player.getAttributeInstance(attribute);
		if (instance == null) {
			return;
		}
		EntityAttributeModifier existing = instance.getModifier(id);
		double current = existing == null ? 0.0 : existing.getValue();
		if (current == value) {
			return;
		}
		instance.removeModifier(id);
		if (value != 0.0) {
			instance.addPersistentModifier(new EntityAttributeModifier(id, name, value,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
