package com.length;

public class Length {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public enum LengthUnit {

        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBase(double value) {
            return value * conversionFactor;
        }

        public double fromBase(double baseValue) {
            return baseValue / conversionFactor;
        }
    }

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
        return unit.toBase(value);
    }

    private boolean compare(Length thatLength) {
        double thisBase = this.convertToBaseUnit();
        double otherBase = thatLength.convertToBaseUnit();
        return Math.abs(thisBase - otherBase) < EPSILON;
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
        double converted = targetUnit.fromBase(base);

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

        return addAndConvert(thatLength, targetUnit);
    }

    private Length addAndConvert(Length length, LengthUnit targetUnit) {

        double base1 = this.convertToBaseUnit();
        double base2 = length.convertToBaseUnit();

        double sumBase = base1 + base2;

        double resultValue = convertFromBaseToTargetUnit(sumBase, targetUnit);

        return new Length(resultValue, targetUnit);
    }

    private double convertFromBaseToTargetUnit(double baseValue, LengthUnit targetUnit) {
        return targetUnit.fromBase(baseValue);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    public static double convert(double value,
                                 LengthUnit source,
                                 LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        double base = source.toBase(value);
        return target.fromBase(base);
    }
}