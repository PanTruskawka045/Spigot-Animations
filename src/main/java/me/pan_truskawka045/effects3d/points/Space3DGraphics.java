package me.pan_truskawka045.effects3d.points;

import com.google.common.base.Preconditions;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Utilities to draw shapes in 3D space
 *
 * @author pan_truskawka045
 */
@UtilityClass
public class Space3DGraphics {

    /**
     * Draws a line between two points
     *
     * @param point1                first point
     * @param point2                second point
     * @param distanceBetweenPoints distance between two points (might change a bit)
     * @return list of created points
     */
    public @NotNull List<Point> drawLine(@NotNull Point point1, @NotNull Point point2, float distanceBetweenPoints) {
        Preconditions.checkNotNull(point1, "point1 cannot be null");
        Preconditions.checkNotNull(point2, "point2 cannot be null");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");

        double diffX = point2.getX() - point1.getX();
        double diffY = point2.getY() - point1.getY();
        double diffZ = point2.getZ() - point1.getZ();
        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
        int pointsCount = (int) (distance / distanceBetweenPoints);
        double xStep = diffX / pointsCount;
        double yStep = diffY / pointsCount;
        double zStep = diffZ / pointsCount;
        List<Point> points = new LinkedList<>();
        for (int i = 0; i <= pointsCount; i++) {
            points.add(new Point(
                    point1.getX() + xStep * i,
                    point1.getY() + yStep * i,
                    point1.getZ() + zStep * i));
        }
        return points;
    }


    /**
     * Draws a circle
     * The circle is drawn in the XZ plane (Y is constant)
     *
     * @param center                center of the circle
     * @param radius                radius of the circle
     * @param distanceBetweenPoints distance between two points (might change a bit)
     * @return list of created points
     */
    public @NotNull List<Point> drawCircle(@NotNull Point center, float radius, float distanceBetweenPoints) {
        Preconditions.checkNotNull(center, "center cannot be null");
        Preconditions.checkArgument(radius > 0, "radius must be positive");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");

        List<Point> points = new LinkedList<>();
        float circumference = (float) (2 * Math.PI * radius);
        int pointsCount = (int) (circumference / distanceBetweenPoints);
        float step = (float) (Math.PI * 2 / pointsCount);

        for (float i = 0; i < Math.PI * 2; i += step) {
            points.add(new Point(
                    center.getX() + radius * (float) Math.cos(i),
                    center.getY(),
                    center.getZ() + radius * (float) Math.sin(i)));
        }
        return points;
    }

    public @NotNull Point[] bezierCurve(float distanceBetweenPoints, int precision, @NotNull Point... points) {
        Preconditions.checkNotNull(points, "points array cannot be null");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");
        Preconditions.checkArgument(precision > 0, "precision must be positive");

        for (int i = 0; i < points.length; i++) {
            Preconditions.checkNotNull(points[i], "Point at index " + i + " cannot be null");
        }

        Point[] values = new Point[precision];
        for (int i = 0; i < precision; i++) {
            float t = (float) i / (precision - 1);
            values[i] = bezierCurveValue(t, points);
        }

        float distance = 0;

        for (int i = 0; i < values.length - 1; i++) {
            distance += values[i].distance(values[i + 1]);
        }

        return bezierCurve((int) (distance / distanceBetweenPoints), points);
    }

    public @NotNull Point[] bezierCurve(int amountOfPoints, @NotNull Point... points) {
        Preconditions.checkNotNull(points, "points array cannot be null");
        Preconditions.checkArgument(amountOfPoints > 0, "amountOfPoints must be positive");

        for (int i = 0; i < points.length; i++) {
            Preconditions.checkNotNull(points[i], "Point at index " + i + " cannot be null");
        }

        Point[] values = new Point[amountOfPoints];
        for (int i = 0; i < amountOfPoints; i++) {
            float t = (float) i / (amountOfPoints - 1);
            values[i] = bezierCurveValue(t, points);
        }

        float[] distanceLookUpTable = new float[amountOfPoints];
        distanceLookUpTable[0] = 0;
        for (int i = 0; i < values.length - 1; i++) {

            distanceLookUpTable[i + 1] = distanceLookUpTable[i] + values[i].distance(values[i + 1]);
        }

        Point[] remappedValues = new Point[amountOfPoints];
        for (int i = 0; i < amountOfPoints; i++) {
            float t = distToT(distanceLookUpTable, distanceLookUpTable[amountOfPoints - 1] * i / (amountOfPoints - 1));
            remappedValues[i] = bezierCurveValue(t, points);
        }
        return remappedValues;
    }

    public @NotNull Point bezierCurveValue(float t, @NotNull Point... points) {
        Preconditions.checkNotNull(points, "points array cannot be null");

        for (int i = 0; i < points.length; i++) {
            Preconditions.checkNotNull(points[i], "Point at index " + i + " cannot be null");
        }

        if (points.length == 0) {
            throw new IllegalArgumentException("At least one point is required");
        }
        if (points.length == 1) {
            return points[0];
        }
        if (points.length == 2) {
            return new Point(
                    points[0].getX() + t * (points[1].getX() - points[0].getX()),
                    points[0].getY() + t * (points[1].getY() - points[0].getY()),
                    points[0].getZ() + t * (points[1].getZ() - points[0].getZ())
            );
        }
        Point[] newPoints = new Point[points.length - 1];
        for (int i = 0; i < newPoints.length; i++) {
            newPoints[i] = lerp(points[i], points[i + 1], t);
        }
        return bezierCurveValue(t, newPoints);
    }


    public float distToT(@NotNull float[] LUT, float distance) {
        Preconditions.checkNotNull(LUT, "LUT array cannot be null");
        Preconditions.checkArgument(LUT.length > 0, "LUT array cannot be empty");

        float arcLength = LUT[LUT.length - 1]; // total arc length
        int n = LUT.length; // sample count

        if (distance >= 0 && distance <= arcLength) { // check if the value is within the length of the curve
            for (int i = 0; i < n - 1; i++) { // iterate through the list to find which segment our distance lies within
                if (within(distance, LUT[i], LUT[i + 1])) { // check if our input distance lies between the two distances
                    return remap(
                            distance,
                            LUT[i],
                            LUT[i + 1],
                            i / (float) (n - 1), // prev t-value
                            (i + 1) / (float) (n - 1) // next t-value
                    );
                }
            }
        }

        return distance / arcLength; // distance is outside the length of the curve - extrapolate values outside
    }

    private boolean within(float value, float min, float max) {
        return value >= min && value <= max;
    }

    private float remap(float value, float inMin, float inMax, float outMin, float outMax) {
        return outMin + (value - inMin) * (outMax - outMin) / (inMax - inMin);
    }


    public @NotNull List<Point> semiLightningLine(@NotNull Point start,
                                                  int bends,
                                                  float horizontalAngle,
                                                  float verticalAngle,
                                                  float distanceBetweenPoints,
                                                  float distanceBetweenBends,
                                                  @NotNull Point direction) {
        Preconditions.checkNotNull(start, "start point cannot be null");
        Preconditions.checkNotNull(direction, "direction point cannot be null");
        Preconditions.checkArgument(bends >= 0, "bends must be non-negative");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");
        Preconditions.checkArgument(distanceBetweenBends > 0, "distanceBetweenBends must be positive");

        List<Point> points = new LinkedList<>();

        Point lastPoint = start;

        for (int i = 0; i < bends; i++) {
            float distX = direction.getX() - lastPoint.getX();
            float distY = direction.getY() - lastPoint.getY();
            float distZ = direction.getZ() - lastPoint.getZ();

            float pointHorizontalAngle = (float) Math.atan2(distZ, distX);
            float pointVerticalAngle = (float) Math.atan2(distY, Math.sqrt(distX * distX + distZ * distZ));

            pointHorizontalAngle += (float) (Math.random() * horizontalAngle - horizontalAngle / 2);
            pointVerticalAngle += (float) (Math.random() * verticalAngle - verticalAngle / 2);

            Point point = new Point(1, 0, 0);
            point.rotateZ(pointVerticalAngle);
            point.rotateY(pointHorizontalAngle);
            point.scale(distanceBetweenBends);
            points.addAll(drawLine(lastPoint, point, distanceBetweenPoints));
            lastPoint = point;
            direction = point.clone();
            direction.scale(2);
        }

        return points;
    }

    /**
     * Linearly interpolates between two points in 3D space.
     *
     * @param point  the starting {@link Point}
     * @param target the target {@link Point}
     * @param alpha  the interpolation factor (0.0 = point, 1.0 = target)
     * @return a new {@link Point} representing the interpolated position
     */
    public @NotNull Point lerp(@NotNull Point point, @NotNull Point target, float alpha) {
        Preconditions.checkNotNull(point, "point cannot be null");
        Preconditions.checkNotNull(target, "target cannot be null");

        return new Point(
                point.getX() + (target.getX() - point.getX()) * alpha,
                point.getY() + (target.getY() - point.getY()) * alpha,
                point.getZ() + (target.getZ() - point.getZ()) * alpha
        );
    }

    /**
     * Draws a quadratic curve between two points in 3D space.
     * The curve is defined by a quadratic function with the specified factor, and points are generated along the curve
     * at intervals determined by distanceBetweenPoints.
     *
     * @param point1                the starting {@link Point} of the curve
     * @param point2                the ending {@link Point} of the curve
     * @param aFactor               the quadratic factor that determines the curvature
     * @param distanceBetweenPoints the distance between each generated point along the curve
     * @return a list of {@link Point} objects representing the quadratic curve
     */
    public @NotNull List<Point> drawQuadratic(@NotNull Point point1, @NotNull Point point2, float aFactor, float distanceBetweenPoints) {
        Preconditions.checkNotNull(point1, "point1 cannot be null");
        Preconditions.checkNotNull(point2, "point2 cannot be null");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");

        List<Point> points = new LinkedList<>();
        float horizontalDistance = point1.horizontalDistance(point2);

        Preconditions.checkArgument(horizontalDistance > 0, "horizontal distance between points must be positive");

        float halfDistance = horizontalDistance / 2;
        float currentX = -halfDistance;

        while (currentX <= halfDistance) {
            float normalizedX = (currentX + halfDistance) / horizontalDistance;
            Point linear1 = lerp(point1, point2, normalizedX);
            float quadratic1 = aFactor * (currentX - halfDistance) * (currentX + halfDistance);
            float y1 = linear1.getY() + quadratic1;

            float currentX2 = currentX + distanceBetweenPoints;
            float normalizedX2 = (currentX2 + halfDistance) / horizontalDistance;

            Point linear2 = lerp(point1, point2, normalizedX2);
            float quadratic2 = aFactor * (currentX2 - halfDistance) * (currentX2 + halfDistance);
            float y2 = linear2.getY() + quadratic2;

            float diffY = y2 - y1;
            float distance = (float) Math.sqrt(diffY * diffY + distanceBetweenPoints * distanceBetweenPoints);

            float derivedDistance = distanceBetweenPoints / distance;

            float actualNormalizedX = (currentX + derivedDistance + halfDistance) / horizontalDistance;

            Point linear3 = lerp(point1, point2, actualNormalizedX);
            float quadratic3 = aFactor * (currentX + derivedDistance - halfDistance) * (currentX + derivedDistance + halfDistance);

            float y3 = linear3.getY() + quadratic3;

            points.add(new Point(linear3.getX(), y3, linear3.getZ()));
            currentX += derivedDistance;
        }
        return points;
    }

    /**
     * Computes the quadratic coefficient \(a\) for a symmetric quadratic curve that
     * reaches the specified peak height at the midpoint between two points.
     * <p>
     * The quadratic shape used is centered at the midpoint and has the form
     * y = a * x^2 (relative to the midpoint). Given a horizontal distance d between
     * the two points and desired peak height h, the coefficient is:
     * a = 4 * h / d^2
     * <p>
     * Note: if the horizontal distance between the points is zero, the result may
     * be Infinity or NaN.
     *
     * @param point1     first end point (must not be null)
     * @param point2     second end point (must not be null)
     * @param peakHeight desired peak vertex height above the line connecting the two points
     * @return the quadratic coefficient \`a\` producing the requested peak height
     */
    public float computeQuadraticCoefficient(@NotNull Point point1, @NotNull Point point2, float peakHeight) {
        Preconditions.checkNotNull(point1, "point1 cannot be null");
        Preconditions.checkNotNull(point2, "point2 cannot be null");

        float horizontalDistance = point1.horizontalDistance(point2);

        Preconditions.checkArgument(horizontalDistance > 0, "horizontal distance between points must be positive");

        return (4 * peakHeight) / (horizontalDistance * horizontalDistance);
    }

    /**
     * Draws the wireframe of a cube defined by two opposite corners.
     *
     * @param point1                one corner of the cube
     * @param point2                the opposite corner of the cube
     * @param distanceBetweenPoints the distance between each generated point along the edges
     * @return a list of {@link Point} objects representing the wireframe of the cube
     */
    public @NotNull List<Point> drawCubeWireframes(@NotNull Point point1, @NotNull Point point2, float distanceBetweenPoints) {
        Preconditions.checkNotNull(point1, "point1 cannot be null");
        Preconditions.checkNotNull(point2, "point2 cannot be null");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");

        List<Point> points = new LinkedList<>();

        // Bottom face
        double minX = Math.min(point1.getX(), point2.getX());
        double maxX = Math.max(point1.getX(), point2.getX());
        double minY = Math.min(point1.getY(), point2.getY());
        double maxY = Math.max(point1.getY(), point2.getY());
        double minZ = Math.min(point1.getZ(), point2.getZ());
        double maxZ = Math.max(point1.getZ(), point2.getZ());

        Point p000 = new Point(minX, minY, minZ);
        Point p100 = new Point(maxX, minY, minZ);
        Point p010 = new Point(minX, maxY, minZ);
        Point p110 = new Point(maxX, maxY, minZ);
        Point p001 = new Point(minX, minY, maxZ);
        Point p101 = new Point(maxX, minY, maxZ);
        Point p011 = new Point(minX, maxY, maxZ);
        Point p111 = new Point(maxX, maxY, maxZ);

        // Bottom face
        points.addAll(drawLine(p000, p100, distanceBetweenPoints));
        points.addAll(drawLine(p100, p101, distanceBetweenPoints));
        points.addAll(drawLine(p101, p001, distanceBetweenPoints));
        points.addAll(drawLine(p001, p000, distanceBetweenPoints));

        // Top face
        points.addAll(drawLine(p010, p110, distanceBetweenPoints));
        points.addAll(drawLine(p110, p111, distanceBetweenPoints));
        points.addAll(drawLine(p111, p011, distanceBetweenPoints));
        points.addAll(drawLine(p011, p010, distanceBetweenPoints));

        // Vertical edges
        points.addAll(drawLine(p000, p010, distanceBetweenPoints));
        points.addAll(drawLine(p100, p110, distanceBetweenPoints));
        points.addAll(drawLine(p101, p111, distanceBetweenPoints));
        points.addAll(drawLine(p001, p011, distanceBetweenPoints));

        return points;
    }

}
