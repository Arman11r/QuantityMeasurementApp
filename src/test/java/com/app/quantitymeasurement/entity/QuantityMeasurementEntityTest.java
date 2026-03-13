package com.app.quantitymeasurement.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementEntityTest {

    @Test
    void testDefaultConstructor() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        assertNotNull(entity);
    }

    @Test
    void testParameterizedConstructor() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        1.0,"FEET","LengthUnit",
                        12.0,"INCHES","LengthUnit",
                        "ADD",
                        2.0,"FEET","LengthUnit",
                        null,false,null
                );

        assertEquals(1.0,entity.getThisValue());
        assertEquals("FEET",entity.getThisUnit());
        assertEquals("LengthUnit",entity.getThisMeasurementType());

        assertEquals(12.0,entity.getThatValue());
        assertEquals("INCHES",entity.getThatUnit());
        assertEquals("LengthUnit",entity.getThatMeasurementType());

        assertEquals("ADD",entity.getOperation());

        assertEquals(2.0,entity.getResultValue());
        assertEquals("FEET",entity.getResultUnit());
        assertEquals("LengthUnit",entity.getResultMeasurementType());

        assertFalse(entity.isError());
    }

    @Test
    void testSettersAndGetters() {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setId(10);

        entity.setThisValue(5.0);
        entity.setThisUnit("FEET");
        entity.setThisMeasurementType("LengthUnit");

        entity.setThatValue(60.0);
        entity.setThatUnit("INCHES");
        entity.setThatMeasurementType("LengthUnit");

        entity.setOperation("COMPARE");

        entity.setResultValue(1.0);
        entity.setResultUnit("BOOLEAN");
        entity.setResultMeasurementType("None");

        entity.setResultString("true");

        entity.setError(false);
        entity.setErrorMessage(null);

        assertEquals(10,entity.getId());

        assertEquals(5.0,entity.getThisValue());
        assertEquals("FEET",entity.getThisUnit());
        assertEquals("LengthUnit",entity.getThisMeasurementType());

        assertEquals(60.0,entity.getThatValue());
        assertEquals("INCHES",entity.getThatUnit());
        assertEquals("LengthUnit",entity.getThatMeasurementType());

        assertEquals("COMPARE",entity.getOperation());

        assertEquals(1.0,entity.getResultValue());
        assertEquals("BOOLEAN",entity.getResultUnit());
        assertEquals("None",entity.getResultMeasurementType());

        assertEquals("true",entity.getResultString());

        assertFalse(entity.isError());
    }
}