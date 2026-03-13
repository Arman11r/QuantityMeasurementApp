package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private final List<QuantityMeasurementEntity> store = new ArrayList<>();

    @Override
    public void save(QuantityMeasurementEntity entity) {
        store.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return new ArrayList<>(store);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return store.stream()
                .filter(e -> operation.equalsIgnoreCase(e.getOperation()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        return store.stream()
                .filter(e -> measurementType.equalsIgnoreCase(e.getThisMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalCount() {
        return store.size();
    }

    @Override
    public void deleteAll() {
        store.clear();
    }
}