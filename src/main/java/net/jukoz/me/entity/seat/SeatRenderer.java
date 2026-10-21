package net.jukoz.me.entity.seat;

import net.jukoz.me.compat.client.render.MEEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SeatRenderer extends MEEntityRenderer<SeatEntity, SeatEntityRenderState> {

    public SeatRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public SeatEntityRenderState createRenderState() {
        return new SeatEntityRenderState();
    }
}
