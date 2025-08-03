package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

import java.util.Set;

/**
 * Animation frame that waits for specific parallel animations to complete.
 * Monitors named parallel animations and finishes when all specified animations are done.
 */
@RequiredArgsConstructor
public class JoinAnimationsFrame extends AbstractFrame {

    private final Animation animation;
    private final Set<String> animations;

    @Override
    public void tick() {

    }

    @Override
    public boolean isFinished() {
        for (String s : animations) {
            if (!animation.getParallelAnimations().containsKey(s)) {
                continue;
            }
            if (!animation.getParallelAnimations().get(s).isFinished()) {
                return false;
            }
        }

        return true;
    }
}
