package net.jukoz.me.particles.custom;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class BiomeFogParticle extends TextureSheetParticle {

    private final BlockPos spawnPos;

    BiomeFogParticle(ClientLevel clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
        this.scale(16.0F);
        this.setAlpha(0.0F);

        this.spawnPos = new BlockPos((int) d, (int) e, (int) f);

        this.velocityX = velocityX + (double)(this.random.nextFloat() / 750.0F);
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    public ParticleRenderType getType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getBrightness(float tint) {
        return (int)(255.0F * getFadeAmount(this.getLifetimeProgress((float)this.age + tint), 0.1F, 0.3F));
    }

    public void tick() {
        if (this.spawnPos.getY() > world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.spawnPos.getX(), this.spawnPos.getZ()) + 1){
            this.markDead();
        }

        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;

        if (this.alpha < 0.15F){
            this.setAlpha(getFadeAmount(this.getLifetimeProgress((float)this.age), 0.3F, 0.5F));
        }

        if (this.age++ < this.maxAge) {
            this.velocityX -= 0.00005F;
            this.velocityY += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.velocityZ += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.move(this.velocityX, this.velocityY, this.velocityZ);
        } else {
            this.markDead();
        }
    }

    private float getLifetimeProgress(float age) {
        return Mth.clamp(age / this.maxAge, 0.0F, 1.0F);
    }

    private static float getFadeAmount(float lifetimeProgress, float fadeIn, float fadeOut) {
        if (lifetimeProgress >= 1.0F - fadeIn) {
            return (1.0F - lifetimeProgress) / fadeIn;
        } else {
            return lifetimeProgress <= fadeOut ? lifetimeProgress / fadeOut : 1.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
            BiomeFogParticle biomeFogParticle = new BiomeFogParticle(clientWorld, d, e, f, g, h, i);
            biomeFogParticle.setMaxAge(clientWorld.random.nextBetween(300, 400));
            biomeFogParticle.setSprite(this.spriteProvider);
            return biomeFogParticle;
        }
    }
}
