package net.jukoz.me.entity.spider.scuttler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.jukoz.me.entity.spider.MirkwoodSpiderVariants;

@Environment(EnvType.CLIENT)
public class ShelobiteScuttlerEntityRenderState extends LivingEntityRenderState {
    public MirkwoodSpiderVariants variant;

    public ShelobiteScuttlerEntityRenderState() {
        variant = MirkwoodSpiderVariants.BLACK;
        ;
    }
}