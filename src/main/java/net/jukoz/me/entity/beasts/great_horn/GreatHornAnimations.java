package net.jukoz.me.entity.beasts.great_horn;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.AnimationChannel;

public class GreatHornAnimations {
    //region WALK
    public static final AnimationDefinition WALK = AnimationDefinition.Builder.create(2.0F).looping()
            .addBoneAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("body", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5417F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5417F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -0.5F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.4167F, KeyframeAnimations.createRotationalVector(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.3333F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.4167F, KeyframeAnimations.createTranslationalVector(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.73F, -2.94F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.3333F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.625F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.3333F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.4583F, KeyframeAnimations.createRotationalVector(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.73F, -2.94F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.3333F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.625F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.4583F, KeyframeAnimations.createTranslationalVector(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.73F, -2.94F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.375F, KeyframeAnimations.createRotationalVector(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.7083F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5417F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.375F, KeyframeAnimations.createTranslationalVector(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.3333F, KeyframeAnimations.createRotationalVector(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.6667F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.3333F, KeyframeAnimations.createTranslationalVector(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5417F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -2.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 2.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5417F, KeyframeAnimations.createTranslationalVector(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5417F, KeyframeAnimations.createTranslationalVector(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("beard", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5417F, KeyframeAnimations.createRotationalVector(-2.0F, 0.0F, 0.0714F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5417F, KeyframeAnimations.createRotationalVector(-2.0F, 0.0F, 0.0714F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .addBoneAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                    new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(2.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .build();
    // endregion

    // region IDLE
    public static final AnimationDefinition EAR_WIGGLE = AnimationDefinition.Builder.create(3.0F)
            .addBoneAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.9583F, KeyframeAnimations.createRotationalVector(10.1692F, 25.5711F, 13.9439F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.125F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.2917F, KeyframeAnimations.createRotationalVector(10.1692F, 25.5711F, 13.9439F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.4583F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(3.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .addBoneAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                    new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(0.9583F, KeyframeAnimations.createRotationalVector(10.1692F, -25.5711F, -13.9439F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.125F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.2917F, KeyframeAnimations.createRotationalVector(10.1692F, -25.5711F, -13.9439F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(1.4583F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                    new Keyframe(3.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
            ))
            .build();

        public static final AnimationDefinition IDLE = AnimationDefinition.Builder.create(3.0F).looping()
                .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(3.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_body", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(3.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_body", new AnimationChannel(AnimationChannel.Targets.SCALE,
                        new Keyframe(0.0F, KeyframeAnimations.createScalingVector(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(1.5F, KeyframeAnimations.createScalingVector(1.0F, 1.0F, 1.05F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(3.0F, KeyframeAnimations.createScalingVector(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .build();
    // endregion

    // region GALLOP
        public static final AnimationDefinition GALLOP = AnimationDefinition.Builder.create(0.75F).looping()
                .addBoneAnimation("root", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.3333F, KeyframeAnimations.createRotationalVector(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.5F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("root", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.3333F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.25F, KeyframeAnimations.createTranslationalVector(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.25F, KeyframeAnimations.createTranslationalVector(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-62.4474F, -0.0884F, 0.5044F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-62.4474F, -0.0884F, 0.5044F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.2083F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createTranslationalVector(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-62.4474F, -0.0884F, 0.5044F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-62.4474F, -0.0884F, 0.5044F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.2083F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createTranslationalVector(0.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 1.0F, -1.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.3333F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.TRANSLATE,
                        new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createTranslationalVector(0.0F, -0.5F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4167F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("beard", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATE,
                        new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.4583F, KeyframeAnimations.createRotationalVector(20.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                        new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .build();
    // endregion
        
    // region BOW
    public static final AnimationDefinition BOW = AnimationDefinition.Builder.create(4.0F)
		.addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE, 
			new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 2.5F, 0.5F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(0.75F, KeyframeAnimations.createRotationalVector(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("back_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE, 
			new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 1.0F, -2.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, -4.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createRotationalVector(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_left_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE, 
			new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(0.75F, KeyframeAnimations.createTranslationalVector(0.0F, 4.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createRotationalVector(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_right_leg", new AnimationChannel(AnimationChannel.Targets.TRANSLATE, 
			new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createTranslationalVector(0.0F, 6.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createRotationalVector(47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("beard", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_half", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createRotationalVector(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("front_half", new AnimationChannel(AnimationChannel.Targets.TRANSLATE, 
			new Keyframe(0.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createTranslationalVector(0.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(2.5F, KeyframeAnimations.createTranslationalVector(0.0F, -6.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createTranslationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("ear_left", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(36.6611F, 8.8837F, 33.0832F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .addBoneAnimation("ear_right", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(1.5F, KeyframeAnimations.createRotationalVector(36.6611F, -8.8837F, -33.0832F), AnimationChannel.Interpolations.CUBIC),
                new Keyframe(4.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CUBIC)
                ))
                .build();
    // endregion
    
    // region ATTACK
    public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.create(0.8333F)
		.addBoneAnimation("head_neck", new AnimationChannel(AnimationChannel.Targets.ROTATE, 
			new Keyframe(0.0F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.0833F, KeyframeAnimations.createRotationalVector(54.06F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.2083F, KeyframeAnimations.createRotationalVector(95.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.createRotationalVector(28.33F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4167F, KeyframeAnimations.createRotationalVector(-5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.createRotationalVector(-12.16F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.6667F, KeyframeAnimations.createRotationalVector(-13.52F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.8333F, KeyframeAnimations.createRotationalVector(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
            ))
            .build();
    // endregion
}