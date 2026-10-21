package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import org.jetbrains.annotations.Nullable;

public class CustomChestplateItem extends ArmorItem implements DyeableItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings) {
        super(material.material(), ArmorItem.Type.CHESTPLATE, settings.maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        BackAttachmentDataComponent backAttachmentDataComponent = MEComponents.get(stack, MEComponents.BACK_ATTACHMENT_DATA);
        if(backAttachmentDataComponent != null) {
            int id = backAttachmentDataComponent.getBackAttachment().getId();
            if(id == BackAttachmentsME.MANTLE_OF_YAVANNA.getId()) {
                MantleOfYavannaItem.applyBackAttachmentColor(stack, (net.minecraft.server.level.ServerLevel) world, entity, backAttachmentDataComponent);
            }
        }
    }
}
