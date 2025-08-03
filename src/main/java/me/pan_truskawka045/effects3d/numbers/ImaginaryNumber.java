package me.pan_truskawka045.effects3d.numbers;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a complex number (also known as an imaginary number) with real and imaginary components.
 * This class extends RealNumber and adds support for complex number arithmetic operations.
 *
 * <p>A complex number is typically written as a + bi, where 'a' is the real part and 'b' is the
 * imaginary part. This class stores both components and provides methods for complex arithmetic.</p>
 *
 * <p>Like its parent class, ImaginaryNumber instances are immutable - all operations return new
 * instances rather than modifying the existing object.</p>
 *
 * @author pan_truskawka045
 * @version 1.0
 * @since 1.0
 */
@Getter
public class ImaginaryNumber extends RealNumber {

    /**
     * The imaginary component of the complex number (coefficient of 'i').
     */
    private final double imaginary;

    /**
     * Constructs a new complex number with the specified real and imaginary parts.
     *
     * @param value     the real part of the complex number
     * @param imaginary the imaginary part of the complex number
     */
    public ImaginaryNumber(double value, double imaginary) {
        super(value);
        this.imaginary = imaginary;
    }

    /**
     * Adds this complex number to another complex number.
     * The addition is performed component-wise: (a + bi) + (c + di) = (a + c) + (b + d)i
     *
     * @param other the complex number to add to this number
     * @return a new ImaginaryNumber representing the sum of the two complex numbers
     * @throws NullPointerException if other is null
     */
    public @NotNull ImaginaryNumber add(@NotNull ImaginaryNumber other) {
        Preconditions.checkNotNull(other, "Other ImaginaryNumber must not be null");
        return new ImaginaryNumber(this.getValue() + other.getValue(), this.imaginary + other.imaginary);
    }

    /**
     * Subtracts another complex number from this complex number.
     * The subtraction is performed component-wise: (a + bi) - (c + di) = (a - c) + (b - d)i
     *
     * @param other the complex number to subtract from this number
     * @return a new ImaginaryNumber representing the difference of the two complex numbers
     * @throws NullPointerException if other is null
     */
    public @NotNull ImaginaryNumber subtract(@NotNull ImaginaryNumber other) {
        Preconditions.checkNotNull(other, "Other ImaginaryNumber must not be null");
        return new ImaginaryNumber(this.getValue() - other.getValue(), this.imaginary - other.imaginary);
    }

    /**
     * Multiplies this complex number by another complex number.
     * Uses the formula: (a + bi) * (c + di) = (ac - bd) + (ad + bc)i
     *
     * @param other the complex number to multiply with this number
     * @return a new ImaginaryNumber representing the product of the two complex numbers
     * @throws NullPointerException if other is null
     */
    public @NotNull ImaginaryNumber multiply(@NotNull ImaginaryNumber other) {
        Preconditions.checkNotNull(other, "Other ImaginaryNumber must not be null");
        double realPart = this.getValue() * other.getValue() - this.imaginary * other.imaginary;
        double imaginaryPart = this.getValue() * other.imaginary + this.imaginary * other.getValue();
        return new ImaginaryNumber(realPart, imaginaryPart);
    }

    /**
     * Divides this complex number by another complex number.
     * Uses the formula: (a + bi) / (c + di) = [(ac + bd) + (bc - ad)i] / (c² + d²)
     *
     * @param other the complex number to divide this number by
     * @return a new ImaginaryNumber representing the quotient of the two complex numbers
     * @throws ArithmeticException  if other represents zero (both real and imaginary parts are zero)
     * @throws NullPointerException if other is null
     */
    public @NotNull ImaginaryNumber divide(@NotNull ImaginaryNumber other) {
        Preconditions.checkNotNull(other, "Other ImaginaryNumber must not be null");
        Preconditions.checkArgument(!other.isZero(), "Division by zero complex number");
        double denominator = other.getValue() * other.getValue() + other.imaginary * other.imaginary;
        double realPart = (this.getValue() * other.getValue() + this.imaginary * other.imaginary) / denominator;
        double imaginaryPart = (this.imaginary * other.getValue() - this.getValue() * other.imaginary) / denominator;
        return new ImaginaryNumber(realPart, imaginaryPart);
    }

    /**
     * Checks if this complex number is equal to zero (both real and imaginary parts are zero).
     *
     * @return true if both the real and imaginary parts are zero, false otherwise
     */
    public boolean isZero() {
        return this.getValue() == 0 && this.imaginary == 0;
    }

}
