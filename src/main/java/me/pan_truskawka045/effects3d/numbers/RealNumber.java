package me.pan_truskawka045.effects3d.numbers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RealNumber {

    private final double value;

    public RealNumber add(RealNumber other) {
        return new RealNumber(value + other.value);
    }

    public RealNumber subtract(RealNumber other) {
        return new RealNumber(value - other.value);
    }

    public RealNumber multiply(RealNumber other) {
        return new RealNumber(value * other.value);
    }

    public RealNumber divide(RealNumber other) {
        if (other.value == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return new RealNumber(value / other.value);
    }

}
