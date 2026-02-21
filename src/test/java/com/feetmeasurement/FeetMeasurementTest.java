package com.feetmeasurement;

import com.length.Length;
import com.length.Length.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_ExplicitTargetUnit_Feet() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Inches() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Yards() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.YARDS);

        assertEquals(0.666666, result.getValue(), 1e-3);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Centimeters() {

        Length l1 = new Length(2.54, LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.CENTIMETERS);

        assertEquals(5.08, result.getValue(), 1e-2);
    }

    @Test
    void testAddition_Commutativity_WithExplicitTarget() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result1 = l1.add(l2, LengthUnit.YARDS);
        Length result2 = l2.add(l1, LengthUnit.YARDS);

        assertEquals(result1.getValue(), result2.getValue(), EPSILON);
    }

    @Test
    void testAddition_NullTargetUnit_Throws() {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(l2, null));
    }

    @Test
    void testAddition_WithZero_TargetUnit() {

        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(0.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.YARDS);

        assertEquals(1.666666, result.getValue(), 1e-3);
    }

    @Test
    void testAddition_NegativeValues_TargetUnit() {

        Length l1 = new Length(5.0, LengthUnit.FEET);
        Length l2 = new Length(-2.0, LengthUnit.FEET);

        Length result = l1.add(l2, LengthUnit.INCHES);

        assertEquals(36.0, result.getValue(), EPSILON);
    }
}