package me.pan_truskawka045.effects3d.numbers;

import lombok.Getter;

@Getter
public class ImaginaryNumber extends RealNumber {

    private final double imaginary;

    public ImaginaryNumber(double value, double imaginary) {
        super(value);
        this.imaginary = imaginary;
    }

    public ImaginaryNumber add(ImaginaryNumber other) {
        return new ImaginaryNumber(this.getValue() + other.getValue(), this.imaginary + other.imaginary);
    }

    public ImaginaryNumber subtract(ImaginaryNumber other) {
        return new ImaginaryNumber(this.getValue() - other.getValue(), this.imaginary - other.imaginary);
    }

    public ImaginaryNumber multiply(ImaginaryNumber other) {
        double realPart = this.getValue() * other.getValue() - this.imaginary * other.imaginary;
        double imaginaryPart = this.getValue() * other.imaginary + this.imaginary * other.getValue();
        return new ImaginaryNumber(realPart, imaginaryPart);
    }

    public ImaginaryNumber divide(ImaginaryNumber other) {
        double denominator = other.getValue() * other.getValue() + other.imaginary * other.imaginary;
        double realPart = (this.getValue() * other.getValue() + this.imaginary * other.imaginary) / denominator;
        double imaginaryPart = (this.imaginary * other.getValue() - this.getValue() * other.imaginary) / denominator;
        return new ImaginaryNumber(realPart, imaginaryPart);
    }

}
