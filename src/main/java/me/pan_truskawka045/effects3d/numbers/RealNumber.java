package me.pan_truskawka045.effects3d.numbers;


import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
class RealNumber {

    private final double value;

    /**
     * Adds this real number to another real number.
     *
     * @param other the real number to add to this number
     * @return a new RealNumber representing the sum of this number and the other number
     * @throws NullPointerException if other is null
     */
    public @NotNull RealNumber add(@NotNull RealNumber other) {
        Preconditions.checkNotNull(other, "Other RealNumber must not be null");
        return new RealNumber(value + other.value);
    }

    /**
     * Subtracts another real number from this real number.
     *
     * @param other the real number to subtract from this number
     * @return a new RealNumber representing the difference of this number and the other number
     * @throws NullPointerException if other is null
     */
    public @NotNull RealNumber subtract(@NotNull RealNumber other) {
        Preconditions.checkNotNull(other, "Other RealNumber must not be null");
        return new RealNumber(value - other.value);
    }

    /**
     * Multiplies this real number by another real number.
     *
     * @param other the real number to multiply with this number
     * @return a new RealNumber representing the product of this number and the other number
     * @throws NullPointerException if other is null
     */
    public @NotNull RealNumber multiply(@NotNull RealNumber other) {
        Preconditions.checkNotNull(other, "Other RealNumber must not be null");
        return new RealNumber(value * other.value);
    }

    /**
     * Divides this real number by another real number.
     *
     * @param other the real number to divide this number by
     * @return a new RealNumber representing the quotient of this number and the other number
     * @throws ArithmeticException  if other represents zero (division by zero)
     * @throws NullPointerException if other is null
     */
    public @NotNull RealNumber divide(@NotNull RealNumber other) {
        Preconditions.checkNotNull(other, "Other RealNumber must not be null");
        Preconditions.checkArgument(other.value != 0, "Division by zero");
        return new RealNumber(value / other.value);
    }


    /**
     * Checks if this real number is zero.
     *
     * @return true if the value is exactly zero, false otherwise
     */
    public boolean isZero() {
        return this.getValue() == 0;
    }

}
