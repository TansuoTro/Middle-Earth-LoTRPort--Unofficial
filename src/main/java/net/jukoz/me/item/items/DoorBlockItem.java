package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.function.Consumer;

public class DoorBlockItem extends BlockItem {
    private final LargeDoorBlock block;

    public DoorBlockItem(LargeDoorBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendTooltip(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".door_size").append(block.getDoorWidth() + "x" + block.getDoorHeight()));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
