package me.pan_truskawka045.effects3d.animations.frames;

import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Animation frame that waits for a collection of independent animations to finish.
 * Efficiently removes completed animations during ticking to optimize performance.
 */
public class JoinIndependentAnimationsFrame extends AbstractFrame {

    private final Collection<Animation> animations;

    public JoinIndependentAnimationsFrame(Collection<Animation> animations, boolean remove) {
        if (remove) {
            this.animations = animations;
        } else {
            this.animations = new LinkedList<>(animations);
        }
    }

    @Override
    public void tick() {
        animations.removeIf(Animation::isFinished);
    }

    @Override
    public boolean isFinished() {
        return animations.isEmpty();
    }
}
