package com.length;

public class Length {

    private static final double EPSILON = 1e-6;

    public enum LengthUnit {

        INCHES(1.0),
        FEET(12.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double factorToInch;

        LengthUnit(double factorToInch) {
            this.factorToInch = factorToInch;
        }

        public double toBase(double value) {
            return value * factorToInch;
        }

        public double fromBase(double baseValue) {
            return baseValue / factorToInch;
        }
    }

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

    public Length convertTo(LengthUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = unit.toBase(value);
        double converted = targetUnit.fromBase(base);

        return new Length(converted, targetUnit);
    }

    public Length add(Length other) {

        if (other == null)
            throw new IllegalArgumentException("Second operand cannot be null");

        return add(other, this.unit);
    }

    public Length add(Length other, LengthUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Second operand cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        double sumBase = base1 + base2;

        double resultValue = targetUnit.fromBase(sumBase);

        return new Length(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Length other = (Length) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.toBase(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        double base = source.toBase(value);
        return target.fromBase(base);
    }
}
