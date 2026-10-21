package net.jukoz.me.entity.beasts;

import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

public class BeastEntityNavigation extends MobNavigation {
    public BeastEntityNavigation(Mob mobEntity, Level world) {
        super(mobEntity, world);
    }

    @Override
    protected boolean isAtValidPosition() {
        return super.isAtValidPosition() && !((AbstractBeastEntity)entity).isSitting();
    }
}
