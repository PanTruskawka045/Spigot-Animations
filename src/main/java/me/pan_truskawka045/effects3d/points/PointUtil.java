package me.pan_truskawka045.effects3d.points;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PointUtil {

    /**
     * Returns the minimum point
     *
     * @param first  first point
     * @param second second point
     * @return Point with minimum coordinates
     */
    public Point minPoint(Point first, Point second) {
        return new Point(Math.min(first.getX(), second.getX()), Math.min(first.getY(), second.getY()), Math.min(first.getZ(), second.getZ()));
    }

    /**
     * Returns the maximum point
     *
     * @param first  first point
     * @param second second point
     * @return Point with maximum coordinates
     */
    public Point maxPoint(Point first, Point second) {
        return new Point(Math.max(first.getX(), second.getX()), Math.max(first.getY(), second.getY()), Math.max(first.getZ(), second.getZ()));
    }

}
