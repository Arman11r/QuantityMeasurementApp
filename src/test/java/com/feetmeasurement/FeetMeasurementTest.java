package com.feetmeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeetMeasurementTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testConversion_FeetToInches() {
        double result = FeetMeasurement.convert(
                1.0,
                FeetMeasurement.Unit.FEET,
                FeetMeasurement.Unit.INCH);

        assertEquals(12.0, result, EPSILON);
    }

    @Test
    void testConversion_YardsToFeet() {
        double result = FeetMeasurement.convert(
                3.0,
                FeetMeasurement.Unit.YARDS,
                FeetMeasurement.Unit.FEET);

        assertEquals(9.0, result, EPSILON);
    }

    @Test
    void testConversion_CentimetersToInches() {
        double result = FeetMeasurement.convert(
                2.54,
                FeetMeasurement.Unit.CENTIMETERS,
                FeetMeasurement.Unit.INCH);

        assertEquals(1.0, result, EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        double result = FeetMeasurement.convert(
                0.0,
                FeetMeasurement.Unit.FEET,
                FeetMeasurement.Unit.INCH);

        assertEquals(0.0, result, EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        double result = FeetMeasurement.convert(
                -1.0,
                FeetMeasurement.Unit.FEET,
                FeetMeasurement.Unit.INCH);

        assertEquals(-12.0, result, EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {
        double value = 5.0;

        double toInch = FeetMeasurement.convert(
                value,
                FeetMeasurement.Unit.FEET,
                FeetMeasurement.Unit.INCH);

        double backToFeet = FeetMeasurement.convert(
                toInch,
                FeetMeasurement.Unit.INCH,
                FeetMeasurement.Unit.FEET);

        assertEquals(value, backToFeet, EPSILON);
    }

    @Test
    void testConversion_InvalidUnit_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                FeetMeasurement.convert(1.0, null, FeetMeasurement.Unit.FEET));
    }

    @Test
    void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                FeetMeasurement.convert(Double.NaN,
                        FeetMeasurement.Unit.FEET,
                        FeetMeasurement.Unit.INCH));
    }
}