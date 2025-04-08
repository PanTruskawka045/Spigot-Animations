package me.pan_truskawka045.effects3d.numbers;

import lombok.Getter;

@Getter
public class QuaternionNumber extends ImaginaryNumber {

    private final double j;
    private final double k;

    public QuaternionNumber(double real, double i, double j, double k) {
        super(real, i);
        this.j = j;
        this.k = k;
    }

    public QuaternionNumber add(QuaternionNumber other) {
        return new QuaternionNumber(
                this.getValue() + other.getValue(),
                this.getImaginary() + other.getImaginary(),
                this.j + other.j,
                this.k + other.k
        );
    }

    public QuaternionNumber subtract(QuaternionNumber other) {
        return new QuaternionNumber(
                this.getValue() - other.getValue(),
                this.getImaginary() - other.getImaginary(),
                this.j - other.j,
                this.k - other.k
        );
    }

    /**
     * Result of multiplying two quaternions
     * <pre>z = this * other</pre>
     * @param other QuaternionNumber to multiply with
     * @return QuaternionNumber result of multiplication
     */
    public QuaternionNumber multiply(QuaternionNumber other) {
        double newValue = this.getValue() * other.getValue() - this.getImaginary() * other.getImaginary() - this.j * other.j - this.k * other.k;
        double newI = this.getValue() * other.getImaginary() + this.getImaginary() * other.getValue() + this.j * other.k - this.k * other.j;
        double newJ = this.getValue() * other.j + this.j * other.getValue() + this.k * other.getImaginary() - this.getImaginary() * other.k;
        double newK = this.getValue() * other.k + this.k * other.getValue() + this.getImaginary() * other.j - this.j * other.getImaginary();
        return new QuaternionNumber(newValue, newI, newJ, newK);
    }

}
