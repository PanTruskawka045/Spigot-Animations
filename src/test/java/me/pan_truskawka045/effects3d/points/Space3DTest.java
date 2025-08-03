package me.pan_truskawka045.effects3d.points;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Space3DTest {

    private Space3D space;

    @BeforeEach
    public void setUp() {
        space = new Space3D();
    }

    @Test
    public void drawLineAddsPointsBetweenTwoPointsAndReturnsSpace() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(3.0f, 0.0f, 0.0f);

        Space3D result = space.drawLine(point1, point2, 1.0f);

        assertSame(space, result);
        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawLineWithIdenticalPointsStillAddsPoints() {
        Point point1 = new Point(5.0f, 5.0f, 5.0f);
        Point point2 = new Point(5.0f, 5.0f, 5.0f);

        space.drawLine(point1, point2, 1.0f);

        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawLineWithVerySmallDistanceBetweenPointsCreatesMorePoints() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(1.0f, 0.0f, 0.0f);

        space.drawLine(point1, point2, 0.1f);
        int smallDistanceCount = space.getPoints().size();

        space = new Space3D();
        space.drawLine(point1, point2, 1.0f);
        int largeDistanceCount = space.getPoints().size();

        assertTrue(smallDistanceCount > largeDistanceCount);
    }

    @Test
    public void drawLineWithNegativeCoordinatesWorksCorrectly() {
        Point point1 = new Point(-5.0f, -3.0f, -2.0f);
        Point point2 = new Point(-1.0f, -1.0f, -1.0f);

        Space3D result = space.drawLine(point1, point2, 1.0f);

        assertSame(space, result);
        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawLineThrowsExceptionWhenFirstPointIsNull() {
        Point point2 = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> space.drawLine(null, point2, 1.0f));
    }

    @Test
    public void drawLineThrowsExceptionWhenSecondPointIsNull() {
        Point point1 = new Point(1.0f, 2.0f, 3.0f);

        assertThrows(NullPointerException.class, () -> space.drawLine(point1, null, 1.0f));
    }

    @Test
    public void drawLineThrowsExceptionWhenDistanceBetweenPointsIsZero() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(1.0f, 1.0f, 1.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawLine(point1, point2, 0.0f));
    }

    @Test
    public void drawLineThrowsExceptionWhenDistanceBetweenPointsIsNegative() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(1.0f, 1.0f, 1.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawLine(point1, point2, -1.0f));
    }

    @Test
    public void drawCircleAddsPointsInCircularPatternAndReturnsSpace() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        Space3D result = space.drawCircle(center, 5.0f, 1.0f);

        assertSame(space, result);
        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawCircleWithSmallerDistanceBetweenPointsCreatesMorePoints() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        space.drawCircle(center, 5.0f, 0.1f);
        int smallDistanceCount = space.getPoints().size();

        space = new Space3D();
        space.drawCircle(center, 5.0f, 1.0f);
        int largeDistanceCount = space.getPoints().size();

        assertTrue(smallDistanceCount > largeDistanceCount);
    }

    @Test
    public void drawCircleWithLargerRadiusCreatesMorePoints() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        space.drawCircle(center, 10.0f, 1.0f);
        int largeRadiusCount = space.getPoints().size();

        space = new Space3D();
        space.drawCircle(center, 2.0f, 1.0f);
        int smallRadiusCount = space.getPoints().size();

        assertTrue(largeRadiusCount > smallRadiusCount);
    }

    @Test
    public void drawCircleWithVerySmallRadiusStillCreatesPoints() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        space.drawCircle(center, 0.1f, 0.05f);

        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawCircleWithNegativeCenterCoordinatesWorksCorrectly() {
        Point center = new Point(-5.0f, -3.0f, -2.0f);

        Space3D result = space.drawCircle(center, 3.0f, 1.0f);

        assertSame(space, result);
        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void drawCircleThrowsExceptionWhenCenterIsNull() {
        assertThrows(NullPointerException.class, () -> space.drawCircle(null, 5.0f, 1.0f));
    }

    @Test
    public void drawCircleThrowsExceptionWhenRadiusIsZero() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawCircle(center, 0.0f, 1.0f));
    }

    @Test
    public void drawCircleThrowsExceptionWhenRadiusIsNegative() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawCircle(center, -5.0f, 1.0f));
    }

    @Test
    public void drawCircleThrowsExceptionWhenDistanceBetweenPointsIsZero() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawCircle(center, 5.0f, 0.0f));
    }

    @Test
    public void drawCircleThrowsExceptionWhenDistanceBetweenPointsIsNegative() {
        Point center = new Point(0.0f, 0.0f, 0.0f);

        assertThrows(IllegalArgumentException.class, () -> space.drawCircle(center, 5.0f, -1.0f));
    }

    @Test
    public void drawLineAndDrawCircleCanBeChainedTogether() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(3.0f, 0.0f, 0.0f);
        Point center = new Point(5.0f, 5.0f, 5.0f);

        Space3D result = space.drawLine(point1, point2, 1.0f)
                .drawCircle(center, 2.0f, 0.5f);

        assertSame(space, result);
        assertFalse(space.getPoints().isEmpty());
    }

    @Test
    public void multipleDrawLineCallsAccumulatePoints() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(1.0f, 0.0f, 0.0f);
        Point point3 = new Point(2.0f, 0.0f, 0.0f);

        space.drawLine(point1, point2, 0.5f);
        int firstLineCount = space.getPoints().size();

        space.drawLine(point2, point3, 0.5f);
        int totalCount = space.getPoints().size();

        assertTrue(totalCount > firstLineCount);
    }

    @Test
    public void multipleDrawCircleCallsAccumulatePoints() {
        Point center1 = new Point(0.0f, 0.0f, 0.0f);
        Point center2 = new Point(5.0f, 5.0f, 5.0f);

        space.drawCircle(center1, 2.0f, 1.0f);
        int firstCircleCount = space.getPoints().size();

        space.drawCircle(center2, 3.0f, 1.0f);
        int totalCount = space.getPoints().size();

        assertTrue(totalCount > firstCircleCount);
    }
}
