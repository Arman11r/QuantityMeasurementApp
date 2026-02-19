package com.feetmeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeetMeasurementTest {

    @Test
    void testFeetEquality_SameValue() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testInchEquality_SameValue() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(5.0, FeetMeasurement.Unit.INCH);

        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(5.0, FeetMeasurement.Unit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testFeetToInchEquality() {
        FeetMeasurement.Quantity oneFoot =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity twelveInch =
                new FeetMeasurement.Quantity(12.0, FeetMeasurement.Unit.INCH);

        assertTrue(oneFoot.equals(twelveInch));
    }

    @Test
    void testDifferentValues() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(2.0, FeetMeasurement.Unit.FEET);

        assertFalse(q1.equals(q2));
    }

    @Test
    void testNullComparison() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        assertFalse(q1.equals(null));
    }

    @Test
    void testDifferentType() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        assertFalse(q1.equals("1.0"));
    }
}