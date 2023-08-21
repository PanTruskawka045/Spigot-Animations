package me.pan_truskawka045.effects3d.animations;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class RepeatingAnimationTest {

    @Test
    public void testRepeatingAnimation() {
        AnimationManager animationManager = new AnimationManager();
        AtomicInteger i = new AtomicInteger(0);
        animationManager.newAnimation()
                .repeat(10, i::incrementAndGet).finish();
        for (int j = 0; j < 10; j++) {
            animationManager.tick();
        }
        assertEquals(10, i.get());
    }

    @Test
    public void testRepeatingDelay(){
        AnimationManager animationManager = new AnimationManager();
        AtomicInteger i = new AtomicInteger(0);
        animationManager.newAnimation()
                .repeat(10, 1, i::incrementAndGet).finish();
        for (int j = 0; j < 10; j++) {
            animationManager.tick();
        }
        assertEquals(5, i.get());
    }
}
