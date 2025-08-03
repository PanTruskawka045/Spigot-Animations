package me.pan_truskawka045.effects3d.animations;

import com.google.common.base.Preconditions;
import lombok.Getter;
import me.pan_truskawka045.effects3d.animations.frames.*;
import me.pan_truskawka045.effects3d.animations.values.EaseValue;
import org.jetbrains.annotations.NotNull;

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

    public Animation(@NotNull AnimationManager manager) {
        Preconditions.checkNotNull(manager, "manager cannot be null");
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
     * @return current animation
     */
    public @NotNull Animation setExceptionHandler(@NotNull BiConsumer<Exception, Animation> exceptionHandler) {
        Preconditions.checkNotNull(exceptionHandler, "exceptionHandler cannot be null");
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
     * @see RunnableFrame
     */
    public @NotNull Animation then(@NotNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
        this.addFrame(new RunnableFrame(runnable));
        return this;
    }

    /**
     * Adds sleep frame to the animation
     *
     * @param ticks ticks to sleep
     * @return current animation
     * @see SleepFrame
     */
    public @NotNull Animation sleep(int ticks) {
        Preconditions.checkArgument(ticks >= 0, "ticks must be non-negative");
        this.addFrame(new SleepFrame(ticks));
        return this;
    }

    /**
     * Adds animation frame to the animation
     *
     * @param animation animation to run
     * @return current animation
     * @see Animation
     */
    public @NotNull Animation then(@NotNull Animation animation) {
        Preconditions.checkNotNull(animation, "animation cannot be null");
        this.addFrame(animation);
        return this;
    }

    /**
     * Adds repeat frame to the animation
     *
     * @param times    times to repeat
     * @param runnable runnable to run
     * @return current animation
     * @see RepeatFrame
     */
    public @NotNull Animation repeat(int times, @NotNull Runnable runnable) {
        Preconditions.checkArgument(times > 0, "times must be positive");
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
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
     * @see RepeatFrame
     */
    public @NotNull Animation repeat(int times, int delay, @NotNull Runnable runnable) {
        Preconditions.checkArgument(times > 0, "times must be positive");
        Preconditions.checkArgument(delay >= 0, "delay must be non-negative");
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
        this.addFrame(new RepeatFrame(times, delay, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     * Repeats technically forever, but will stop on application crash
     *
     * @param runnable runnable to run
     * @return current animation
     * @see RepeatFrame
     */
    public @NotNull Animation repeatForever(@NotNull Runnable runnable) {
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
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
     * @see RepeatFrame
     */
    public @NotNull Animation repeatForever(int delay, @NotNull Runnable runnable) {
        Preconditions.checkArgument(delay >= 0, "delay must be non-negative");
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
        this.addFrame(new RepeatFrame(Integer.MAX_VALUE, delay, runnable));
        return this;
    }

    /**
     * Adds repeat frame to the animation
     *
     * @param times     times to repeat
     * @param animation animation to run
     * @return current animation
     * @see Animation
     */
    public @NotNull Animation repeatAnimation(int times, @NotNull Animation animation) {
        Preconditions.checkArgument(times > 0, "times must be positive");
        Preconditions.checkNotNull(animation, "animation cannot be null");
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
     * @see ValueFrame
     */
    public @NotNull Animation untilValue(float startValue, float target, float maxStep, @NotNull Consumer<Float> consumer) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(maxStep > 0, "maxStep must be positive");
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
     * @see ValueFrame
     */
    public @NotNull Animation untilValue(float startValue, float target, float maxStep, @NotNull Consumer<Float> consumer, int ticksBetween) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(maxStep > 0, "maxStep must be positive");
        Preconditions.checkArgument(ticksBetween > 0, "ticksBetween must be positive");
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
     * @see EaseFunctionFrame
     */
    public @NotNull Animation easeFunction(float startValue, float endValue, @NotNull EaseFunction easeFunction, int steps, int ticksPerStep, @NotNull Consumer<Float> consumer) {
        Preconditions.checkNotNull(easeFunction, "easeFunction cannot be null");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(steps > 0, "steps must be positive");
        Preconditions.checkArgument(ticksPerStep > 0, "ticksPerStep must be positive");
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
     * @see EaseFunctionFrame
     */
    public @NotNull Animation linearEaseFunction(float startValue, float endValue, int steps, int ticksPerStep, @NotNull Consumer<Float> consumer) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(steps > 0, "steps must be positive");
        Preconditions.checkArgument(ticksPerStep > 0, "ticksPerStep must be positive");
        this.addFrame(new EaseFunctionFrame(startValue, endValue, EaseFunctions.LINEAR, steps, ticksPerStep, consumer));
        return this;
    }

    /**
     * Ease function with linear ease function
     *
     * @param startValue   first frame value
     * @param endValue     last frame value
     * @param easeFunction ease function to use
     * @param step         step to take
     * @param consumer     consumer to run
     * @return current animation
     * @see EaseFunctionFrame
     */
    public @NotNull Animation easeFunction(float startValue, float endValue, @NotNull EaseFunction easeFunction, int step, @NotNull Consumer<Float> consumer) {
        Preconditions.checkNotNull(easeFunction, "easeFunction cannot be null");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(step > 0, "step must be positive");
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
     * @see EaseFunctionFrame
     */
    public @NotNull Animation linearEaseFunction(float startValue, float endValue, int step, @NotNull Consumer<Float> consumer) {
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(step > 0, "step must be positive");
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
     * @see LoopFrame
     */
    public @NotNull <T> Animation forEach(@NotNull List<T> list, @NotNull Consumer<T> consumer) {
        Preconditions.checkNotNull(list, "list cannot be null");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
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
     * @see LoopFrame
     */
    public @NotNull <T> Animation forEach(@NotNull List<T> list, @NotNull Consumer<T> consumer, int ticksPerStep) {
        Preconditions.checkNotNull(list, "list cannot be null");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkArgument(ticksPerStep > 0, "ticksPerStep must be positive");
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
     * @see MultipleEaseFunctionsFrame
     */
    public @NotNull Animation easeFunctions(int steps, int ticksPerStep, @NotNull Consumer<float[]> consumer, @NotNull EaseValue... easeValues) {
        Preconditions.checkArgument(steps > 0, "steps must be positive");
        Preconditions.checkArgument(ticksPerStep > 0, "ticksPerStep must be positive");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkNotNull(easeValues, "easeValues cannot be null");
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
     * @see MultipleEaseFunctionsFrame
     */
    public @NotNull Animation easeFunctions(int steps, int ticksPerStep, @NotNull Consumer<float[]> consumer, @NotNull List<EaseValue> easeValues) {
        Preconditions.checkArgument(steps > 0, "steps must be positive");
        Preconditions.checkArgument(ticksPerStep > 0, "ticksPerStep must be positive");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkNotNull(easeValues, "easeValues cannot be null");
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
     * @see MultipleEaseFunctionsFrame
     */
    public @NotNull Animation easeFunctions(int steps, @NotNull Consumer<float[]> consumer, @NotNull EaseValue easeValue) {
        Preconditions.checkArgument(steps > 0, "steps must be positive");
        Preconditions.checkNotNull(consumer, "consumer cannot be null");
        Preconditions.checkNotNull(easeValue, "easeValue cannot be null");
        this.addFrame(new MultipleEaseFunctionsFrame(new EaseValue[]{easeValue}, consumer, steps, 1));
        return this;
    }

    /**
     * Waits for {@param animations} to finish
     *
     * @param animations animations to wait for
     * @return current animation
     * @see WaitForCompletion
     */
    public @NotNull Animation waitForCompletion(@NotNull Animation... animations) {
        Preconditions.checkNotNull(animations, "animations cannot be null");
        this.addFrame(new WaitForCompletion(animations));
        return this;
    }


    /**
     * @param condition condition to continue. If falsy the animation will stop
     * @return current animation
     * @see ConditionalStopFrame
     */
    public @NotNull Animation continueIf(@NotNull Predicate<Animation> condition) {
        Preconditions.checkNotNull(condition, "condition cannot be null");
        this.addFrame(new ConditionalStopFrame(this, condition));
        return this;
    }


    /**
     * Runs the given animation in parallel with the current animation.
     *
     * @param name      the name of the parallel animation
     * @param animation the animation to run in parallel
     * @return current animation
     * @see RunInParallelFrame
     */
    public @NotNull Animation runInParallel(@NotNull String name, @NotNull Animation animation) {
        Preconditions.checkNotNull(name, "name cannot be null");
        Preconditions.checkNotNull(animation, "animation cannot be null");
        this.addFrame(new RunInParallelFrame(animation, name, this));
        return this;
    }

    /**
     * Joins the given animations with the current animation.
     * If the joined animations are not finished, the current animation will wait for them to finish.
     *
     * @param animations the animations to join
     * @return current animation
     * @see JoinAnimationsFrame
     */
    public @NotNull Animation join(@NotNull String... animations) {
        Preconditions.checkNotNull(animations, "animations cannot be null");
        this.addFrame(new JoinAnimationsFrame(this, new HashSet<>(Arrays.asList(animations))));
        return this;
    }


    /**
     * Runs the given runnable while the condition is falsy
     *
     * @param runnable  runnable to run
     * @param condition condition to stop
     * @return current animation
     * @see RepeatUntilFrame
     */
    public @NotNull Animation runWhile(@NotNull Runnable runnable, @NotNull Predicate<Animation> condition) {
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
        Preconditions.checkNotNull(condition, "condition cannot be null");
        this.addFrame(new RepeatUntilFrame(1, runnable, condition, this));
        return this;
    }

    /**
     * Runs the given runnable while the condition is falsy
     *
     * @param delay     delay between each run in ticks
     * @param runnable  runnable to run
     * @param condition condition to stop
     * @return current animation
     * @see RepeatUntilFrame
     */
    public @NotNull Animation runWhile(int delay, @NotNull Runnable runnable, @NotNull Predicate<Animation> condition) {
        Preconditions.checkArgument(delay >= 0, "delay must be non-negative");
        Preconditions.checkNotNull(runnable, "runnable cannot be null");
        Preconditions.checkNotNull(condition, "condition cannot be null");
        this.addFrame(new RepeatUntilFrame(delay, runnable, condition, this));
        return this;
    }

    /**
     * Will join already running animations and wait for them to finish
     *
     * @param animations animations to join
     * @return current animation
     * @see JoinIndependentAnimationsFrame
     */
    public @NotNull Animation join(@NotNull Animation... animations) {
        Preconditions.checkNotNull(animations, "animations cannot be null");
        JoinIndependentAnimationsFrame frame = new JoinIndependentAnimationsFrame(Arrays.asList(animations), false);
        this.addFrame(frame);
        return this;
    }

    /**
     * Will join already running animations and wait for them to finish
     *
     * @param animations animations to join
     * @return current animation
     * @see JoinIndependentAnimationsFrame
     */
    public @NotNull Animation join(@NotNull Collection<Animation> animations) {
        Preconditions.checkNotNull(animations, "animations cannot be null");
        JoinIndependentAnimationsFrame frame = new JoinIndependentAnimationsFrame(animations, false);
        this.addFrame(frame);
        return this;
    }

    /**
     * Will join already running animations and wait for them to finish and will remove them from the list
     *
     * @param animations animations to join
     * @return current animation
     * @see JoinIndependentAnimationsFrame
     */
    public @NotNull Animation joinAndRemove(@NotNull Collection<Animation> animations) {
        Preconditions.checkNotNull(animations, "animations cannot be null");
        JoinIndependentAnimationsFrame frame = new JoinIndependentAnimationsFrame(animations, true);
        this.addFrame(frame);
        return this;
    }


    /**
     * Awaits a notification to proceed with the animation.
     * The provided listener will be used to handle the notification logic.
     *
     * @param listener a consumer that accepts an `AwaitNotifyAnimationFrame.AwaitNotifyListener`
     *                 to handle the notification process
     * @return the current animation instance
     * @see AwaitNotifyAnimationFrame
     */
    public @NotNull Animation awaitNotification(@NotNull Consumer<AwaitNotifyAnimationFrame.AwaitNotifyListener> listener) {
        Preconditions.checkNotNull(listener, "listener cannot be null");
        this.addFrame(new AwaitNotifyAnimationFrame(listener, -1));
        return this;
    }


    /**
     * Awaits a notification to proceed with the animation, with a specified timeout.
     *
     * @param timeout  the maximum time to wait for the notification, in ticks
     * @param listener a consumer that accepts an `AwaitNotifyAnimationFrame.AwaitNotifyListener`
     *                 to handle the notification process
     * @return the current animation instance
     * @see AwaitNotifyAnimationFrame
     */
    public @NotNull Animation awaitNotification(int timeout, @NotNull Consumer<AwaitNotifyAnimationFrame.AwaitNotifyListener> listener) {
        Preconditions.checkArgument(timeout > 0, "timeout must be positive");
        Preconditions.checkNotNull(listener, "listener cannot be null");
        this.addFrame(new AwaitNotifyAnimationFrame(listener, timeout));
        return this;
    }

    /**
     * Makes animation never end
     *
     * @return current animation
     */
    public @NotNull Animation looped() {
        this.loop = true;
        return this;
    }


    /**
     * Means the animation is finished, and won't be ticked anymore
     *
     * @return current animation
     * @see FinishFrame
     */
    public @NotNull Animation finish() {
        this.addFrame(new FinishFrame(this));
        return this;
    }

    /**
     * Adds frame to the animation
     *
     * @param frame frame to add
     */
    private void addFrame(@NotNull AbstractFrame frame) {
        frame.reset();
        if (first == null) {
            first = frame;
        }
        if (last == null) {
            last = frame;
        }
        if (current == null) {
            current = frame;
        }
        last.setNextFrame(frame);
        last = frame;
    }


}
