package me.pan_truskawka045.effects3d.points;

import me.pan_truskawka045.effects3d.vector.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    @Test
    public void scaleUniformlyMultipliesAllCoordinatesByScale() {
        Point point = new Point(2.0f, 3.0f, 4.0f);

        point.scale(2.5f);

        assertEquals(5.0f, point.getX(), 0.001f);
        assertEquals(7.5f, point.getY(), 0.001f);
        assertEquals(10.0f, point.getZ(), 0.001f);
    }

    @Test
    public void scaleUniformlyWithZeroScaleSetsAllCoordinatesToZero() {
        Point point = new Point(2.0f, 3.0f, 4.0f);

        point.scale(0.0f);

        assertEquals(0.0f, point.getX());
        assertEquals(0.0f, point.getY());
        assertEquals(0.0f, point.getZ());
    }

    @Test
    public void scaleUniformlyWithNegativeScaleInvertsAndScalesCoordinates() {
        Point point = new Point(2.0f, 3.0f, 4.0f);

        point.scale(-2.0f);

        assertEquals(-4.0f, point.getX());
        assertEquals(-6.0f, point.getY());
        assertEquals(-8.0f, point.getZ());
    }

    @Test
    public void scaleNonUniformlyAppliesIndividualScalesPerAxis() {
        Point point = new Point(2.0f, 3.0f, 4.0f);

        point.scale(2.0f, 3.0f, 0.5f);

        assertEquals(4.0f, point.getX());
        assertEquals(9.0f, point.getY());
        assertEquals(2.0f, point.getZ());
    }

    @Test
    public void scaleNonUniformlyWithZeroScaleOnOneAxisSetsOnlyThatAxisToZero() {
        Point point = new Point(2.0f, 3.0f, 4.0f);

        point.scale(1.0f, 0.0f, 1.0f);

        assertEquals(2.0f, point.getX());
        assertEquals(0.0f, point.getY());
        assertEquals(4.0f, point.getZ());
    }

    @Test
    public void distanceCalculatesCorrectEuclideanDistance() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(3.0f, 4.0f, 0.0f);

        float distance = point1.distance(point2);

        assertEquals(5.0f, distance, 0.001f);
    }

    @Test
    public void distanceBetweenSamePointsIsZero() {
        Point point1 = new Point(5.0f, 10.0f, 15.0f);
        Point point2 = new Point(5.0f, 10.0f, 15.0f);

        float distance = point1.distance(point2);

        assertEquals(0.0f, distance, 0.001f);
    }

    @Test
    public void distanceWithNegativeCoordinatesCalculatesCorrectly() {
        Point point1 = new Point(-3.0f, -4.0f, 0.0f);
        Point point2 = new Point(0.0f, 0.0f, 0.0f);

        float distance = point1.distance(point2);

        assertEquals(5.0f, distance, 0.001f);
    }

    @Test
    public void distanceSquareReturnsPowerOfTwoOfDistance() {
        Point point1 = new Point(0.0f, 0.0f, 0.0f);
        Point point2 = new Point(3.0f, 4.0f, 0.0f);

        float distanceSquare = point1.distanceSquare(point2);

        assertEquals(25.0f, distanceSquare, 0.001f);
    }

    @Test
    public void distanceSquareBetweenSamePointsIsZero() {
        Point point1 = new Point(7.0f, 2.0f, 9.0f);
        Point point2 = new Point(7.0f, 2.0f, 9.0f);

        float distanceSquare = point1.distanceSquare(point2);

        assertEquals(0.0f, distanceSquare, 0.001f);
    }

    @Test
    public void horizontalDistanceSquaredIgnoresYCoordinateOnly() {
        Point point1 = new Point(0.0f, 100.0f, 0.0f);
        Point point2 = new Point(3.0f, 200.0f, 4.0f);

        float horizontalDistanceSquared = point1.horizontalDistanceSquared(point2);

        assertEquals(25.0f, horizontalDistanceSquared, 0.001f);
    }

    @Test
    public void horizontalDistanceSquaredWithSameXZCoordinatesIsZero() {
        Point point1 = new Point(5.0f, 10.0f, 7.0f);
        Point point2 = new Point(5.0f, 50.0f, 7.0f);

        float horizontalDistanceSquared = point1.horizontalDistanceSquared(point2);

        assertEquals(0.0f, horizontalDistanceSquared, 0.001f);
    }

    @Test
    public void horizontalDistanceIgnoresYCoordinateOnly() {
        Point point1 = new Point(0.0f, 100.0f, 0.0f);
        Point point2 = new Point(3.0f, 200.0f, 4.0f);

        float horizontalDistance = point1.horizontalDistance(point2);

        assertEquals(5.0f, horizontalDistance, 0.001f);
    }

    @Test
    public void horizontalDistanceWithSameXZCoordinatesIsZero() {
        Point point1 = new Point(5.0f, 10.0f, 7.0f);
        Point point2 = new Point(5.0f, 50.0f, 7.0f);

        float horizontalDistance = point1.horizontalDistance(point2);

        assertEquals(0.0f, horizontalDistance, 0.001f);
    }

    @Test
    public void toVectorCreatesVectorWithSameCoordinates() {
        Point point = new Point(1.5f, 2.5f, 3.5f);

        Vector vector = point.toVector();

        assertEquals(1.5f, vector.getX(), 0.001f);
        assertEquals(2.5f, vector.getY(), 0.001f);
        assertEquals(3.5f, vector.getZ(), 0.001f);
    }

    @Test
    public void toVectorWithZeroCoordinatesCreatesZeroVector() {
        Point point = new Point(0.0f, 0.0f, 0.0f);

        Vector vector = point.toVector();

        assertEquals(0.0f, vector.getX());
        assertEquals(0.0f, vector.getY());
        assertEquals(0.0f, vector.getZ());
    }

    @Test
    public void toVectorWithNegativeCoordinatesPreservesValues() {
        Point point = new Point(-1.0f, -2.0f, -3.0f);

        Vector vector = point.toVector();

        assertEquals(-1.0f, vector.getX());
        assertEquals(-2.0f, vector.getY());
        assertEquals(-3.0f, vector.getZ());
    }

    @Test
    public void cloneCreatesIndependentCopyWithSameCoordinates() {
        Point original = new Point(1.0f, 2.0f, 3.0f);

        Point cloned = original.clone();

        assertNotSame(original, cloned);
        assertEquals(original.getX(), cloned.getX());
        assertEquals(original.getY(), cloned.getY());
        assertEquals(original.getZ(), cloned.getZ());
    }

    @Test
    public void cloneCreatesIndependentCopyThatCanBeModifiedSeparately() {
        Point original = new Point(1.0f, 2.0f, 3.0f);
        Point cloned = original.clone();

        cloned.setX(5.0f);
        cloned.setY(6.0f);
        cloned.setZ(7.0f);

        assertEquals(1.0f, original.getX());
        assertEquals(2.0f, original.getY());
        assertEquals(3.0f, original.getZ());
        assertEquals(5.0f, cloned.getX());
        assertEquals(6.0f, cloned.getY());
        assertEquals(7.0f, cloned.getZ());
    }

    @Test
    public void cloneWithZeroCoordinatesCreatesZeroPoint() {
        Point original = new Point(0.0f, 0.0f, 0.0f);

        Point cloned = original.clone();

        assertEquals(0.0f, cloned.getX());
        assertEquals(0.0f, cloned.getY());
        assertEquals(0.0f, cloned.getZ());
    }

    @Test
    public void cloneWithNegativeCoordinatesPreservesValues() {
        Point original = new Point(-1.5f, -2.5f, -3.5f);

        Point cloned = original.clone();

        assertEquals(-1.5f, cloned.getX());
        assertEquals(-2.5f, cloned.getY());
        assertEquals(-3.5f, cloned.getZ());
    }
}
