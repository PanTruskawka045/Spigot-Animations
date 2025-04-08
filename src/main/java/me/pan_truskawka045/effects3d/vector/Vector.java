package me.pan_truskawka045.effects3d.vector;

import me.pan_truskawka045.effects3d.points.Point;

public class Vector extends Point implements Cloneable {

    public Vector(float x, float y, float z) {
        super(x, y, z);
    }

    public Vector(double x, double y, double z) {
        super((float) x, (float) y, (float) z);
    }

    public Vector normalise() {
        if (this.isZero()) {
            return new Vector(0, 0, 0);
        }
        float length = (float) Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());

        return new Vector(this.getX() / length, this.getY() / length, this.getZ() / length);
    }

    public boolean isNormalised() {
        return this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() == 1;
    }

    public boolean isZero() {
        return this.getX() == 0 && this.getY() == 0 && this.getZ() == 0;
    }

    @Override
    public Vector clone() {
        Vector clone = (Vector) super.clone();
        clone.setX(this.getX());
        clone.setY(this.getY());
        clone.setZ(this.getZ());
        return clone;
    }
}
