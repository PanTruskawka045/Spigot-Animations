package me.pan_truskawka045.effects3d.points;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@SuppressWarnings("unused")
public class Space3D {

    private final List<Point> points = new ArrayList<>();

    /**
     * Moves all points by given values
     *
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @param z z coordinate of the point
     * @return this
     */
    public Space3D shiftAll(float x, float y, float z) {
        points.forEach(point -> point.shift(x, y, z));
        return this;
    }

    /**
     * Adds a point to the space
     *
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     * @param z z coordinate of the point
     * @return this
     */
    public Space3D addPoint(float x, float y, float z) {
        points.add(new Point(x, y, z));
        return this;
    }

    /**
     * Adds a point to the space
     *
     * @param point point to add
     * @return this
     */
    public Space3D addPoint(Point point) {
        points.add(point);
        return this;
    }

    /**
     * Adds all points from given space to this space
     *
     * @param space source space to add points from
     * @return this
     */
    public Space3D addAll(Space3D space) {
        points.addAll(space.getPoints());
        return this;
    }

    /**
     * Rotates all points by given values
     *
     * @param x x rotation angle (in radians)
     * @param y y rotation angle (in radians)
     * @param z z rotation angle (in radians)
     * @return this
     */
    public Space3D rotateAll(float x, float y, float z) {
        //TODO optimize (calculate sins and cosines only once)
        points.forEach(point -> point.rotate(x, y, z));
        return this;
    }

    /**
     * Rotates all points by given values on the X axis
     *
     * @param angle rotation angle (in radians)
     * @return this
     */
    public Space3D rotateAllX(float angle) {
        points.forEach(point -> point.rotateX(angle));
        return this;
    }

    /**
     * Rotates all points by given values on the Y axis
     *
     * @param angle rotation angle (in radians)
     * @return this
     */
    public Space3D rotateAllY(float angle) {
        points.forEach(point -> point.rotateY(angle));
        return this;
    }

    /**
     * Rotates all points by given values on the Z axis
     *
     * @param angle rotation angle (in radians)
     * @return this
     */
    public Space3D rotateAllZ(float angle) {
        points.forEach(point -> point.rotateZ(angle));
        return this;
    }

    /**
     * Scales all points by the given value on the X axis
     *
     * @param scale scale factor
     * @return this
     */
    public Space3D scaleX(float scale) {
        points.forEach(point -> point.setX(point.getX() * scale));
        return this;
    }

    /**
     * Scales all points by the given value on the Y axis
     *
     * @param scale scale factor
     * @return this
     */
    public Space3D scaleY(float scale) {
        points.forEach(point -> point.setY(point.getY() * scale));
        return this;
    }

    /**
     * Scales all points by the given value on the Y axis
     *
     * @param scale scale factor
     * @return this
     */
    public Space3D scaleZ(float scale) {
        points.forEach(point -> point.setZ(point.getZ() * scale));
        return this;
    }

    /**
     * Scales all points by the given values
     *
     * @param scaleX scale factor on the X axis
     * @param scaleY scale factor on the Y axis
     * @param scaleZ scale factor on the Z axis
     * @return this
     */
    public Space3D scale(float scaleX, float scaleY, float scaleZ) {
        return this.scaleX(scaleX).scaleY(scaleY).scaleZ(scaleZ);
    }

    /**
     * Scales all points by the given value on all 3 axes
     *
     * @param scale scale factor
     * @return this
     */
    public Space3D scale(float scale) {
        return this.scale(scale, scale, scale);
    }

    /**
     * Returns a copy of this space with shifted points
     *
     * @param scale scale factor
     * @return new space with scaled points
     */
    public Space3D scaledCopy(float scale) {
        return this.scaledCopy(scale, scale, scale);
    }

    /**
     * Returns a copy of this space with scaled points
     *
     * @param scaleX scale factor on the X axis
     * @param scaleY scale factor on the Y axis
     * @param scaleZ scale factor on the Z axis
     * @return new space with scaled points
     */
    public Space3D scaledCopy(float scaleX, float scaleY, float scaleZ) {
        Space3D space = new Space3D();
        this.points.forEach(point -> space.addPoint(point.clone()));
        space.scale(scaleX, scaleY, scaleZ);
        return space;
    }


    /**
     * Returns all points in the given range
     *
     * @param first  first point
     * @param second second point
     * @return list of points
     */
    public List<Point> allInRange(Point first, Point second) {
        Point min = PointUtil.minPoint(first, second);
        Point max = PointUtil.maxPoint(first, second);
        List<Point> points = new ArrayList<>();
        this.points.forEach(point -> {
            if (point.getX() >= min.getX() && point.getX() <= max.getX() && point.getY() >= min.getY() && point.getY() <= max.getY() && point.getZ() >= min.getZ() && point.getZ() <= max.getZ()) {
                points.add(point);
            }
        });
        return points;
    }

    /**
     * Returns all points outside the given range
     *
     * @param first  first point
     * @param second second point
     * @return list of points
     */
    public List<Point> allOutsideRange(Point first, Point second) {
        Point min = PointUtil.minPoint(first, second);
        Point max = PointUtil.maxPoint(first, second);
        List<Point> points = new ArrayList<>();
        this.points.forEach(point -> {
            if (point.getX() < min.getX() || point.getX() > max.getX() || point.getY() < min.getY() || point.getY() > max.getY() || point.getZ() < min.getZ() || point.getZ() > max.getZ()) {
                points.add(point);
            }
        });
        return points;
    }

    /**
     * Returns all points in the given distance
     *
     * @param point    point
     * @param distance distance
     * @return list of points
     */
    public List<Point> allInDistance(Point point, float distance) {
        List<Point> points = new ArrayList<>();
        float distanceSquare = distance * distance;
        this.points.forEach(point1 -> {
            if (point.distanceSquare(point1) <= distanceSquare) {
                points.add(point1);
            }
        });
        return points;
    }

    /**
     * Returns all points outside the given distance
     *
     * @param point    point
     * @param distance distance
     * @return list of points
     */
    public List<Point> allOutsideDistance(Point point, float distance) {
        List<Point> points = new ArrayList<>();
        float distanceSquare = distance * distance;
        this.points.forEach(point1 -> {
            if (point.distanceSquare(point1) > distanceSquare) {
                points.add(point1);
            }
        });
        return points;
    }

    /*

        GRAPHIC UTILS

     */

    /**
     * Draws a line between two points
     *
     * @param point1                first point
     * @param point2                second point
     * @param distanceBetweenPoints distance between points
     * @return this
     * @see Space3DGraphics#drawLine for more info
     */
    public Space3D drawLine(Point point1, Point point2, float distanceBetweenPoints) {
        this.points.addAll(Space3DGraphics.drawLine(point1, point2, distanceBetweenPoints));
        return this;
    }

    /**
     * Draws a circle
     *
     * @param center                center of the circle
     * @param radius                radius of the circle
     * @param distanceBetweenPoints distance between points
     * @return this
     * @see Space3DGraphics#drawCircle for more info
     */
    public Space3D drawCircle(Point center, float radius, float distanceBetweenPoints) {
        this.points.addAll(Space3DGraphics.drawCircle(center, radius, distanceBetweenPoints));
        return this;
    }
}
