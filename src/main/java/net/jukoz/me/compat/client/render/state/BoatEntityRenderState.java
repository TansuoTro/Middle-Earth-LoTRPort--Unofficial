package net.jukoz.me.compat.client.render.state;

/**
 * 1.20.1 stand-in for 1.21.8
 * {@code net.jukoz.me.compat.client.render.state.BoatEntityRenderState}. Only the members
 * the donor {@code BarrelEntityRenderer} reads are declared.
 */
public class BoatEntityRenderState extends EntityRenderState {
    public boolean submergedInWater;
    public float bubbleWobble;
    public float damageWobbleTicks;
    public float damageWobbleStrength;
    public int damageWobbleSide;
    public float leftPaddleAngle;
    public float rightPaddleAngle;
}
