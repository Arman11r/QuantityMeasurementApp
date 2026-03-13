package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementServiceImplTest {

    private IQuantityMeasurementService service;

    @BeforeEach
    void setup() {
        service = new QuantityMeasurementServiceImpl(
                new QuantityMeasurementCacheRepository());
    }

    @Test
    void testLengthEquality() {

        QuantityDTO q1 = new QuantityDTO(1.0,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12.0,"INCHES","LengthUnit");

        assertTrue(service.compare(q1,q2));
    }

    @Test
    void testWeightEquality() {

        QuantityDTO q1 = new QuantityDTO(1.0,"KILOGRAM","WeightUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0,"GRAM","WeightUnit");

        assertTrue(service.compare(q1,q2));
    }

    @Test
    void testVolumeEquality() {

        QuantityDTO q1 = new QuantityDTO(1.0,"LITRE","VolumeUnit");
        QuantityDTO q2 = new QuantityDTO(1000.0,"MILLILITRE","VolumeUnit");

        assertTrue(service.compare(q1,q2));
    }

    @Test
    void testAdditionLength() {

        QuantityDTO q1 = new QuantityDTO(1.0,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12.0,"INCHES","LengthUnit");

        QuantityDTO result = service.add(q1,q2);

        assertEquals(2.0,result.getValue());
    }

    @Test
    void testSubtractionLength() {

        QuantityDTO q1 = new QuantityDTO(10.0,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(6.0,"INCHES","LengthUnit");

        QuantityDTO result = service.subtract(q1,q2);

        assertEquals(9.5,result.getValue());
    }

    @Test
    void testDivision() {

        QuantityDTO q1 = new QuantityDTO(10.0,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(2.0,"FEET","LengthUnit");

        assertEquals(5.0,service.divide(q1,q2));
    }

    @Test
    void testTemperatureEquality() {

        QuantityDTO q1 = new QuantityDTO(0.0,"CELSIUS","TemperatureUnit");
        QuantityDTO q2 = new QuantityDTO(32.0,"FAHRENHEIT","TemperatureUnit");

        assertTrue(service.compare(q1,q2));
    }
}