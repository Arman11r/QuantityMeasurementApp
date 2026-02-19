package com.feetmeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeetMeasurementTest {


    @Test
    void testFeetEquality_SameValue() {
        assertTrue(FeetMeasurement.checkFeetEquality(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(FeetMeasurement.checkFeetEquality(1.0, 2.0));
    }

    @Test
    void testFeetEquality_SameReference() {
        FeetMeasurement.Feet f = new FeetMeasurement.Feet(1.0);
        assertTrue(f.equals(f));
    }

    @Test
    void testFeetEquality_NullComparison() {
        FeetMeasurement.Feet f = new FeetMeasurement.Feet(1.0);
        assertFalse(f.equals(null));
    }

    @Test
    void testFeetEquality_DifferentType() {
        FeetMeasurement.Feet f = new FeetMeasurement.Feet(1.0);
        assertFalse(f.equals("1.0"));
    }

    
    @Test
    void testInchEquality_SameValue() {
        assertTrue(FeetMeasurement.checkInchEquality(1.0, 1.0));
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(FeetMeasurement.checkInchEquality(1.0, 2.0));
    }

    @Test
    void testInchEquality_SameReference() {
        FeetMeasurement.Inch i = new FeetMeasurement.Inch(1.0);
        assertTrue(i.equals(i));
    }

    @Test
    void testInchEquality_NullComparison() {
        FeetMeasurement.Inch i = new FeetMeasurement.Inch(1.0);
        assertFalse(i.equals(null));
    }

    @Test
    void testInchEquality_DifferentType() {
        FeetMeasurement.Inch i = new FeetMeasurement.Inch(1.0);
        assertFalse(i.equals(1.0));
    }
}