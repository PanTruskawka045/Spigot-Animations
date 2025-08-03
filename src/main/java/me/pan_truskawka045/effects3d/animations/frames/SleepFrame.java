package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

/**
 * Animation frame that pauses animation execution for a specified number of ticks.
 * Provides timing delays between other animation frames in a sequence.
 */
@RequiredArgsConstructor
public class SleepFrame extends AbstractFrame {

    private final int ticks;
    private int ticksLeft;


    @Override
    public void tick() {
        ticksLeft--;
    }

    @Override
    public boolean isFinished() {
        return ticksLeft <= 0;
    }

    @Override
    public void reset() {
        this.ticksLeft = ticks;
    }
}
