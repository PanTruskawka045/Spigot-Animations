package me.pan_truskawka045.effects3d.points;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
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
        List<Point> points = new ArrayList<>();
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
     * @param center                center of the circle
     * @param radius                radius of the circle
     * @param distanceBetweenPoints distance between two points (might change a bit)
     * @return list of created points
     */
    public List<Point> drawCircle(Point center, float radius, float distanceBetweenPoints) {
        List<Point> points = new ArrayList<>();
        float circumference = (float) (2 * Math.PI * radius);
        int pointsCount = (int) (circumference / distanceBetweenPoints);
        float step = (float) (Math.PI * 2 / pointsCount);

        for (float i = 0; i < Math.PI * 2; i += step) {
            points.add(new Point(
                    center.getX() + radius * (float) Math.cos(i),
                    center.getY(),
                    center.getY() + radius * (float) Math.sin(i)));
        }
        return points;
    }


}
