package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

/**
 * Animation frame that executes a runnable a specified number of times with delays between executions.
 * Useful for creating repeated actions at regular intervals during animation.
 */
@RequiredArgsConstructor
public class RepeatFrame extends AbstractFrame {

    private final int times;
    private final int delay;
    private final Runnable runnable;
    private int timesLeft;
    private int counter;

    @Override
    public void tick() {
        if (counter >= delay) {
            runnable.run();
            counter = 0;
            timesLeft--;
        } else {
            counter++;
        }
    }

    @Override
    public boolean isFinished() {
        return timesLeft <= 0;
    }

    @Override
    public void reset() {
        this.timesLeft = times;
    }
}
