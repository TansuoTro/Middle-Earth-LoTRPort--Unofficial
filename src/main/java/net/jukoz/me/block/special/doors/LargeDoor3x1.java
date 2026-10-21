package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LargeDoor3x1 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 2);

    public LargeDoor3x1(BlockBehaviour.Properties settings) {
        super(settings);
        this.doorHeight = 3;
        this.doorWidth  = 1;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}
