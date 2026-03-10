package com.feetmeasurement;

import com.measurement.Quantity;
import com.measurement.IMeasurable;
import com.length.LengthUnit;
import com.weight.WeightUnit;
import com.volume.VolumeUnit;
import com.temperature.TemperatureUnit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementTest {

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
    void testIMeasurableInterface_VolumeUnitImplementation() {
        IMeasurable unit = VolumeUnit.LITRE;
        assertNotNull(unit.getConversionFactor());
    }


    @Test
    void testLengthEquality() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        assertTrue(q1.equals(q2));
    }


    @Test
    void testWeightEquality() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }


    @Test
    void testVolumeEquality_LitreMillilitre() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(q1.equals(q2));
    }


    @Test
    void testVolumeEquality_LitreGallon() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        assertTrue(q1.equals(q2));
    }


    @Test
    void testLengthConversion() {

        Quantity<LengthUnit> q =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> converted =
                q.convertTo(LengthUnit.INCHES);

        assertEquals(12.0, converted.getValue(), EPSILON);
    }


    @Test
    void testWeightConversion() {

        Quantity<WeightUnit> q =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> converted =
                q.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, converted.getValue(), EPSILON);
    }


    @Test
    void testVolumeConversion() {

        Quantity<VolumeUnit> q =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                q.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), EPSILON);
    }


    @Test
    void testLengthAddition() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                q1.add(q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPSILON);
    }


    @Test
    void testWeightAddition() {

        Quantity<WeightUnit> q1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> q2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        Quantity<WeightUnit> result =
                q1.add(q2, WeightUnit.KILOGRAM);

        assertEquals(2.0, result.getValue(), EPSILON);
    }


    @Test
    void testVolumeAddition() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                q1.add(q2, VolumeUnit.LITRE);

        assertEquals(2.0, result.getValue(), EPSILON);
    }


    @Test
    void testVolumeAdditionWithGallon() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(3.78541, VolumeUnit.LITRE);

        Quantity<VolumeUnit> result =
                q1.add(q2, VolumeUnit.GALLON);

        assertEquals(2.0, result.getValue(), 1e-3);
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
    void testCrossCategoryPrevention_VolumeVsLength() {

        Quantity<VolumeUnit> volume =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<LengthUnit> length =
                new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
    }


    @Test
    void testConstructorValidation_NullUnit() {

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }


    @Test
    void testConstructorValidation_InvalidValue() {

        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }
    
    @Test
    void testSubtraction_Length() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        Quantity<LengthUnit> result =
                q1.subtract(q2, LengthUnit.FEET);

        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_Volume() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result =
                q1.subtract(q2, VolumeUnit.LITRE);

        assertEquals(4.5, result.getValue(), EPSILON);
    }

    @Test
    void testDivision_Length() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(2.0, LengthUnit.FEET);

        assertEquals(5.0, q1.divide(q2), EPSILON);
    }

    @Test
    void testDivision_Volume() {

        Quantity<VolumeUnit> q1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> q2 =
                new Quantity<>(10.0, VolumeUnit.LITRE);

        assertEquals(0.5, q1.divide(q2), EPSILON);
    }

    @Test
    void testDivisionByZero() {

        Quantity<LengthUnit> q1 =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> q2 =
                new Quantity<>(0.0, LengthUnit.FEET);

        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }
    @Test
    void testTemperatureEquality_CelsiusToFahrenheit() {

        Quantity<TemperatureUnit> c =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> f =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        assertTrue(c.equals(f));
    }

    @Test
    void testTemperatureConversion() {

        Quantity<TemperatureUnit> c =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> f =
                c.convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(212.0, f.getValue(), EPSILON);
    }

    @Test
    void testTemperatureUnsupportedAddition() {

        Quantity<TemperatureUnit> t1 =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> t2 =
                new Quantity<>(50.0, TemperatureUnit.CELSIUS);

        assertThrows(
                UnsupportedOperationException.class,
                () -> t1.add(t2)
        );
    }
}