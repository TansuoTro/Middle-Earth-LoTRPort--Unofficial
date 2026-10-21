package net.jukoz.me.entity.spider.larva;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;

public class ShelobiteLarvaAnimations {

    // region WALK

    public static final AnimationDefinition SHELOBITE_LARVA_WALK = AnimationDefinition.Builder.create(1f).looping()
            .addBoneAnimation("leg_left_front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.33333f, KeyframeAnimations.createRotationalVector(0f, -15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_center_front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.29167f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(0f, -15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_center_back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.33333f, KeyframeAnimations.createRotationalVector(0f, -15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_left_back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.29167f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(0f, -15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-37.23f, -9.24f, -30.47f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.29167f, KeyframeAnimations.createRotationalVector(0f, -2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_center_front",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.33333f, KeyframeAnimations.createRotationalVector(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_center_back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.29167f, KeyframeAnimations.createRotationalVector(0f, -2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-14.73f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC)))
            .addBoneAnimation("leg_right_back",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.33333f, KeyframeAnimations.createRotationalVector(0f, 15f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.70833f, KeyframeAnimations.createRotationalVector(-37.23f, 9.24f, 30.47f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 2.5f, 0f),
                                    AnimationChannel.Interpolations.CUBIC))).build();
    // endregion
    
    // region BITE
    public static final AnimationDefinition SHELOBITE_LARVA_BITE = AnimationDefinition.Builder.create(0.65f)
            .addBoneAnimation("root",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.createRotationalVector(-25f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.5f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("abdomen",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.createRotationalVector(-15f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.2916767f, KeyframeAnimations.createRotationalVector(20f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.625f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("core",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.createRotationalVector(-30f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.375f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("leg_left",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.createRotationalVector(25f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.375f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("leg_right",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.125f, KeyframeAnimations.createRotationalVector(25f, 0f, 0f),
                                    AnimationChannel.Interpolations.CUBIC),
                            new Keyframe(0.375f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    // endregion
}