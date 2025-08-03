package me.pan_truskawka045.effects3d.animations.values;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.EaseFunction;
import me.pan_truskawka045.effects3d.animations.EaseFunctions;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an eased value that interpolates between a start value and a target value
 * using a specified easing function. This class is useful for creating smooth animations
 * and transitions by applying mathematical easing curves to value interpolation.
 *
 * <p>The class provides various factory methods for common easing types such as linear,
 * ease-in, and ease-out transitions, as well as a generic method for custom easing functions.</p>
 *
 * @author pan_truskawka045
 * @since 1.0
 */
@RequiredArgsConstructor
public final class EaseValue {
    /** The initial value at the start of the animation */
    private final float startValue;

    /** The target value at the end of the animation */
    private final float target;

    /** The easing function used to interpolate between start and target values */
    private final EaseFunction easeFunction;

    /**
     * Calculates the current value based on the animation progress and easing function.
     *
     * @param f the animation progress as a value between 0.0 and 1.0, where 0.0 represents
     *          the start of the animation and 1.0 represents the end
     * @return the interpolated value between startValue and target, modified by the easing function
     */
    public float getValue(float f) {
        return startValue + (target - startValue) * easeFunction.ease(f);
    }

    /**
     * Creates a new EaseValue with the specified start value, target value, and easing function.
     *
     * @param startValue the initial value at the start of the animation
     * @param target the target value at the end of the animation
     * @param easeFunction the easing function to apply during interpolation
     * @return a new EaseValue instance
     * @throws NullPointerException if easeFunction is null
     */
    public static EaseValue of(float startValue, float target, @NotNull EaseFunction easeFunction) {
        Preconditions.checkNotNull(easeFunction, "Ease function must not be null");
        return new EaseValue(startValue, target, easeFunction);
    }

    /**
     * Creates a new EaseValue with linear interpolation between the start and target values.
     * Linear interpolation provides a constant rate of change throughout the animation.
     *
     * @param startValue the initial value at the start of the animation
     * @param target the target value at the end of the animation
     * @return a new EaseValue instance with linear easing
     */
    public static EaseValue linear(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.LINEAR);
    }

    /**
     * Creates a new EaseValue with ease-in interpolation between the start and target values.
     * Ease-in provides a slow start that gradually accelerates towards the end.
     *
     * @param startValue the initial value at the start of the animation
     * @param target the target value at the end of the animation
     * @return a new EaseValue instance with ease-in easing
     */
    public static EaseValue easeIn(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.EASE_IN);
    }

    /**
     * Creates a new EaseValue with ease-out interpolation between the start and target values.
     * Ease-out provides a fast start that gradually decelerates towards the end.
     *
     * @param startValue the initial value at the start of the animation
     * @param target the target value at the end of the animation
     * @return a new EaseValue instance with ease-out easing
     */
    public static EaseValue easeOut(float startValue, float target) {
        return new EaseValue(startValue, target, EaseFunctions.EASE_OUT);
    }

}
