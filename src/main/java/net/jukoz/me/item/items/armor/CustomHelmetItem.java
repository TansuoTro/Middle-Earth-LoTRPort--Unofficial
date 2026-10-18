package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ArmorItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;

public class CustomHelmetItem extends ArmorItem {

    public CustomHelmetItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.armor(material.material(), ArmorItem.Type.HELMET).maxCount(1));
    }

    public static void toggleHoodState(ServerPlayerEntity player, ItemStack stack){
        HelmetAttachmentDataComponent hoodDataComponent = MEComponents.get(stack, MEComponents.HELMET_ATTACHMENT_DATA);
        if (hoodDataComponent != null){
            if (hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null) {
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_up"), true);
            } else if (!hoodDataComponent.down() && hoodDataComponent.getHelmetAttachment().getConstantState() == null){
                MEComponents.set(stack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, hoodDataComponent.helmetAttachment(), hoodDataComponent.helmetAttachmentColor()));
                player.sendMessage(Text.translatable("alert." + MiddleEarth.MOD_ID + ".hood_down"), true);
            }
        }
    }
}
