package me.pan_truskawka045.effects3d.animations;

public class EaseFunctions {

    public static final EaseFunction LINEAR = f -> f;
    public static final EaseFunction EASE_IN = f -> f * f;
    public static final EaseFunction EASE_OUT = f -> f * (2 - f);


}
