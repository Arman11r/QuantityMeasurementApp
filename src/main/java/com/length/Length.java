package com.length;

public class Length {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public Length(double value, LengthUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    private boolean compare(Length thatLength) {
        double base1 = this.convertToBaseUnit();
        double base2 = thatLength.convertToBaseUnit();
        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Length that = (Length) o;
        return compare(that);
    }

    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = convertToBaseUnit();
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Length(converted, targetUnit);
    }

    public Length add(Length thatLength) {
        return add(thatLength, this.unit);
    }

    public Length add(Length thatLength, LengthUnit targetUnit) {

        if (thatLength == null)
            throw new IllegalArgumentException("Second operand cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base1 = this.convertToBaseUnit();
        double base2 = thatLength.convertToBaseUnit();

        double sumBase = base1 + base2;

        double resultValue = targetUnit.convertFromBaseUnit(sumBase);

        return new Length(resultValue, targetUnit);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}