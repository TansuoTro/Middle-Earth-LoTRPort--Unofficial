package net.jukoz.me.entity.seat;
import net.jukoz.me.compat.client.render.MEEntityRenderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;

public class SeatRenderer extends MEEntityRenderer<SeatEntity, SeatEntityRenderState> {

    public SeatRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public SeatEntityRenderState createRenderState() {
        return new SeatEntityRenderState();
    }
}
