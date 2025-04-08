package me.pan_truskawka045.effects3d.animations;

public class EaseFunctions {


    /**
     * Linear ease function
     * Defined by:
     * <pre>f(x) = x</pre>
     */
    public static final EaseFunction LINEAR = f -> f;
    /**
     * Quadratic ease-in function
     * Defined by:
     * <pre>f(x) = x^2</pre>
     */
    public static final EaseFunction EASE_IN = f -> f * f;
    /**
     * Quadratic ease-out function
     * Defined by:
     * <pre>f(x) = x(2 - x)</pre>
     */
    public static final EaseFunction EASE_OUT = f -> f * (2 - f);


}
