package me.pan_truskawka045.effects3d.points;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


public class Point implements Cloneable{

    @Getter
    @Setter
    private float x, y, z;

    public Point(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    public void shift(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void rotate(float xAngle, float yAngle, float zAngle) {
        rotateX(xAngle);
        rotateY(yAngle);
        rotateZ(zAngle);
    }

    public void rotateX(float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x;
        float y = this.y * cos - this.z * sin;
        float z = this.y * sin + this.z * cos;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotateY(float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x * cos + this.z * sin;
        float y = this.y;
        float z = -this.x * sin + this.z * cos;

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void rotateZ(float angle){
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float x = this.x * cos - this.y * sin;
        float y = this.x * sin + this.y * cos;
        float z = this.z;

        this.x = x;
        this.y = y;
        this.z = z;
    }


    public float distance(Point point) {
        return (float) Math.sqrt(distanceSquare(point));
    }

    public float distanceSquare(Point point){
        return (float) (Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2) + Math.pow(point.getZ() - z, 2));
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
