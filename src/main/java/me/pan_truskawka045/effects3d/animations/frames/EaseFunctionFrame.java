package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.EaseFunction;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class EaseFunctionFrame extends AbstractFrame {

    private final float startValue;
    private final float endValue;
    private final EaseFunction easeFunction;
    private final int steps;
    private final int ticksPerStep;
    private final Consumer<Float> consumer;
    private int currentStep;
    private int currentTick = 0;


    @Override
    public void tick() {
        if (++currentTick % ticksPerStep == 0) {
            float progress = (float) currentStep / (float) steps;
            float value = easeFunction.ease(progress) * (endValue - startValue) + startValue;
            consumer.accept(value);
            currentStep++;
        }
    }

    @Override
    public boolean isFinished() {
        return currentStep >= steps;
    }

    @Override
    public void reset() {
        currentStep = 0;
        currentTick = 0;
    }
}
