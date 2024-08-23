package me.pan_truskawka045.effects3d.animations.frames;

import lombok.RequiredArgsConstructor;
import me.pan_truskawka045.effects3d.animations.AbstractFrame;
import me.pan_truskawka045.effects3d.animations.Animation;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class JoinAnimationsFrame extends AbstractFrame {

    private final Animation animation;
    private final Set<String> animations;

    @Override
    public void tick() {

    }

    @Override
    public boolean isFinished() {
        for (Map.Entry<String, Animation> stringAnimationEntry : animation.getParallelAnimations().entrySet()) {
            if (animations.contains(stringAnimationEntry.getKey())) {
                continue;
            }
            if (!stringAnimationEntry.getValue().isFinished()) {
                return false;
            }
        }
        return true;
    }
}
