package com.feetmeasurement;

import com.length.Length;
import com.length.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testFeetEquality() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testInchesEquality() {
        Length l1 = new Length(12.0, LengthUnit.INCHES);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testYardEquals36Inches() {
        Length l1 = new Length(1.0, LengthUnit.YARDS);
        Length l2 = new Length(36.0, LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testCentimeterEqualsOneFoot() {
        Length l1 = new Length(30.48, LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testConversionFeetToInches() {
        Length l = new Length(1.0, LengthUnit.FEET);
        Length converted = l.convertTo(LengthUnit.INCHES);
        assertEquals(12.0, converted.getValue(), EPSILON);
    }

    @Test
    void testConversionInchesToFeet() {
        Length l = new Length(24.0, LengthUnit.INCHES);
        Length converted = l.convertTo(LengthUnit.FEET);
        assertEquals(2.0, converted.getValue(), EPSILON);
    }

    @Test
    void testAdditionSameUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(2.0, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdditionCrossUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.FEET);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAdditionExplicitTargetYards() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.YARDS);
        assertEquals(0.666666, result.getValue(), 1e-3);
    }

    @Test
    void testCommutativity() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length r1 = l1.add(l2, LengthUnit.FEET);
        Length r2 = l2.add(l1, LengthUnit.FEET);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }

    @Test
    void testNullUnitThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(1.0, null));
    }

    @Test
    void testInvalidValueThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testRoundTripConversion() {
        Length l = new Length(5.0, LengthUnit.FEET);
        Length converted = l.convertTo(LengthUnit.INCHES)
                            .convertTo(LengthUnit.FEET);

        assertEquals(5.0, converted.getValue(), EPSILON);
    }
}