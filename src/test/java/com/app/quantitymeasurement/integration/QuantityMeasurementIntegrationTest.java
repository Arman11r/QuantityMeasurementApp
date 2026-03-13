package com.app.quantitymeasurement.integration;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementIntegrationTest {

    private QuantityMeasurementController controller;

    @BeforeEach
    public void setup() {

        QuantityMeasurementCacheRepository repository =
                new QuantityMeasurementCacheRepository();

        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repository);

        controller = new QuantityMeasurementController(service);
    }

    @Test
    public void testEndToEndComparison() {

        QuantityDTO q1 =
                new QuantityDTO(1.0, "FEET", "LengthUnit");

        QuantityDTO q2 =
                new QuantityDTO(12.0, "INCHES", "LengthUnit");

        boolean result = controller.performComparison(q1, q2);

        assertTrue(result);
    }
}