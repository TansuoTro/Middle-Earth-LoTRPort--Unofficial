package net.jukoz.me.block.special.doors;

import net.jukoz.me.block.special.LargeDoorBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class LargeDoor4x2 extends LargeDoorBlock {
    public static final IntegerProperty PART = IntegerProperty.of("part", 0, 7);

    public LargeDoor4x2(Settings settings) {
        super(settings);
        this.doorHeight = 4;
        this.doorWidth  = 2;
    }

    @Override
    public IntegerProperty getPart() {
        return PART;
    }
}
