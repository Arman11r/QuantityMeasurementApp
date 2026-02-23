package com.feetmeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeatMeasurementTest {

    @Test
    void testFeetEquality_SameValue() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        FeatMeasurement.Length l2 =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        assertTrue(l1.equals(l2));
    }

    @Test
    void testInchesEquality_SameValue() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(5.0,
                        FeatMeasurement.Length.LengthUnit.INCHES);

        FeatMeasurement.Length l2 =
                new FeatMeasurement.Length(5.0,
                        FeatMeasurement.Length.LengthUnit.INCHES);

        assertTrue(l1.equals(l2));
    }

    @Test
    void testFeetToInchesEquality() {
        FeatMeasurement.Length oneFoot =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        FeatMeasurement.Length twelveInches =
                new FeatMeasurement.Length(12.0,
                        FeatMeasurement.Length.LengthUnit.INCHES);

        assertTrue(oneFoot.equals(twelveInches));
    }

    @Test
    void testDifferentValues() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        FeatMeasurement.Length l2 =
                new FeatMeasurement.Length(2.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        assertFalse(l1.equals(l2));
    }

    @Test
    void testNullComparison() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }

    @Test
    void testDifferentTypeComparison() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(1.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        assertFalse(l1.equals("1.0"));
    }

    @Test
    void testReflexiveProperty() {
        FeatMeasurement.Length l1 =
                new FeatMeasurement.Length(3.0,
                        FeatMeasurement.Length.LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }
}
