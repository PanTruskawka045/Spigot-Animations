package me.pan_truskawka045.effects3d.points;

import lombok.experimental.UtilityClass;

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
    public List<Point> drawLine(Point point1, Point point2, float distanceBetweenPoints) {
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
    public List<Point> drawCircle(Point center, float radius, float distanceBetweenPoints) {
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

    public Point[] bezierCurve(float distanceBetweenPoints, int precision, Point... points) {
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

    public Point[] bezierCurve(int amountOfPoints, Point... points) {
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

    public Point bezierCurveValue(float t, Point... points) {
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
            newPoints[i] = new Point(
                    points[i].getX() + t * (points[i + 1].getX() - points[i].getX()),
                    points[i].getY() + t * (points[i + 1].getY() - points[i].getY()),
                    points[i].getZ() + t * (points[i + 1].getZ() - points[i].getZ())
            );
        }
        return bezierCurveValue(t, newPoints);
    }


    public float distToT(float[] LUT, float distance) {
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


    public List<Point> semiLightningLine(Point start,
                                         int bends,
                                         float horizontalAngle,
                                         float verticalAngle,
                                         float distanceBetweenPoints,
                                         float distanceBetweenBends,
                                         Point direction) {
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

    public Point lerp(Point point, Point target, float alpha) {
        return new Point(
                point.getX() + (target.getX() - point.getX()) * alpha,
                point.getY() + (target.getY() - point.getY()) * alpha,
                point.getZ() + (target.getZ() - point.getZ()) * alpha
        );
    }


}
