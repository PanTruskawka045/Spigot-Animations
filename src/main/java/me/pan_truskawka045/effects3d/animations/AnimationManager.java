package me.pan_truskawka045.effects3d.animations;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

public class AnimationManager {

    private final List<Animation> animations = new ArrayList<>();
    private final List<Animation> animationsToRemove = new ArrayList<>();
    private final Object SYNC = new Object();

    /**
     * Ticks all animations
     */
    public void tick() {
        synchronized (SYNC) {
            Iterator<Animation> iterator = animations.iterator();
            while (iterator.hasNext()) {
                try {
                    Animation animation = iterator.next();
                    if (animation.isFinished() || animationsToRemove.contains(animation)) {
                        animation.stop();
                        iterator.remove();
                        continue;
                    }
                    animation.tick();
                } catch (ConcurrentModificationException e) {
                    break;
                }
            }
        }
    }

    /**
     * Creates new animation which will be ticked
     *
     * @return new animation
     */
    public Animation newAnimation() {
        Animation animation = new Animation(this);
        synchronized (SYNC) {
            animations.add(animation);
        }
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
        synchronized (SYNC) {
            animations.forEach(Animation::stop);
            animations.clear();
            animationsToRemove.clear();
        }
    }

}
