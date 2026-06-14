package me.pan_truskawka045.effects3d.animations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnimationManager {

    private final List<Animation> animations = new ArrayList<>();
    private final Set<Animation> animationsToRemove = Collections.newSetFromMap(new IdentityHashMap<>());
    private final Object SYNC = new Object();

    /**
     * Ticks all animations
     */
    public void tick() {
        synchronized (SYNC) {
            try {
                Iterator<Animation> iterator = animations.iterator();
                while (iterator.hasNext()) {
                    Animation animation = iterator.next();
                    if (animation.isFinished() || animationsToRemove.remove(animation)) {
                        iterator.remove();
                        continue;
                    }
                    animation.tick();

                    if (animationsToRemove.remove(animation)) {
                        iterator.remove();
                    }
                }
            } catch (ConcurrentModificationException ignored) {
                // An animation may create another managed animation while being ticked.
            } finally {
                animations.removeAll(animationsToRemove);
                animationsToRemove.clear();
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
        synchronized (SYNC) {
            animationsToRemove.add(animation);
        }
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
