package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

@RequiredArgsConstructor
public class FinnishFrame extends AbstractFrame {

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
