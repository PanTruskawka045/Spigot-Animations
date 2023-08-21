package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ValueFrame extends AbstractFrame {
    private final float startValue;
    private final float target;
    private final float maxStep;
    private final Consumer<Float> consumer;
    private final int ticksBetween;
    private int currentTick = 0;
    private float currentValue;

    @Override
    public void tick() {
        if(++currentTick % ticksBetween == 0){
            float step = Math.min(maxStep, Math.abs(target - currentValue));
            if (target > currentValue) {
                currentValue = Math.min(target, currentValue + step);
            } else {
                currentValue = Math.max(target, currentValue - step);
            }
            consumer.accept(currentValue);
        }
    }

    @Override
    public boolean isFinished() {
        return currentValue == target;
    }

    @Override
    public void reset() {
        currentValue = startValue;
        currentTick = 0;
    }
}
