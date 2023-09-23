package me.pan_truskawka045.effects3d.animations.frames;


import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.values.EaseValue;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class MultipleEaseFunctionsFrame extends AbstractFrame {

    private final EaseValue[] easeValues;
    private final Consumer<float[]> consumer;
    private final int steps;
    private final int ticksPerStep;
    private int currentStep;
    private int currentTick = 0;


    @Override
    public void tick() {
        if (++currentTick % ticksPerStep == 0) {
            float progress = (float) currentStep / (float) steps;
            float[] values = new float[easeValues.length];
            for (int i = 0; i < easeValues.length; i++) {
                values[i] = easeValues[i].getValue(progress);
            }
            consumer.accept(values);
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
