package net.jukoz.me.item.items.weapons;

import com.google.common.collect.Multimap;
import net.jukoz.me.item.MEComponents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;
import net.jukoz.me.item.utils.ItemSettingsME;

public class ReachWeaponItem extends Item {

    public static final ResourceLocation ENTITY_INTERACTION_RANGE_MODIFIER_ID = new ResourceLocation(MiddleEarth.MOD_ID, "entity_interaction_range");

    public WeaponTypesME type;

    private final Tier material;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public ReachWeaponItem(Tier toolMaterial, WeaponTypesME type, Item.Properties settings) {
        super(ItemSettingsME.createWeaponSettings(toolMaterial, settings, type)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
        this.material = toolMaterial;
        this.attributeModifiers = ItemSettingsME.createWeaponAttibutes(
                toolMaterial, type.attack, type.attackSpeed, type.attackRange);
    }

    /**
     * Donor's {@code attributeModifiers(createWeaponAttibutes(material, type.attack, type.attackSpeed,
     * type.attackRange))}: attack damage = type attack + material bonus, the type's attack speed and
     * entity-interaction-range modifier.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    /** Donor's {@code repairable(material.repairItems())}. */
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    /** Donor's {@code enchantable(material.enchantmentValue())}. */
    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    public boolean canMine(ItemStack stack, BlockState state, Level world, BlockPos pos, LivingEntity user) {
        return !(user instanceof net.minecraft.world.entity.player.Player player && player.isCreative());
    }

    @Override
    public Component getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Component.translatable(this.getTranslationKey()).formatted(ChatFormatting.GOLD);
        }
        return super.getName(stack);
    }
}
