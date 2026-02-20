package com.feetmeasurement;

public class FeetMeasurement {

    private static final double EPSILON = 1e-6;

    public enum Unit {

        INCH(1.0),
        FEET(12.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

        private final double factorToInch;

        Unit(double factorToInch) {
            this.factorToInch = factorToInch;
        }

        public double toBase(double value) {
            return value * factorToInch;
        }

        public double fromBase(double baseValue) {
            return baseValue / factorToInch;
        }
    }

    public static class Quantity {

        private final double value;
        private final Unit unit;

        public Quantity(double value, Unit unit) {

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

        public Unit getUnit() {
            return unit;
        }

        public Quantity convertTo(Unit targetUnit) {

            if (targetUnit == null)
                throw new IllegalArgumentException("Target unit cannot be null");

            double base = unit.toBase(value);
            double converted = targetUnit.fromBase(base);

            return new Quantity(converted, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Quantity other = (Quantity) obj;

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
    }

    public static double convert(double value, Unit source, Unit target) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (source == null || target == null)
            throw new IllegalArgumentException("Units cannot be null");

        double base = source.toBase(value);
        return target.fromBase(base);
    }
}