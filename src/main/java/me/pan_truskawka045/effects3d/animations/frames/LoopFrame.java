package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class LoopFrame<T> extends AbstractFrame {

    private final List<T> list;
    private final Consumer<T> consumer;
    private final int ticksPerStep;
    private int pointer = 0;
    private int ticks;

    @Override
    public void tick() {
        if (++ticks % ticksPerStep == 0) {
            consumer.accept(list.get(pointer));
            pointer++;
        }
    }

    @Override
    public boolean isFinished() {
        return pointer >= list.size();
    }

    @Override
    public void reset() {
        pointer = 0;
        ticks = 0;
    }
}
