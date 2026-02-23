package com.feetmeasurement;

public class FeatMeasurement {

    public static class Length {

        private double value;
        private LengthUnit unit;

        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0);

            private final double conversionFactor;

            LengthUnit(double conversionFactor) {
                this.conversionFactor = conversionFactor;
            }

            public double getConversionFactor() {
                return conversionFactor;
            }
        }

        public Length(double value, LengthUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        private double convertToBaseUnit() {
            return this.value * this.unit.getConversionFactor();
        }

        public boolean compare(Length thatLength) {
            return Double.compare(
                    this.convertToBaseUnit(),
                    thatLength.convertToBaseUnit()
            ) == 0;
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

        @Override
        public int hashCode() {
            return Double.hashCode(convertToBaseUnit());
        }
    }

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void demonstrateFeetEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);
        System.out.println("Feet equal? " + l1.equals(l2));
    }

    public static void demonstrateInchesEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
        System.out.println("Inches equal? " + l1.equals(l2));
    }

    public static void demonstrateFeetInchesComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        System.out.println("Feet & Inches equal? " + l1.equals(l2));
    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
        demonstrateFeetInchesComparison();
    }
}
