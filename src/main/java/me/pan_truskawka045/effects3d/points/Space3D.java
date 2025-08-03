package me.pan_truskawka045.effects3d.points;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.pan_truskawka045.effects3d.vector.Vector;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull Space3D shiftAll(float x, float y, float z) {
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
    public @NotNull Space3D addPoint(float x, float y, float z) {
        points.add(new Point(x, y, z));
        return this;
    }

    /**
     * Adds a point to the space
     *
     * @param point point to add
     * @return this
     */
    public @NotNull Space3D addPoint(@NotNull Point point) {
        Preconditions.checkNotNull(point, "point cannot be null");
        points.add(point);
        return this;
    }

    /**
     * Adds all points from given space to this space
     *
     * @param space source space to add points from
     * @return this
     */
    public @NotNull Space3D addAll(@NotNull Space3D space) {
        Preconditions.checkNotNull(space, "space cannot be null");
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
    public @NotNull Space3D rotateAll(float x, float y, float z) {
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
    public @NotNull Space3D rotateAllX(float angle) {
        points.forEach(point -> point.rotateX(angle));
        return this;
    }

    /**
     * Rotates all points by given values on the Y axis
     *
     * @param angle rotation angle (in radians)
     * @return this
     */
    public @NotNull Space3D rotateAllY(float angle) {
        points.forEach(point -> point.rotateY(angle));
        return this;
    }

    /**
     * Rotates all points by given values on the Z axis
     *
     * @param angle rotation angle (in radians)
     * @return this
     */
    public @NotNull Space3D rotateAllZ(float angle) {
        points.forEach(point -> point.rotateZ(angle));
        return this;
    }

    public @NotNull Space3D rotateAllAroundVector(float angle, @NotNull Vector vector) {
        Preconditions.checkNotNull(vector, "vector cannot be null");
        points.forEach(point -> point.rotateAroundVector(angle, vector));
        return this;
    }

    /**
     * Scales all points by the given value on the X axis
     *
     * @param scale scale factor
     * @return this
     */
    public @NotNull Space3D scaleX(float scale) {
        points.forEach(point -> point.setX(point.getX() * scale));
        return this;
    }

    /**
     * Scales all points by the given value on the Y axis
     *
     * @param scale scale factor
     * @return this
     */
    public @NotNull Space3D scaleY(float scale) {
        points.forEach(point -> point.setY(point.getY() * scale));
        return this;
    }

    /**
     * Scales all points by the given value on the Z axis
     *
     * @param scale scale factor
     * @return this
     */
    public @NotNull Space3D scaleZ(float scale) {
        points.forEach(point -> point.setZ(point.getZ() * scale));
        return this;
    }

    /**
     * Scales all points by the given value on all axes
     *
     * @param scale scale factor
     * @return this
     */
    public @NotNull Space3D scale(float scale) {
        points.forEach(point -> point.scale(scale));
        return this;
    }


    /**
     * Scales all points by the given values on each axis.
     *
     * @param scaleX scale factor for the X axis
     * @param scaleY scale factor for the Y axis
     * @param scaleZ scale factor for the Z axis
     * @return this
     */
    public @NotNull Space3D scale(float scaleX, float scaleY, float scaleZ) {
        points.forEach(point -> point.scale(scaleX, scaleY, scaleZ));
        return this;
    }

    /**
     * Returns a copy of this space with shifted points
     *
     * @param scale scale factor
     * @return new space with scaled points
     */
    public @NotNull Space3D scaledCopy(float scale) {
        Preconditions.checkArgument(!Float.isNaN(scale), "scale cannot be NaN");
        Preconditions.checkArgument(Float.isFinite(scale), "scale must be finite");
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
    public @NotNull Space3D scaledCopy(float scaleX, float scaleY, float scaleZ) {
        Preconditions.checkArgument(!Float.isNaN(scaleX), "scaleX cannot be NaN");
        Preconditions.checkArgument(!Float.isNaN(scaleY), "scaleY cannot be NaN");
        Preconditions.checkArgument(!Float.isNaN(scaleZ), "scaleZ cannot be NaN");
        Preconditions.checkArgument(Float.isFinite(scaleX), "scaleX must be finite");
        Preconditions.checkArgument(Float.isFinite(scaleY), "scaleY must be finite");
        Preconditions.checkArgument(Float.isFinite(scaleZ), "scaleZ must be finite");
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
    public @NotNull List<Point> allInRange(@NotNull Point first, @NotNull Point second) {
        Preconditions.checkNotNull(first, "first point cannot be null");
        Preconditions.checkNotNull(second, "second point cannot be null");
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
    public @NotNull List<Point> allOutsideRange(@NotNull Point first, @NotNull Point second) {
        Preconditions.checkNotNull(first, "first point cannot be null");
        Preconditions.checkNotNull(second, "second point cannot be null");
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
    public @NotNull List<Point> allInDistance(@NotNull Point point, float distance) {
        Preconditions.checkNotNull(point, "point cannot be null");
        Preconditions.checkArgument(distance >= 0, "distance must be non-negative");
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
     * Returns all points outside the given distance from the reference point
     *
     * @param point    reference point
     * @param distance minimum distance
     * @return list of points outside distance
     */
    public @NotNull List<Point> allOutsideDistance(@NotNull Point point, float distance) {
        Preconditions.checkNotNull(point, "point cannot be null");
        Preconditions.checkArgument(distance >= 0, "distance must be non-negative");

        List<Point> outsideDistance = new ArrayList<>();
        float distanceSquare = distance * distance;
        this.points.forEach(point1 -> {
            if (point.distanceSquare(point1) > distanceSquare) {
                outsideDistance.add(point1);
            }
        });
        return outsideDistance;
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
    public @NotNull Space3D drawLine(@NotNull Point point1, @NotNull Point point2, float distanceBetweenPoints) {
        Preconditions.checkNotNull(point1, "point1 cannot be null");
        Preconditions.checkNotNull(point2, "point2 cannot be null");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");

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
    public @NotNull Space3D drawCircle(@NotNull Point center, float radius, float distanceBetweenPoints) {
        Preconditions.checkNotNull(center, "center cannot be null");
        Preconditions.checkArgument(radius > 0, "radius must be positive");
        Preconditions.checkArgument(distanceBetweenPoints > 0, "distanceBetweenPoints must be positive");
        this.points.addAll(Space3DGraphics.drawCircle(center, radius, distanceBetweenPoints));
        return this;
    }
}
