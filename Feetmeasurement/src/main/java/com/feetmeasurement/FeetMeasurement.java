package com.feetmeasurement;

public class FeetMeasurement {

   
    public enum Unit {

        FEET(12.0),
        INCH(1.0);

        private final double conversionFactor;

        Unit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double toBase(double value) {
            return value * conversionFactor;
        }
    }

   
    public static class Quantity {

        private final double value;
        private final Unit unit;

        public Quantity(double value, Unit unit) {
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            // Reflexive
            if (this == obj)
                return true;

            // Null + Type check
            if (obj == null || getClass() != obj.getClass())
                return false;

            Quantity other = (Quantity) obj;

            // Convert both to base (inch) before comparing
            double thisInBase = this.unit.toBase(this.value);
            double otherInBase = other.unit.toBase(other.value);

            return Double.compare(thisInBase, otherInBase) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(unit.toBase(value));
        }
    }
}