package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import java.util.List;
import java.util.Random;
import net.minecraft.util.RandomSource;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {

    @ModifyArg(method = "grow", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
    public int grow(int index, @Local(ordinal = 0) List<ConfiguredFeature<?, ?>> flowers) {
        RandomSource random = new RandomSource();
        return random.nextInt(flowers.size());
    }
}
