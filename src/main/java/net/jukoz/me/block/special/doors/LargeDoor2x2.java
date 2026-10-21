package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LargeDoor2x2 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.of("part", 0, 3);

    public LargeDoor2x2(Settings settings) {
        super(settings);
        this.doorHeight = 2;
        this.doorWidth  = 2;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}
