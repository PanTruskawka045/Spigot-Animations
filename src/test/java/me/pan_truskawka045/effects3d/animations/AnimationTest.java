package me.pan_truskawka045.effects3d.animations;

import me.pan_truskawka045.effects3d.animations.frames.AwaitNotifyAnimationFrame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class AnimationTest {

    private AnimationManager manager;

    @BeforeEach
    public void setUp() {
        manager = new AnimationManager();
    }

    @Test
    public void awaitNotificationWithValidTimeoutAndListener() {
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> capturedListener = new AtomicReference<>();
        Animation animation = manager.newAnimation()
                .awaitNotification(100, capturedListener::set)
                .finish();

        manager.tick();

        assertNotNull(capturedListener.get());
    }

    @Test
    public void awaitNotificationCompletesWhenNotified() {
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> capturedListener = new AtomicReference<>();
        AtomicBoolean completed = new AtomicBoolean(false);

        manager.newAnimation()
                .awaitNotification(100, capturedListener::set)
                .then(() -> completed.set(true))
                .finish();

        manager.tick();
        assertFalse(completed.get());

        capturedListener.get().notifyAnimation();
        manager.tick();
        manager.tick();
        assertTrue(completed.get());
    }

    @Test
    public void awaitNotificationTimesOutAfterSpecifiedTicks() {
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> capturedListener = new AtomicReference<>();
        AtomicBoolean completed = new AtomicBoolean(false);

        manager.newAnimation()
                .awaitNotification(3, capturedListener::set)
                .then(() -> completed.set(true))
                .finish();

        for (int i = 0; i < 3; i++) {
            manager.tick();
            assertFalse(completed.get());
        }

        manager.tick();
        manager.tick();
        assertTrue(completed.get());
    }

    @Test
    public void awaitNotificationThrowsExceptionForZeroTimeout() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.newAnimation().awaitNotification(0, listener -> {}));
    }

    @Test
    public void awaitNotificationThrowsExceptionForNegativeTimeout() {
        assertThrows(IllegalArgumentException.class, () ->
                manager.newAnimation().awaitNotification(-1, listener -> {}));
    }

    @Test
    public void awaitNotificationThrowsExceptionForNullListener() {
        assertThrows(NullPointerException.class, () ->
                manager.newAnimation().awaitNotification(100, null));
    }

    @Test
    public void loopedMakesAnimationNeverEnd() throws Exception {
        Animation animation = manager.newAnimation().looped();

        Field loopField = Animation.class.getDeclaredField("loop");
        loopField.setAccessible(true);
        assertTrue((Boolean) loopField.get(animation));
    }

    @Test
    public void loopedReturnsCurrentAnimationInstance() {
        Animation animation = manager.newAnimation();
        assertSame(animation, animation.looped());
    }

    @Test
    public void finishAddsFinishFrameAndReturnsCurrentAnimation() {
        Animation animation = manager.newAnimation();
        assertSame(animation, animation.finish());
    }

    @Test
    public void finishCausesAnimationToBeRemovedFromManager() throws Exception {
        manager.newAnimation().finish();
        manager.tick();

        Field animationsField = manager.getClass().getDeclaredField("animations");
        animationsField.setAccessible(true);
        assertEquals(0, ((java.util.List<?>) animationsField.get(manager)).size());
    }

    @Test
    public void finishStopsAnimationExecution() {
        AtomicBoolean executed = new AtomicBoolean(false);

        manager.newAnimation()
                .finish()
                .then(() -> executed.set(true));

        manager.tick();
        manager.tick();
        assertFalse(executed.get());
    }

    @Test
    public void multipleAwaitNotificationsCanBeChained() {
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> firstListener = new AtomicReference<>();
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> secondListener = new AtomicReference<>();
        AtomicBoolean completed = new AtomicBoolean(false);

        manager.newAnimation()
                .awaitNotification(100, firstListener::set)
                .awaitNotification(100, secondListener::set)
                .then(() -> completed.set(true))
                .finish();

        manager.tick();
        firstListener.get().notifyAnimation();
        manager.tick();
        manager.tick();
        assertFalse(completed.get());

        secondListener.get().notifyAnimation();
        manager.tick();
        manager.tick();
        assertTrue(completed.get());
    }

    @Test
    public void awaitNotificationWithLoopedAnimation() {
        AtomicReference<AwaitNotifyAnimationFrame.AwaitNotifyListener> capturedListener = new AtomicReference<>();
        AtomicBoolean executed = new AtomicBoolean(false);

        manager.newAnimation()
                .awaitNotification(100, capturedListener::set)
                .then(() -> executed.set(!executed.get()))
                .looped();

        manager.tick();
        capturedListener.get().notifyAnimation();
        manager.tick();
        manager.tick();
        assertTrue(executed.get());

        manager.tick();
        capturedListener.get().notifyAnimation();
        manager.tick();
        manager.tick();
        assertFalse(executed.get());
    }
}
