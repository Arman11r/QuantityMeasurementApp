package com.feetmeasurement;

public class FeetMeasurement {

    /*
     * ===== FEET CLASS =====
     */
    public static class Feet {

        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Feet other = (Feet) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    /*
     * ===== INCH CLASS =====
     */
    public static class Inch {

        private final double value;

        public Inch(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj)
                return true;

            if (obj == null)
                return false;

            if (getClass() != obj.getClass())
                return false;

            Inch other = (Inch) obj;

            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    /*
     * ===== STATIC METHODS (UC2 Requirement) =====
     */

    public static boolean checkFeetEquality(double value1, double value2) {
        Feet f1 = new Feet(value1);
        Feet f2 = new Feet(value2);
        return f1.equals(f2);
    }

    public static boolean checkInchEquality(double value1, double value2) {
        Inch i1 = new Inch(value1);
        Inch i2 = new Inch(value2);
        return i1.equals(i2);
    }

    /*
     * ===== MAIN METHOD =====
     */

    public static void main(String[] args) {

        boolean inchResult = checkInchEquality(1.0, 1.0);
        boolean feetResult = checkFeetEquality(1.0, 1.0);

        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Equal (" + inchResult + ")");

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Equal (" + feetResult + ")");
    }
}