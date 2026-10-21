package net.jukoz.me.world.features.tree.foliages;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.special.ModLeavesBlock;
import net.jukoz.me.world.gen.ModTreeGeneration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.TreeFeatureConfig;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class PalmFoliagePlacer extends FoliagePlacer {
    protected final int baseHeight;
    protected final IntProvider offset;
    protected final IntProvider baseRadius;
    protected final float acceleration;
    protected final float velocity;

    public static final Codec<PalmFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.intRange(0,16).fieldOf("baseHeight").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseHeight;
            }), IntProvider.createValidatingCodec(0, 16).fieldOf("baseRadius").forGetter((trunkPlacer) -> {
                return trunkPlacer.baseRadius;
            }), IntProvider.createValidatingCodec(-16, 16).fieldOf("offset").forGetter((trunkPlacer) -> {
                return trunkPlacer.offset;
            }), Codec.floatRange(-1.0f, 1.0f).fieldOf("acceleration").forGetter((trunkPlacer) -> {
                return trunkPlacer.acceleration;
            }), Codec.floatRange(-1.0f, 1.0f).fieldOf("velocity").forGetter((trunkPlacer) -> {
                return trunkPlacer.velocity;
            })).apply(instance, PalmFoliagePlacer::new));

    public PalmFoliagePlacer(int baseHeight, IntProvider baseRadius, IntProvider offset, float acceleration, float velocity) {
        super(baseRadius, offset);
        this.baseHeight = baseHeight;
        this.baseRadius = baseRadius;
        this.offset = offset;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return ModTreeGeneration.PALM_FOLIAGE;
    }

    @Override
    protected void generate(LevelSimulatedReader world, BlockPlacer placer, RandomSource random, TreeConfiguration config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int count = 6 + (int) (Math.random() * 2);
        double angle = (float) (360 / count);
        double offsetAngle = Math.random() * 20;

        for (int i = 0; i < count; i++) {
            createArcBranch(world, placer, random, mutable, config, treeNode.getCenter().add(0, offset, 0), this.baseHeight,
                    (angle * i) + offsetAngle, this.acceleration, this.velocity);
        }

        count--;
        angle = (float) (360 / count);
        offsetAngle += Math.random() * 20;
        for (int i = 0; i < count; i++) {
            createArcBranch(world, placer, random, mutable, config, treeNode.getCenter().add(0, offset, 0), this.baseHeight,
                    (angle * i) + offsetAngle, this.acceleration - 0.05f, this.velocity + 0.85f);
        }
    }

    protected void createArcBranch(LevelSimulatedReader world, BlockPlacer placer, RandomSource random, BlockPos.Mutable mutable,
                                                           TreeConfiguration config, BlockPos startPos, int height, double angle, float acceleration, float velocity) {
        if(height < 0) {
            height *= -1;
        }
        Vec3 dir = new Vec3(Math.cos(angle), 0, Math.sin(angle)).normalize();
        Vec3 currentPos = new Vec3(startPos.getX(), startPos.getY() - 1, startPos.getZ());
        Vec3 lastPos;
        float currentVel = velocity;

        for(int i = 0; i < height; i++) {
            lastPos = currentPos;
            currentPos = currentPos.add(new Vec3(dir.x, Math.max(-1, currentVel), dir.z));
            mutable.set(currentPos.x, currentPos.y, currentPos.z);
            placeFoliageBlock(world, placer, random, config, mutable);
            if(lastPos.getY() != currentPos.getY()) {
                mutable.set(currentPos.x, currentPos.y + 1, currentPos.z);
                placeFoliageBlock(world, placer, random, config, mutable);
            }
            currentVel += acceleration;
        }
    }

    @Override
    public int getRandomHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return 4;
    }

    @Override
    protected boolean isInvalidForLeaves(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx + dz >= ModLeavesBlock.MAX_DISTANCE;
    }
}
