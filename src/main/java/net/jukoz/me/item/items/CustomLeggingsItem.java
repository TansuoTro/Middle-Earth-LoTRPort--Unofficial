package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.utils.ModArmorMaterials;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CustomLeggingsItem extends ArmorItem implements DyeableLeatherItem {

    private ModArmorMaterials material;
    private List<CustomBootsItem.Customizations> customsList;

    public CustomLeggingsItem(ModArmorMaterials material, Type type, Settings settings, List<CustomBootsItem.Customizations> customsList) {
        super(material, type, settings);

        this.material = material;
        this.customsList = customsList;
    }

    public CustomLeggingsItem(ModArmorMaterials material, Type type, Settings settings) {
        super(material, type, settings);

        this.material = material;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {

        tooltip.add(Component.of(""));
        if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(material.getFaction()));
            if(material.getSubFaction() != null){
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(material.getSubFaction()));
            }
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".tier" + this.material.getTier()));
            tooltip.add(Component.of(""));
        } else {
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        if (this.customsList != null) {
            if (Screen.hasAltDown()) {
                tooltip.add(Component.of(""));
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".customizations"));
            } else {
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".alt"));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    enum Customizations{
        DYEABLE("dyeable"),
        ;

        public final String name;
        Customizations(String name){
            this.name = name;
        }
    }
}
