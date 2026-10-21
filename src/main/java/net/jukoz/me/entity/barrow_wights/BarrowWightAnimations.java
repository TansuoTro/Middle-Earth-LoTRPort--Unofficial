package net.jukoz.me.entity.barrow_wights;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;

public class BarrowWightAnimations {
    public static final AnimationDefinition anim_scream = AnimationDefinition.Builder.create(1.5f)
            .addBoneAnimation("head",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.createRotationalVector(-5f, 20f, 8f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("rightArm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.createRotationalVector(-20f, 0f, 10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.createRotationalVector(-20f, 0f, 20f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.createRotationalVector(-20f, 0f, 10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-20f, 0f, 20f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.createRotationalVector(-20f, 0f, 10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("leftArm",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.25f, KeyframeAnimations.createRotationalVector(-20f, 0f, -10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.createRotationalVector(-20f, 0f, -20f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.75f, KeyframeAnimations.createRotationalVector(-20f, 0f, -10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(-20f, 0f, -20f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.25f, KeyframeAnimations.createRotationalVector(-20f, 0f, -10f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1.5f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addBoneAnimation("bottomJaw",
                    new AnimationChannel(AnimationChannel.Targets.ROTATE,
                            new Keyframe(0f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(0.5f, KeyframeAnimations.createRotationalVector(32f, 6f, 12.5f),
                                    AnimationChannel.Interpolations.LINEAR),
                            new Keyframe(1f, KeyframeAnimations.createRotationalVector(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
