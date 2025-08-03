package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

/**
 * Animation frame that waits for multiple animations to finish, ticking them during wait.
 * Synchronizes completion of multiple animations and continues ticking them until all are done.
 */
@RequiredArgsConstructor
public class WaitForCompletion extends AbstractFrame {

    private final Animation[] animations;

    @Override
    public void tick() {
        for (Animation animation : animations) {
            if (animation.isFinished()) {
                continue;
            }
            animation.tick();
        }
    }

    @Override
    public boolean isFinished() {
        for (Animation animation : animations) {
            if (!animation.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
