package net.jukoz.me.entity.spider.spawn;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.AnimationState;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerRenderState;

@OnlyIn(Dist.CLIENT)
public class SpawnOfShelobRenderState extends ShelobiteScuttlerRenderState {
    public final AnimationState blockAnimationState = new AnimationState();

    public SpawnOfShelobRenderState() {

    }
}
