package me.pan_truskawka045.effects3d.points;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.pan_truskawka045.effects3d.numbers.QuaternionNumber;
import me.pan_truskawka045.effects3d.vector.Vector;
import org.jetbrains.annotations.NotNull;


@Setter
@Getter
@ToString
/**
 * Represents a point in 3D space.
 */
public class Point implements Cloneable {

    private float x, y, z;

    /**
     * Constructs a new Point with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a new Point with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    public Point(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    /**
     * Shifts the point by the specified amounts.
     *
     * @param x the amount to shift the x-coordinate
     * @param y the amount to shift the y-coordinate
     * @param z the amount to shift the z-coordinate
     */
    public void shift(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    /**
     * Rotates the point by the specified angles around the x, y, and z axes.
     *
     * @param xAngle the angle to rotate around the x-axis
     * @param yAngle the angle to rotate around the y-axis
     * @param zAngle the angle to rotate around the z-axis
     */
    public void rotate(float xAngle, float yAngle, float zAngle) {
        rotateX(xAngle);
        rotateY(yAngle);
        rotateZ(zAngle);
    }

    /**
     * Rotates the point around the x-axis by the specified angle.
     *
     * @param angle the angle to rotate by
     */
    public void rotateX(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x;
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Rotates the point around the y-axis by the specified angle.
     *
     * @param angle the angle to rotate by
     */
    public void rotateY(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x * cos + this.z * sin;
        float y = this.y;
        float z = -this.x * sin + this.z * cos;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Rotates the point around the z-axis by the specified angle.
     *
     * @param angle the angle to rotate by
     */
    public void rotateZ(float angle) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        float z = this.z;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Rotates the point around the specified vector by the specified angle.
     *
     * @param angle  the angle to rotate by
     * @param vector the vector to rotate around
     */
    public void rotateAroundVector(float angle, @NotNull Vector vector) {
        Preconditions.checkNotNull(vector, "vector cannot be null");
        Preconditions.checkArgument(Float.isFinite(angle), "angle must be a finite number");
        Preconditions.checkArgument(!vector.isZero(), "vector cannot be zero vector");

        if (!vector.isNormalised()) {
            vector = vector.clone().normalise();
        }

        float sin = (float) Math.sin(angle / 2);
        float cos = (float) Math.cos(angle / 2);

        QuaternionNumber q1 = new QuaternionNumber(cos, vector.getX() * sin, vector.getY() * sin, vector.getZ() * sin);
        QuaternionNumber point = new QuaternionNumber(0, x, y, z);
        QuaternionNumber q2 = new QuaternionNumber(cos, -vector.getX() * sin, -vector.getY() * sin, -vector.getZ() * sin);

        QuaternionNumber result = q1.multiply(point).multiply(q2);

        this.x = (float) result.getImaginary();
        this.y = (float) result.getJ();
        this.z = (float) result.getK();
    }

    /**
     * Scales the point by the specified factor.
     *
     * @param scale the factor to scale by
     */
    public void scale(float scale) {
        Preconditions.checkArgument(Float.isFinite(scale), "scale must be a finite number");
        x *= scale;
        y *= scale;
        z *= scale;
    }

    /**
     * Scales the point by the specified factors for each coordinate.
     *
     * @param scaleX the factor to scale the x-coordinate by
     * @param scaleY the factor to scale the y-coordinate by
     * @param scaleZ the factor to scale the z-coordinate by
     */
    public void scale(float scaleX, float scaleY, float scaleZ) {
        Preconditions.checkArgument(Float.isFinite(scaleX), "scaleX must be a finite number");
        Preconditions.checkArgument(Float.isFinite(scaleY), "scaleY must be a finite number");
        Preconditions.checkArgument(Float.isFinite(scaleZ), "scaleZ must be a finite number");
        x *= scaleX;
        y *= scaleY;
        z *= scaleZ;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param point the other point
     * @return the distance between the two points
     */
    public float distance(@NotNull Point point) {
        Preconditions.checkNotNull(point, "point cannot be null");
        return (float) Math.sqrt(distanceSquare(point));
    }

    /**
     * Calculates the squared distance between this point and another point.
     *
     * @param point the other point
     * @return the squared distance between the two points
     */
    public float distanceSquare(@NotNull Point point) {
        Preconditions.checkNotNull(point, "point cannot be null");
        return (float) (Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2) + Math.pow(point.getZ() - z, 2));
    }

    /**
     * Calculates the squared horizontal distance between this point and another point.
     *
     * @param point the other point
     * @return the squared horizontal distance between the two points
     */
    public float horizontalDistanceSquared(@NotNull Point point) {
        Preconditions.checkNotNull(point, "point cannot be null");
        return (float) (Math.pow(point.getX() - x, 2) + Math.pow(point.getZ() - z, 2));
    }

    /**
     * Calculates the horizontal distance between this point and another point.
     *
     * @param point the other point
     * @return the horizontal distance between the two points
     */
    public float horizontalDistance(@NotNull Point point) {
        Preconditions.checkNotNull(point, "point cannot be null");
        return (float) Math.sqrt(horizontalDistanceSquared(point));
    }

    /**
     * Converts this point to a vector.
     *
     * @return a new vector with the same coordinates as this point
     */
    public Vector toVector() {
        return new Vector(x, y, z);
    }

    @Override
    public Point clone() {
        try {
            Point clone = (Point) super.clone();
            clone.x = x;
            clone.y = y;
            clone.z = z;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
