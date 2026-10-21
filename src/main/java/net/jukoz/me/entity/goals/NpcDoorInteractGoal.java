package net.jukoz.me.entity.goals;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.entity.ai.goal.LongDoorInteractGoal;
import net.minecraft.world.entity.Mob;
import net.jukoz.me.block.registration.ModBlocks;

public class NpcDoorInteractGoal extends LongDoorInteractGoal {
    public NpcDoorInteractGoal(Mob mob, boolean delayedClose) {
        super(mob, delayedClose);
    }

    @Override
    protected void setDoorOpen(boolean open) {
        BlockState blockState = this.mob.getWorld().getBlockState(this.doorPos);
        if(blockState.isOf(ModBlocks.TREATED_STEEL_DOOR))
            return;
        super.setDoorOpen(open);
    }
}
