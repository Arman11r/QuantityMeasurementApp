package com.feetmeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeetMeasurementTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(2.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(3.0, result.getValue(), EPSILON);
        assertEquals(FeetMeasurement.Unit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(12.0, FeetMeasurement.Unit.INCH);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(FeetMeasurement.Unit.FEET, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(12.0, FeetMeasurement.Unit.INCH);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals(FeetMeasurement.Unit.INCH, result.getUnit());
    }

    @Test
    void testAddition_CrossUnit_YardPlusFeet() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.YARDS);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(3.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals(FeetMeasurement.Unit.YARDS, result.getUnit());
    }

    @Test
    void testAddition_WithZero() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(5.0, FeetMeasurement.Unit.FEET);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(0.0, FeetMeasurement.Unit.INCH);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(5.0, FeetMeasurement.Unit.FEET);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(-2.0, FeetMeasurement.Unit.FEET);

        FeetMeasurement.Quantity result = q1.add(q2);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_Commutativity_BaseEquality() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);
        FeetMeasurement.Quantity q2 =
                new FeetMeasurement.Quantity(12.0, FeetMeasurement.Unit.INCH);

        FeetMeasurement.Quantity result1 = q1.add(q2);
        FeetMeasurement.Quantity result2 = q2.add(q1);

        double result1Base =
                result1.getUnit().toBase(result1.getValue());

        double result2Base =
                result2.getUnit().toBase(result2.getValue());

        assertEquals(result1Base, result2Base, EPSILON);
    }

    @Test
    void testAddition_NullOperand() {
        FeetMeasurement.Quantity q1 =
                new FeetMeasurement.Quantity(1.0, FeetMeasurement.Unit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> q1.add(null));
    }
}