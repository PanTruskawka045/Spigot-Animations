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
    /**
     * Cubic ease-in function
     * Defined by:
     * <pre>f(x) = x^3</pre>
     */
    public static final EaseFunction CUBIC_IN = f -> f * f * f;

    /**
     * Cubic ease-out function
     * Defined by:
     * <pre>f(x) = (--f) * f * f + 1</pre>
     */
    public static final EaseFunction CUBIC_OUT = f -> {
        float t = f - 1;
        return t * t * t + 1;
    };

    /**
     * Sine ease-in function
     * Defined by:
     * <pre>f(x) = 1 - cos((x * PI) / 2)</pre>
     */
    public static final EaseFunction SINE_IN = f -> (float)(1 - Math.cos((f * Math.PI) / 2));

    /**
     * Sine ease-out function
     * Defined by:
     * <pre>f(x) = sin((x * PI) / 2)</pre>
     */
    public static final EaseFunction SINE_OUT = f -> (float)Math.sin((f * Math.PI) / 2);


}
