package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

@RequiredArgsConstructor
public class RunnableFrame extends AbstractFrame {

    private final Runnable runnable;

    @Override
    public void tick() {
        runnable.run();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
