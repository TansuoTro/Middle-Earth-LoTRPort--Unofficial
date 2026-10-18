package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Consumer;

public class DoorBlockItem extends BlockItem {
    private final LargeDoorBlock block;

    public DoorBlockItem(LargeDoorBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip." + MiddleEarth.MOD_ID + ".door_size").append(block.getDoorWidth() + "x" + block.getDoorHeight()));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
