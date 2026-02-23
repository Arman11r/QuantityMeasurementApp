package com.feetmeasurement;

import com.length.Length;
import com.length.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_SameUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_CrossUnit_FeetAndInches() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_YardAndInches() {
        Length l1 = new Length(1.0, LengthUnit.YARDS);
        Length l2 = new Length(36.0, LengthUnit.INCHES);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testEquality_CentimeterAndFoot() {
        Length l1 = new Length(30.48, LengthUnit.CENTIMETERS);
        Length l2 = new Length(1.0, LengthUnit.FEET);
        assertTrue(l1.equals(l2));
    }

    @Test
    void testConvert_FeetToInches() {
        Length l = new Length(1.0, LengthUnit.FEET);
        Length result = l.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.INCHES, result.getUnit());
    }

    @Test
    void testConvert_InchesToFeet() {
        Length l = new Length(24.0, LengthUnit.INCHES);
        Length result = l.convertTo(LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_DefaultUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(2.0, LengthUnit.FEET);

        Length result = l1.add(l2);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_WithTargetUnit() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(LengthUnit.FEET, result.getUnit());
    }

    @Test
    void testAddition_WithExplicitTarget_Yards() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length result = l1.add(l2, LengthUnit.YARDS);

        assertEquals(0.666666, result.getValue(), 1e-3);
        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    @Test
    void testAddition_Commutativity() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length r1 = l1.add(l2, LengthUnit.FEET);
        Length r2 = l2.add(l1, LengthUnit.FEET);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }

    @Test
    void testConstructor_NullUnit_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(1.0, null));
    }

    @Test
    void testConstructor_InvalidValue_Throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new Length(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testRoundTripConversion() {
        Length l = new Length(5.0, LengthUnit.FEET);

        Length result = l.convertTo(LengthUnit.INCHES)
                         .convertTo(LengthUnit.FEET);

        assertEquals(5.0, result.getValue(), EPSILON);
    }
}