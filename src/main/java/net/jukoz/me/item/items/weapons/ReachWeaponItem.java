package net.jukoz.me.item.items.weapons;
import net.jukoz.me.item.MEComponents;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
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

    public ReachWeaponItem(ToolMaterial toolMaterial, WeaponTypesME type, Item.Settings settings) {
        super(ItemSettingsME.createWeaponSettings(toolMaterial, settings, type)
                .component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(type.name)));
        this.type = type;
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
