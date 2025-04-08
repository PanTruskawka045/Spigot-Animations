package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class AwaitNotifyAnimationFrame extends AbstractFrame {

    private final Consumer<AwaitNotifyListener> consumer;
    private final int timeout;

    private boolean notified = false;
    private boolean init = false;
    private int ticks = 0;


    public void init() {
        consumer.accept(() -> AwaitNotifyAnimationFrame.this.notified = true);
    }

    @Override
    public void tick() {
        if (!init) {
            init = true;
            init();
        }
        // No need to do anything here, the notification will be handled in the init method
    }

    @Override
    public boolean isFinished() {
        return notified || (timeout > 0 && ticks++ >= timeout);
    }

    @Override
    public void reset() {
        this.notified = false;
        this.init = false;
    }

    public interface AwaitNotifyListener {
        @SuppressWarnings("unused")
        void notifyAnimation();
    }
}
