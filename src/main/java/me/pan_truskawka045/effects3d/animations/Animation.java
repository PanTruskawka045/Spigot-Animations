package me.pan_truskawka045.effects3d.animations;

import lombok.Getter;
import me.pan_truskawka045.effects3d.animations.frames.*;
import me.pan_truskawka045.effects3d.animations.values.EaseValue;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Animation class
 * <p>This class is used to create animations</p>
 * <br>
 * Example:
 * <pre>
 *         {@code
 *         Animation animation = new Animation();
 *         animation.then(() -> System.out.println("Hello world!"))
 *         .sleep(20)
 *         .then(() -> System.out.println("Hello world again!"))
 *         .finish();
 *         }
 *         </pre>
 *
 * @author pan_truskawka045
 * @since 1.0
 */
@SuppressWarnings("unused")
public class Animation extends AbstractFrame {

    private final AnimationManager manager;
    private AbstractFrame current;
    private AbstractFrame first;
    private AbstractFrame last;
    private boolean stopped = false;
    private boolean loop = false;
    private BiConsumer<Exception, Animation> exceptionHandler;
    @Getter
    private final Map<String, Animation> parallelAnimations = new HashMap<>();

    public Animation(AnimationManager manager) {
        this.manager = manager;
        exceptionHandler = (exc, animation) -> {
            System.err.println("Caught an exception in animation: " + exc.getMessage());
            this.manager.stopAnimation(this);
        };
    }

    /**
     * Tick the animation
     */
    @Override
    public void tick() {
        try {
            if (stopped || current == null) {
                return;
            }
            current.tick();
            if (current.isFinished()) {
                if (loop) {
                    current.reset();
                    addFrame(current);
                }
                current = current.getNextFrame();
                if (current == null) {
                    last = null;
                }
            }
            for (Animation value : parallelAnimations.values()) {
                if (!value.isFinished()) {
                    value.tick();
                }
            }
        } catch (Exception exc) {
            exceptionHandler.accept(exc, this);
        }
    }

    /**
     * Sets the animation exception handler
     *
     * @param exceptionHandler exception handler
     */
    public Animation setExceptionHandler(BiConsumer<Exception, Animation> exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    /**
     * Check if the animation is finished
     *
     * @return true if the animation is finished
     */
    @Override
    public boolean isFinished() {
        return (last != null && last.isFinished() && last == current) || stopped;
    }

    /**
     * Stops the animation
     */
    public void stop() {
        manager.stopAnimation(this);
        this.stopped = true;
    }

    /**
     * @param runnable runnable to run
     * @return current animation
     */
    public Animation then(Runnable runnable) {
        this.addFrame(new RunnableFrame(runnable));
        return this;
    }

    /**
     * Adds sleep frame to the animation
     *
     * @param ticks ticks to sleep
     * @return current animation
     */
    public Animation sleep(int ticks) {
        this.addFrame(new SleepFrame(ticks));
        return this;
    }

    /**
     * Adds animation frame to the animation
     *
     * @param animation animation to run
     * @return current animation
     */
    public Animation then(Animation animation) {
        this.addFrame(animation);
        return this;
    }

    /**
     * Adds repeat frame to the animation
     *
     * @param times    times to repeat
     * @param runnable runnable to run
     * @return current animation
     */
    public Animation repeat(int times, Runnable runnable) {
        this.addFrame(new RepeatFrame(times, 0, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     *
     * @param times    times to repeat
     * @param delay    delay between each repeat in ticks
     * @param runnable runnable to run
     * @return current animation
     */
    public Animation repeat(int times, int delay, Runnable runnable) {
        this.addFrame(new RepeatFrame(times, delay, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     * Repeats technically forever, but will stop on application crash
     *
     * @param runnable runnable to run
     * @return current animation
     */
    public Animation repeatForever(Runnable runnable) {
        this.addFrame(new RepeatFrame(Integer.MAX_VALUE, 0, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     * Repeats technically forever, will stop on application crash
     *
     * @param runnable runnable to run
     * @param delay    delay between each repeat in ticks
     * @return current animation
     */
    public Animation repeatForever(int delay, Runnable runnable) {
        this.addFrame(new RepeatFrame(Integer.MAX_VALUE, delay, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     *
     * @param times     times to repeat
     * @param animation animation to run
     * @return current animation
     */
    public Animation repeatAnimation(int times, Animation animation) {
        for (int i = 0; i < times; i++) {
            this.addFrame(animation);
        }
        return this;
    }

    /**
     * Repeats provided consumer until target value equals startValue
     *
     * @param startValue first frame value
     * @param target     target value (last value)
     * @param maxStep    max step between each frame
     * @param consumer   consumer to run
     * @return current animation
     */
    public Animation untilValue(float startValue, float target, float maxStep, Consumer<Float> consumer) {
        this.addFrame(new ValueFrame(startValue, target, maxStep, consumer, 1));
        return this;
    }

    /**
     * Repeats provided consumer until target value equals startValue
     *
     * @param startValue   first frame value
     * @param target       target value (last value)
     * @param maxStep      max step between each frame
     * @param consumer     consumer to run
     * @param ticksBetween ticks between each frame
     * @return current animation
     */
    public Animation untilValue(float startValue, float target, float maxStep, Consumer<Float> consumer, int ticksBetween) {
        this.addFrame(new ValueFrame(startValue, target, maxStep, consumer, ticksBetween));
        return this;
    }


    /**
     * @param startValue   first frame value
     * @param endValue     last frame value
     * @param easeFunction ease function to use
     * @param steps        steps to take
     * @param ticksPerStep ticks between each step
     * @param consumer     consumer to run
     * @return current animation
     */
    public Animation easeFunction(float startValue, float endValue, EaseFunction easeFunction, int steps, int ticksPerStep, Consumer<Float> consumer) {
        this.addFrame(new EaseFunctionFrame(startValue, endValue, easeFunction, steps, ticksPerStep, consumer));
        return this;
    }

    /**
     * Ease function with linear ease function
     *
     * @param startValue   first frame value
     * @param endValue     last frame value
     * @param steps        steps to take
     * @param ticksPerStep ticks between each step
     * @param consumer     consumer to run
     * @return current animation
     */
    public Animation linearEaseFunction(float startValue, float endValue, int steps, int ticksPerStep, Consumer<Float> consumer) {
        this.addFrame(new EaseFunctionFrame(startValue, endValue, EaseFunctions.LINEAR, steps, ticksPerStep, consumer));
        return this;
    }

    /**
     * Ease function with linear ease function
     *
     * @param startValue first frame value
     * @param endValue   last frame value
     * @param step       step to take
     * @param consumer   consumer to run
     * @return current animation
     */
    public Animation easeFunction(float startValue, float endValue, EaseFunction easeFunction, int step, Consumer<Float> consumer) {
        this.addFrame(new EaseFunctionFrame(startValue, endValue, easeFunction, step, 1, consumer));
        return this;
    }

    /**
     * Ease function with linear ease function
     *
     * @param startValue first frame value
     * @param endValue   last frame value
     * @param step       step to take
     * @param consumer   consumer to run
     * @return current animation
     */
    public Animation linearEaseFunction(float startValue, float endValue, int step, Consumer<Float> consumer) {
        this.addFrame(new EaseFunctionFrame(startValue, endValue, EaseFunctions.LINEAR, step, 1, consumer));
        return this;
    }


    /**
     * Loops over a list
     *
     * @param list     list to iterate
     * @param consumer consumer to run
     * @param <T>      type of list
     * @return current animation
     */
    public <T> Animation forEach(List<T> list, Consumer<T> consumer) {
        this.addFrame(new LoopFrame<>(list, consumer, 1));
        return this;
    }

    /**
     * Loops over a list
     *
     * @param list         list to iterate
     * @param consumer     consumer to run
     * @param ticksPerStep ticks between each step
     * @param <T>          type of list
     * @return current animation
     */
    public <T> Animation forEach(List<T> list, Consumer<T> consumer, int ticksPerStep) {
        this.addFrame(new LoopFrame<>(list, consumer, ticksPerStep));
        return this;
    }


    /**
     * Runs {@param steps} steps per {@param ticksPerStep} ticks, easing the values with {@param easeFunction}
     *
     * @param steps        steps to take
     * @param ticksPerStep ticks between each step
     * @param consumer     consumer with <code>float[]</code> as
     * @param easeValues   ease values
     * @return current animation
     */
    public Animation easeFunctions(int steps, int ticksPerStep, Consumer<float[]> consumer, EaseValue... easeValues) {
        this.addFrame(new MultipleEaseFunctionsFrame(easeValues, consumer, steps, ticksPerStep));
        return this;
    }

    /**
     * Runs {@param steps} steps per {@param ticksPerStep} ticks, easing the values with {@param easeFunction}
     *
     * @param steps        steps to take
     * @param ticksPerStep ticks between each step
     * @param consumer     consumer with <code>float[]</code> as
     * @param easeValues   ease values
     * @return current animation
     */
    public Animation easeFunctions(int steps, int ticksPerStep, Consumer<float[]> consumer, List<EaseValue> easeValues) {
        this.addFrame(new MultipleEaseFunctionsFrame(easeValues.toArray(new EaseValue[0]), consumer, steps, ticksPerStep));
        return this;
    }

    /**
     * Runs {@param steps} steps, each per tick, easing the values with {@param easeFunction}
     *
     * @param steps     steps to take
     * @param consumer  consumer with <code>float[]</code> as
     * @param easeValue ease value
     * @return current animation
     */
    public Animation easeFunctions(int steps, Consumer<float[]> consumer, EaseValue easeValue) {
        this.addFrame(new MultipleEaseFunctionsFrame(new EaseValue[]{easeValue}, consumer, steps, 1));
        return this;
    }

    /**
     * Waits for {@param animations} to finish
     *
     * @param animations animations to wait for
     * @return current animation
     */
    public Animation waitForCompletion(Animation... animations) {
        this.addFrame(new WaitForCompletion(animations));
        return this;
    }


    /**
     * @param condition condition to continue. If falsy the animation will stop
     * @return current animation
     */
    public Animation continueIf(Predicate<Animation> condition) {
        this.addFrame(new ConditionalStopFrame(this, condition));
        return this;
    }

    public Animation runInParallel(String name, Animation animation) {
        this.addFrame(new RunInParallelFrame(animation, name, this));
        return this;
    }

    public Animation join(String... animations) {
        this.addFrame(new JoinAnimationsFrame(this, new HashSet<>(Arrays.asList(animations))));
        return this;
    }

    public Animation runWhile(Runnable runnable, Predicate<Animation> condition) {
        this.addFrame(new RepeatUntilFrame(1, runnable, condition, this));
        return this;
    }

    public Animation runWhile(int delay, Runnable runnable, Predicate<Animation> condition) {
        this.addFrame(new RepeatUntilFrame(delay, runnable, condition, this));
        return this;
    }


    /**
     * Makes animation never end
     *
     * @return current animation
     */
    public Animation looped() {
        this.loop = true;
        return this;
    }


    /**
     * Means the animation is finished, and won't be ticked anymore
     *
     * @return current animation
     */
    public Animation finish() {
        this.addFrame(new FinnishFrame(this));
        return this;
    }

    /**
     * Adds frame to the animation
     *
     * @param frame frame to add
     */
    private void addFrame(AbstractFrame frame) {
        frame.reset();
        if (first == null) {
            first = frame;
        }
        if (current == null) {
            current = frame;
        } else {
            last.setNextFrame(frame);
        }
        last = frame;
    }


}
