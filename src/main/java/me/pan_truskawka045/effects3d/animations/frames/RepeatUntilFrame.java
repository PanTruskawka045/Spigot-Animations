package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

import java.util.function.Predicate;


@RequiredArgsConstructor
public class RepeatUntilFrame extends AbstractFrame {

    private final int delay;
    private final Runnable runnable;
    private final Predicate<Animation> condition;
    private final Animation parent;
    private int counter;

    @Override
    public void tick() {
        if (counter >= delay) {
            runnable.run();
            counter = 0;
        } else {
            counter++;
        }
    }

    @Override
    public boolean isFinished() {
        return condition.test(parent);
    }
}
