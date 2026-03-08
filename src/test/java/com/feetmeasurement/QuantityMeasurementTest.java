package com.feetmeasurement;

import com.measurement.Quantity;
import com.measurement.IMeasurable;
import com.length.LengthUnit;
import com.weight.WeightUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    private static final double EPSILON = 1e-6;


    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        IMeasurable unit = LengthUnit.FEET;
        assertNotNull(unit.getConversionFactor());
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        IMeasurable unit = WeightUnit.KILOGRAM;
        assertNotNull(unit.getConversionFactor());
    }


    @Test
    void testGenericQuantity_LengthOperations_Equality() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }



    @Test
    void testGenericQuantity_WeightOperations_Equality() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }



    @Test
    void testGenericQuantity_LengthOperations_Conversion() {

        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> converted =
                q.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, converted.getValue(), EPSILON);
    }


    @Test
    void testGenericQuantity_WeightOperations_Conversion() {

        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> converted =
                q.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, converted.getValue(), EPSILON);
    }



    @Test
    void testGenericQuantity_LengthOperations_Addition() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                q1.add(q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }


    @Test
    void testGenericQuantity_WeightOperations_Addition() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                q1.add(q2, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPSILON);
    }


    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<WeightUnit> weight =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(length.equals(weight));
    }



    @Test
    void testGenericQuantity_ConstructorValidation_NullUnit() {

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testGenericQuantity_ConstructorValidation_InvalidValue() {

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }


    @Test
    void testAddition_Commutativity() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> r1 = q1.add(q2);
        Quantity<WeightUnit> r2 = q2.add(q1);

        assertEquals(r1.getValue(), r2.getValue(), EPSILON);
    }

    @Test
    void testConversion_RoundTrip() {

        Quantity<WeightUnit> q =
                new Quantity<>(1.5, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> result =
                q.convertTo(WeightUnit.GRAM)
                        .convertTo(WeightUnit.KILOGRAM);

        assertEquals(1.5, result.getValue(), EPSILON);
    }



    @Test
    void testHashCode_GenericQuantity_Consistency() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(q1.hashCode(), q2.hashCode());
    }

   

    @Test
    void testEquals_GenericQuantity_ContractPreservation() {

        Quantity<LengthUnit> a =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> b =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> c =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }
}
