package com.feetmeasurement;

import com.length.Length;
import com.length.LengthUnit;
import com.weight.Weight;
import com.weight.WeightUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementTest {

    private static final double EPSILON = 1e-6;

    @Test
    void testEquality_KilogramToKilogram_SameValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_KilogramToKilogram_DifferentValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);
        assertFalse(w1.equals(w2));
    }

    @Test
    void testEquality_KilogramToGram_EquivalentValue() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_GramToKilogram_EquivalentValue() {
        Weight w1 = new Weight(1000.0, WeightUnit.GRAM);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_WeightVsLength_Incompatible() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        Length l = new Length(1.0, LengthUnit.FEET);
        assertFalse(w.equals(l));
    }

    @Test
    void testEquality_NullComparison() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        assertFalse(w.equals(null));
    }

    @Test
    void testEquality_SameReference() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        assertTrue(w.equals(w));
    }

    @Test
    void testEquality_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Weight(1.0, null));
    }

    @Test
    void testEquality_TransitiveProperty() {
        Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight b = new Weight(1000.0, WeightUnit.GRAM);
        Weight c = new Weight(1.0, WeightUnit.KILOGRAM);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    @Test
    void testEquality_ZeroValue() {
        Weight w1 = new Weight(0.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(0.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_NegativeWeight() {
        Weight w1 = new Weight(-1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(-1000.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_LargeWeightValue() {
        Weight w1 = new Weight(1000000.0, WeightUnit.GRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    void testEquality_SmallWeightValue() {
        Weight w1 = new Weight(0.001, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1.0, WeightUnit.GRAM);
        assertTrue(w1.equals(w2));
    }

 
    @Test
    void testConversion_PoundToKilogram() {
        Weight w = new Weight(2.20462, WeightUnit.POUND);
        Weight result = w.convertTo(WeightUnit.KILOGRAM);
        assertEquals(1.0, result.getValue(), 1e-3);
    }

    @Test
    void testConversion_KilogramToPound() {
        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight result = w.convertTo(WeightUnit.POUND);
        assertEquals(2.20462, result.getValue(), 1e-3);
    }

    @Test
    void testConversion_SameUnit() {
        Weight w = new Weight(5.0, WeightUnit.KILOGRAM);
        Weight result = w.convertTo(WeightUnit.KILOGRAM);
        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_ZeroValue() {
        Weight w = new Weight(0.0, WeightUnit.KILOGRAM);
        Weight result = w.convertTo(WeightUnit.GRAM);
        assertEquals(0.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_NegativeValue() {
        Weight w = new Weight(-1.0, WeightUnit.KILOGRAM);
        Weight result = w.convertTo(WeightUnit.GRAM);
        assertEquals(-1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {
        Weight w = new Weight(1.5, WeightUnit.KILOGRAM);

        Weight result = w.convertTo(WeightUnit.GRAM)
                .convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.5, result.getValue(), EPSILON);
    }


    @Test
    void testAddition_SameUnit_KilogramPlusKilogram() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(2.0, WeightUnit.KILOGRAM);
        Weight result = w1.add(w2);
        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_KilogramPlusGram() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);
        Weight result = w1.add(w2);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_CrossUnit_PoundPlusKilogram() {
        Weight w1 = new Weight(2.20462, WeightUnit.POUND);
        Weight w2 = new Weight(1.0, WeightUnit.KILOGRAM);

        Weight result = w1.add(w2);
        assertEquals(4.40924, result.getValue(), 1e-3);
    }

    @Test
    void testAddition_ExplicitTargetUnit_Kilogram() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        Weight result = w1.add(w2, WeightUnit.GRAM);

        assertEquals(2000.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_Commutativity() {
        Weight w1 = new Weight(1.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1000.0, WeightUnit.GRAM);

        Weight r1 = w1.add(w2);
        Weight r2 = w2.add(w1);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }

    @Test
    void testAddition_WithZero() {
        Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(0.0, WeightUnit.GRAM);
 
        Weight result = w1.add(w2);

        assertEquals(5.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_NegativeValues() {
        Weight w1 = new Weight(5.0, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(-2000.0, WeightUnit.GRAM);

        Weight result = w1.add(w2);

        assertEquals(3.0, result.getValue(), EPSILON);
    }

    @Test
    void testAddition_LargeValues() {
        Weight w1 = new Weight(1e6, WeightUnit.KILOGRAM);
        Weight w2 = new Weight(1e6, WeightUnit.KILOGRAM);

        Weight result = w1.add(w2);

        assertEquals(2e6, result.getValue(), EPSILON);
    }
}