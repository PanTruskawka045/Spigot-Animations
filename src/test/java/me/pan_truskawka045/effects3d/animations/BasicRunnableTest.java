package me.pan_truskawka045.effects3d.animations;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicRunnableTest {

    @Test
    public void testRunnable() {
        AnimationManager manager = new AnimationManager();
        AtomicInteger i = new AtomicInteger(0);
        manager.newAnimation()
                .then(i::incrementAndGet)
                .then(i::incrementAndGet)
                .finish();
        assertEquals(0, i.get());
        manager.tick();
        assertEquals(1, i.get());
        manager.tick();
        assertEquals(2, i.get());
    }

    @SneakyThrows
    @Test
    public void testFinnish(){
        AnimationManager manager = new AnimationManager();
        manager.newAnimation().finish();
        manager.tick();
        manager.tick();
        Field animations = manager.getClass().getDeclaredField("animations");
        animations.setAccessible(true);
        assertEquals(0, ((List<?>)animations.get(manager)).size());
    }

}
