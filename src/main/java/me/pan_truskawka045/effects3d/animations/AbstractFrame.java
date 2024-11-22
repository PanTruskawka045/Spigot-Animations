package me.pan_truskawka045.effects3d.animations;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractFrame {

    private AbstractFrame nextFrame;

    /**
     * Executes the frame
     */
    public abstract void tick();

    /**
     * @return true if this frame is finished and whether the next frame should be executed
     */
    public abstract boolean isFinished();

    /**
     * Resets the frame to its initial state. Don't have to be implemented.
     */
    public void reset() {
    }

}
