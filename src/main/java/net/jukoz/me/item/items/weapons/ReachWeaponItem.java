package net.jukoz.me.item.items.weapons;
import com.google.common.collect.Multimap;
import net.jukoz.me.item.MEComponents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;
import net.jukoz.me.item.utils.ItemSettingsME;

public class ReachWeaponItem extends Item {

    public static final Identifier ENTITY_INTERACTION_RANGE_MODIFIER_ID = new Identifier(MiddleEarth.MOD_ID, "entity_interaction_range");

    public WeaponTypesME type;

    private final ToolMaterial material;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public ReachWeaponItem(ToolMaterial toolMaterial, WeaponTypesME type, Item.Settings settings) {
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
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
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

    public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user) {
        return !(user instanceof net.minecraft.entity.player.PlayerEntity player && player.isCreative());
    }

    @Override
    public Text getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);
        }
        return super.getName(stack);
    }
}
