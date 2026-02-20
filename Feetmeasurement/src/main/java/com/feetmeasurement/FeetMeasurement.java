package com.feetmeasurement;

public class FeetMeasurement {

   
    public enum Unit {

        INCH(1.0),
        FEET(12.0),                
        YARDS(36.0),               
        CENTIMETERS(0.393701);      

        private final double conversionFactorToInch;

        Unit(double conversionFactorToInch) {
            this.conversionFactorToInch = conversionFactorToInch;
        }

        public double toBase(double value) {
            return value * conversionFactorToInch;
        }
    }

    
    public static class Quantity {

        private final double value;
        private final Unit unit;

        public Quantity(double value, Unit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            Quantity other = (Quantity) obj;

            double thisInBase = this.unit.toBase(this.value);
            double otherInBase = other.unit.toBase(other.value);

            return Double.compare(thisInBase, otherInBase) == 0;
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
}