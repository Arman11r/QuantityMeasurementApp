package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementCacheRepositoryTest {

    @Test
    void testSaveAndCount() {

        QuantityMeasurementDatabaseRepository repo =
                QuantityMeasurementDatabaseRepository.getInstance();

        repo.deleteAll();

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(
                        1,"FEET","LengthUnit",
                        12,"INCHES","LengthUnit",
                        "ADD",
                        2,"FEET","LengthUnit",
                        null,false,null
                );

        repo.save(entity);

        assertEquals(1,repo.getTotalCount());
    }
}