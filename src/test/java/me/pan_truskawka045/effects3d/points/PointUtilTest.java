package me.pan_truskawka045.effects3d.points;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointUtilTest {

    @Test
    public void minPointReturnsPointWithMinimumCoordinatesFromTwoPoints() {
        Point first = new Point(5.0f, 2.0f, 8.0f);
        Point second = new Point(3.0f, 7.0f, 4.0f);

        Point result = PointUtil.minPoint(first, second);

        assertEquals(3.0f, result.getX());
        assertEquals(2.0f, result.getY());
        assertEquals(4.0f, result.getZ());
    }

    @Test
    public void minPointWithIdenticalPointsReturnsSameCoordinates() {
        Point first = new Point(5.0f, 5.0f, 5.0f);
        Point second = new Point(5.0f, 5.0f, 5.0f);

        Point result = PointUtil.minPoint(first, second);

        assertEquals(5.0f, result.getX());
        assertEquals(5.0f, result.getY());
        assertEquals(5.0f, result.getZ());
    }

    @Test
    public void minPointWithNegativeCoordinatesSelectsCorrectMinimums() {
        Point first = new Point(-2.0f, 3.0f, -1.0f);
        Point second = new Point(1.0f, -5.0f, -3.0f);

        Point result = PointUtil.minPoint(first, second);

        assertEquals(-2.0f, result.getX());
        assertEquals(-5.0f, result.getY());
        assertEquals(-3.0f, result.getZ());
    }

    @Test
    public void minPointWithZeroCoordinatesSelectsZeroWhenSmaller() {
        Point first = new Point(0.0f, 2.0f, -1.0f);
        Point second = new Point(1.0f, 0.0f, 3.0f);

        Point result = PointUtil.minPoint(first, second);

        assertEquals(0.0f, result.getX());
        assertEquals(0.0f, result.getY());
        assertEquals(-1.0f, result.getZ());
    }

    @Test
    public void minPointThrowsExceptionWhenFirstPointIsNull() {
        Point second = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> PointUtil.minPoint(null, second));
    }

    @Test
    public void minPointThrowsExceptionWhenSecondPointIsNull() {
        Point first = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> PointUtil.minPoint(first, null));
    }

    @Test
    public void minPointThrowsExceptionWhenBothPointsAreNull() {
        assertThrows(NullPointerException.class, () -> PointUtil.minPoint(null, null));
    }

    @Test
    public void maxPointReturnsPointWithMaximumCoordinatesFromTwoPoints() {
        Point first = new Point(5.0f, 2.0f, 8.0f);
        Point second = new Point(3.0f, 7.0f, 4.0f);

        Point result = PointUtil.maxPoint(first, second);

        assertEquals(5.0f, result.getX());
        assertEquals(7.0f, result.getY());
        assertEquals(8.0f, result.getZ());
    }

    @Test
    public void maxPointWithIdenticalPointsReturnsSameCoordinates() {
        Point first = new Point(5.0f, 5.0f, 5.0f);
        Point second = new Point(5.0f, 5.0f, 5.0f);

        Point result = PointUtil.maxPoint(first, second);

        assertEquals(5.0f, result.getX());
        assertEquals(5.0f, result.getY());
        assertEquals(5.0f, result.getZ());
    }

    @Test
    public void maxPointWithNegativeCoordinatesSelectsCorrectMaximums() {
        Point first = new Point(-2.0f, 3.0f, -1.0f);
        Point second = new Point(1.0f, -5.0f, -3.0f);

        Point result = PointUtil.maxPoint(first, second);

        assertEquals(1.0f, result.getX());
        assertEquals(3.0f, result.getY());
        assertEquals(-1.0f, result.getZ());
    }

    @Test
    public void maxPointWithZeroCoordinatesSelectsZeroWhenLarger() {
        Point first = new Point(0.0f, -2.0f, 1.0f);
        Point second = new Point(-1.0f, 0.0f, -3.0f);

        Point result = PointUtil.maxPoint(first, second);

        assertEquals(0.0f, result.getX());
        assertEquals(0.0f, result.getY());
        assertEquals(1.0f, result.getZ());
    }

    @Test
    public void maxPointThrowsExceptionWhenFirstPointIsNull() {
        Point second = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> PointUtil.maxPoint(null, second));
    }

    @Test
    public void maxPointThrowsExceptionWhenSecondPointIsNull() {
        Point first = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> PointUtil.maxPoint(first, null));
    }

    @Test
    public void maxPointThrowsExceptionWhenBothPointsAreNull() {
        assertThrows(NullPointerException.class, () -> PointUtil.maxPoint(null, null));
    }

    @Test
    public void minPointCreatesNewInstanceIndependentOfInputPoints() {
        Point first = new Point(5.0f, 2.0f, 8.0f);
        Point second = new Point(3.0f, 7.0f, 4.0f);

        Point result = PointUtil.minPoint(first, second);

        assertNotSame(first, result);
        assertNotSame(second, result);
    }

    @Test
    public void maxPointCreatesNewInstanceIndependentOfInputPoints() {
        Point first = new Point(5.0f, 2.0f, 8.0f);
        Point second = new Point(3.0f, 7.0f, 4.0f);

        Point result = PointUtil.maxPoint(first, second);

        assertNotSame(first, result);
        assertNotSame(second, result);
    }
}
