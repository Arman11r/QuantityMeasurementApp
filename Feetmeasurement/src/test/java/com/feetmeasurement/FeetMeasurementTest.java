package com.feetmeasurement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FeetMeasurementTest {

    @Test
    void testEquality_YardToYard_SameValue() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.YARDS);

        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.YARDS);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        FeetMeasurement.Quantity yard =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.YARDS);

        FeetMeasurement.Quantity feet =
                new FeetMeasurement.Quantity(3.0, FeetMeasurement.Unit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        FeetMeasurement.Quantity yard =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.YARDS);

        FeetMeasurement.Quantity inches =
                new FeetMeasurement.Quantity(36.0, FeetMeasurement.Unit.INCH);

        assertTrue(yard.equals(inches));
    }

    @Test
    void testEquality_CentimeterToInches_EquivalentValue() {
        FeetMeasurement.Quantity cm =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.CENTIMETERS);

        FeetMeasurement.Quantity inch =
                new FeetMeasurement.Quantity(0.393701, FeetMeasurement.Unit.INCH);

        assertTrue(cm.equals(inch));
    }
}