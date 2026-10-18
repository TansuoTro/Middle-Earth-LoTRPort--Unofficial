package net.jukoz.me.mixin;

import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Explosion.class)
public interface ExplosionAccessMixin {
    @Accessor("x")
    double me$getX();

    @Accessor("y")
    double me$getY();

    @Accessor("z")
    double me$getZ();

    @Accessor("power")
    float me$getPower();
}
