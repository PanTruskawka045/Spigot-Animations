package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

/**
 * Animation frame that starts an animation in parallel with the current one.
 * Registers the animation with a specified name in the parent's parallel animations map.
 */
@RequiredArgsConstructor
public class RunInParallelFrame extends AbstractFrame {

    private final Animation animation;
    private final String name;
    private final Animation parent;


    @Override
    public void tick() {
        if (!animation.getParallelAnimations().containsKey(name)) {
            parent.getParallelAnimations().put(name, animation);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
