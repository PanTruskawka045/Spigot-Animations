package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

import java.util.function.Predicate;

/**
 * Animation frame that conditionally stops an animation based on a predicate test.
 * If the predicate returns false, the animation is stopped immediately.
 */
@RequiredArgsConstructor
public class ConditionalStopFrame extends AbstractFrame {

    private final Animation animation;
    private final Predicate<Animation> predicate;

    @Override
    public void tick() {
        boolean test = predicate.test(animation);
        if (!test) {
            animation.stop();
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
