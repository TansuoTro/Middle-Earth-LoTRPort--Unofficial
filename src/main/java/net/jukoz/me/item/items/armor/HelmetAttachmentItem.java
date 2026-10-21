package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class HelmetAttachmentItem extends Item{

    public HelmetAttachmentItem(Item.Properties settings, ExtendedArmorMaterial armorMaterial) {
        super(settings.maxCount(1));
    }

    public static void toggleHelmetAttachmentState(ServerPlayer player, ItemStack stack){
        HelmetAttachmentDataComponent helmetAttachmentDataComponent = MEComponents.get(stack, MEComponents.HELMET_ATTACHMENT_DATA);
        if (helmetAttachmentDataComponent != null){
            if (helmetAttachmentDataComponent.below() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null) {
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendSystemMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!helmetAttachmentDataComponent.below() && helmetAttachmentDataComponent.getHelmetAttachment().getConstantState() == null){
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachmentDataComponent.helmetAttachment(), helmetAttachmentDataComponent.helmetAttachmentColor()));
                player.sendSystemMessage(Component.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
