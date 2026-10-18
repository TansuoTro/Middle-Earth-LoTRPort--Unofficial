package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class HelmetAttachmentItem extends Item{

    public HelmetAttachmentItem(Item.Settings settings, ExtendedArmorMaterial armorMaterial) {
        super(settings.armor(armorMaterial.material(), ArmorItem.Type.HELMET).maxCount(1));
    }

    public static void toggleHelmetAttachmentState(ServerPlayerEntity player, ItemStack stack){
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = MEComponents.get(stack, MEComponents.HELMET_ATTACHMENT_DATA);
        if (helmetAttachmentDataComponent != null){
            if (helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null) {
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!helmetAttachmentDataComponent.down() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null){
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
