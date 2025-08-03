package me.pan_truskawka045.effects3d.numbers;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a quaternion number with four components: real, i, j, and k.
 * This class extends ImaginaryNumber and adds support for quaternion arithmetic operations.
 *
 * <p>A quaternion is typically written as a + bi + cj + dk, where 'a' is the real part and
 * 'b', 'c', 'd' are the coefficients of the fundamental quaternion units i, j, k respectively.
 * Quaternions are used extensively in 3D graphics for representing rotations.</p>
 *
 * <p>The quaternion multiplication follows Hamilton's rules:
 * <ul>
 * <li>i² = j² = k² = ijk = -1</li>
 * <li>ij = k, ji = -k</li>
 * <li>jk = i, kj = -i</li>
 * <li>ki = j, ik = -j</li>
 * </ul></p>
 *
 * <p>Like its parent classes, QuaternionNumber instances are immutable - all operations return new
 * instances rather than modifying the existing object.</p>
 *
 * @author pan_truskawka045
 * @version 1.0
 * @since 1.0
 */
@Getter
public class QuaternionNumber extends ImaginaryNumber {

    /**
     * The j component of the quaternion.
     */
    private final double j;

    /**
     * The k component of the quaternion.
     */
    private final double k;

    /**
     * Constructs a new quaternion with the specified real, i, j, and k components.
     *
     * @param real the real part of the quaternion (scalar component)
     * @param i    the i component of the quaternion (first imaginary unit)
     * @param j    the j component of the quaternion (second imaginary unit)
     * @param k    the k component of the quaternion (third imaginary unit)
     */
    public QuaternionNumber(double real, double i, double j, double k) {
        super(real, i);
        this.j = j;
        this.k = k;
    }

    /**
     * Adds this quaternion to another quaternion.
     * The addition is performed component-wise: (a + bi + cj + dk) + (e + fi + gj + hk) = (a+e) + (b+f)i + (c+g)j + (d+h)k
     *
     * @param other the quaternion to add to this quaternion
     * @return a new QuaternionNumber representing the sum of the two quaternions
     * @throws NullPointerException if other is null
     */
    public @NotNull QuaternionNumber add(@NotNull QuaternionNumber other) {
        Preconditions.checkNotNull(other, "Other QuaternionNumber must not be null");
        return new QuaternionNumber(
                this.getValue() + other.getValue(),
                this.getImaginary() + other.getImaginary(),
                this.j + other.j,
                this.k + other.k
        );
    }

    /**
     * Subtracts another quaternion from this quaternion.
     * The subtraction is performed component-wise: (a + bi + cj + dk) - (e + fi + gj + hk) = (a-e) + (b-f)i + (c-g)j + (d-h)k
     *
     * @param other the quaternion to subtract from this quaternion
     * @return a new QuaternionNumber representing the difference of the two quaternions
     * @throws NullPointerException if other is null
     */
    public @NotNull QuaternionNumber subtract(@NotNull QuaternionNumber other) {
        Preconditions.checkNotNull(other, "Other QuaternionNumber must not be null");
        return new QuaternionNumber(
                this.getValue() - other.getValue(),
                this.getImaginary() - other.getImaginary(),
                this.j - other.j,
                this.k - other.k
        );
    }

    /**
     * Multiplies this quaternion by another quaternion using Hamilton's quaternion multiplication rules.
     *
     * <p>The multiplication formula for quaternions (q₁ = a + bi + cj + dk) and (q₂ = e + fi + gj + hk) is:
     * <br>q₁ × q₂ = (ae - bf - cg - dh) + (af + be + ch - dg)i + (ag - bh + ce + df)j + (ah + bg - cf + de)k</p>
     *
     * <p>Note that quaternion multiplication is non-commutative, meaning q₁ × q₂ ≠ q₂ × q₁ in general.</p>
     *
     * @param other the quaternion to multiply with this quaternion
     * @return a new QuaternionNumber representing the product of the two quaternions
     * @throws NullPointerException if other is null
     */
    public @NotNull QuaternionNumber multiply(@NotNull QuaternionNumber other) {
        Preconditions.checkNotNull(other, "Other QuaternionNumber must not be null");
        double newValue = this.getValue() * other.getValue() - this.getImaginary() * other.getImaginary() - this.j * other.j - this.k * other.k;
        double newI = this.getValue() * other.getImaginary() + this.getImaginary() * other.getValue() + this.j * other.k - this.k * other.j;
        double newJ = this.getValue() * other.j + this.j * other.getValue() + this.k * other.getImaginary() - this.getImaginary() * other.k;
        double newK = this.getValue() * other.k + this.k * other.getValue() + this.getImaginary() * other.j - this.j * other.getImaginary();
        return new QuaternionNumber(newValue, newI, newJ, newK);
    }

    /**
     * Checks if this quaternion is the zero quaternion (all components are zero).
     *
     * @return true if all components (real, i, j, k) are zero, false otherwise
     */
    public boolean isZero() {
        return this.getValue() == 0 && this.getImaginary() == 0 && this.j == 0 && this.k == 0;
    }

}
