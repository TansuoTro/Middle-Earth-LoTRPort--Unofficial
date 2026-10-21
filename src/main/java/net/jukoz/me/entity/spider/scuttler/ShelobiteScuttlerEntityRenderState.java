package net.jukoz.me.entity.spider.scuttler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.jukoz.me.entity.spider.MirkwoodSpiderVariants;

@OnlyIn(Dist.CLIENT)
public class ShelobiteScuttlerEntityRenderState extends LivingEntityRenderState {
    public MirkwoodSpiderVariants variant;

    public ShelobiteScuttlerEntityRenderState() {
        variant = MirkwoodSpiderVariants.BLACK;
        ;
    }
}
