package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementControllerTest {

    private final QuantityMeasurementController controller =
            new QuantityMeasurementController(
                    new QuantityMeasurementServiceImpl(
                            new QuantityMeasurementCacheRepository()));

    @Test
    void testControllerComparison() {

        QuantityDTO q1 = new QuantityDTO(1,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12,"INCHES","LengthUnit");

        assertTrue(controller.performComparison(q1,q2));
    }

    @Test
    void testControllerAddition() {

        QuantityDTO q1 = new QuantityDTO(1,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(12,"INCHES","LengthUnit");

        assertEquals(2.0,
                controller.performAddition(q1,q2).getValue());
    }

    @Test
    void testControllerSubtraction() {

        QuantityDTO q1 = new QuantityDTO(10,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(6,"INCHES","LengthUnit");

        assertEquals(9.5,
                controller.performSubtraction(q1,q2).getValue());
    }

    @Test
    void testControllerDivision() {

        QuantityDTO q1 = new QuantityDTO(10,"FEET","LengthUnit");
        QuantityDTO q2 = new QuantityDTO(2,"FEET","LengthUnit");

        assertEquals(5.0,
                controller.performDivision(q1,q2));
    }
}