package com.feetmeasurement;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class FeetMeasurementTest {

    @Test
    void testEquality_YardToYard_SameValue() {

        FeetMeasurement.Length l1 =
                new FeetMeasurement.Length(1.0,
                        FeetMeasurement.Length.LengthUnit.YARDS);

        FeetMeasurement.Length l2 =
                new FeetMeasurement.Length(1.0,
                        FeetMeasurement.Length.LengthUnit.YARDS);

        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {

        FeetMeasurement.Length yard =
                new FeetMeasurement.Length(1.0,
                        FeetMeasurement.Length.LengthUnit.YARDS);

        FeetMeasurement.Length feet =
                new FeetMeasurement.Length(3.0,
                        FeetMeasurement.Length.LengthUnit.FEET);

        assertTrue(yard.equals(feet));
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {

        FeetMeasurement.Length yard =
                new FeetMeasurement.Length(1.0,
                        FeetMeasurement.Length.LengthUnit.YARDS);

        FeetMeasurement.Length inches =
                new FeetMeasurement.Length(36.0,
                        FeetMeasurement.Length.LengthUnit.INCHES);

        assertTrue(yard.equals(inches));
    }

    @Test
    void testEquality_CentimeterToInches_EquivalentValue() {

        FeetMeasurement.Length cm =
                new FeetMeasurement.Length(1.0,
                        FeetMeasurement.Length.LengthUnit.CENTIMETERS);

        FeetMeasurement.Length inch =
                new FeetMeasurement.Length(0.393701,
                        FeetMeasurement.Length.LengthUnit.INCHES);

        assertTrue(cm.equals(inch));
    }
}