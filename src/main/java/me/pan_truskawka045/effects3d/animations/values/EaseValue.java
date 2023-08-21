package me.pan_truskawka045.effects3d.animations.values;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.EaseFunction;
import me.pan_truskawka045.effects3d.animations.EaseFunctions;

@RequiredArgsConstructor
public final class EaseValue {
    private final float startValue;
    private final float target;
    private final EaseFunction easeFunction;

    public float getValue(float f) {
        return startValue + (target - startValue) * easeFunction.ease(f);
    }


    public static EaseValue of(float startValue, float target, EaseFunction easeFunction) {
        return new EaseValue(startValue, target, easeFunction);
    }

    public static EaseValue linear(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.LINEAR);
    }

    public static EaseValue easeIn(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.EASE_IN);
    }

    public static EaseValue easeOut(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.EASE_OUT);
    }


}