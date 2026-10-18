package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.server.world.ServerWorld;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import org.jetbrains.annotations.Nullable;

public class CustomChestplateItem extends ArmorItem {

    public CustomChestplateItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), ArmorItem.Type.CHESTPLATE).maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        super.inventoryTick(stack, world, entity, slot);
        BackAttachmentDataComponent backAttachmentDataComponent = MEComponents.get(stack, MEComponents.BACK_ATTACHMENT_DATA);
        if(backAttachmentDataComponent != null) {
            int id = backAttachmentDataComponent.getBackAttachment().getId();
            if(id == BackAttachmentsME.MANTLE_OF_YAVANNA.getId()) {
                MantleOfYavannaItem.applyBackAttachmentColor(stack, world, entity, backAttachmentDataComponent);
            }
        }
    }
}
