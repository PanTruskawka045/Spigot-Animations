package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

@RequiredArgsConstructor
public class RunInParallelFrame extends AbstractFrame {

    private final Animation animation;
    private final String name;
    private final Animation parent;


    @Override
    public void tick() {
        if (!animation.getParallelAnimations().containsKey(name)) {
            parent.getParallelAnimations().put(name, animation);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
