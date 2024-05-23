package me.pan_truskawka045.effects3d.animations;

import java.util.ArrayList;
import java.util.List;

public class AnimationManager {

    private final List<Animation> animations = new ArrayList<>();
    private final List<Animation> animationsToRemove = new ArrayList<>();

    /**
     * Ticks all animations
     */
    public void tick() {
        animations.removeAll(animationsToRemove);
        animationsToRemove.clear();
        animations.forEach(Animation::tick);
    }

    /**
     * Creates new animation which will be ticked
     *
     * @return new animation
     */
    public Animation newAnimation() {
        Animation animation = new Animation(this);
        animations.add(animation);
        return animation;
    }

    /**
     * Creates new animation which won't be ticked
     *
     * @return new animation
     */
    public Animation of() {
        return new Animation(this);
    }

    /**
     * Stops animation
     *
     * @param animation animation to stop
     */
    public void stopAnimation(Animation animation) {
        animationsToRemove.add(animation);
    }

    /**
     * Disposes all animations
     */
    public void dispose() {
        animations.forEach(Animation::stop);
        animations.clear();
        animationsToRemove.clear();
    }

}
