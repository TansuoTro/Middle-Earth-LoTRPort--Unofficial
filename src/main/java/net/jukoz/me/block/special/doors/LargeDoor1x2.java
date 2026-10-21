package net.jukoz.me.block.special.doors;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LargeDoor1x2 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);

    public LargeDoor1x2(BlockBehaviour.Properties settings) {
        super(settings);
        this.doorHeight = 1;
        this.doorWidth  = 2;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}
