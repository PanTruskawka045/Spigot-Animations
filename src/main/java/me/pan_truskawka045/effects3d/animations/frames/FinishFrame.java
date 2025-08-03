package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

/**
 * Animation frame that stops the parent animation.
 * Executes once and immediately finishes, causing the animation to terminate.
 */
@RequiredArgsConstructor
public class FinishFrame extends AbstractFrame {

    private final Animation animation;
    @Override
    public void tick() {
        animation.stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
