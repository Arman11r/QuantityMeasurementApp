package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.List;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    public boolean performComparison(QuantityDTO thisQty, QuantityDTO thatQty) {
        return service.compare(thisQty, thatQty);
    }

    public QuantityDTO performConversion(QuantityDTO thisQty, QuantityDTO targetUnit) {
        return service.convert(thisQty, targetUnit);
    }

    public QuantityDTO performAddition(QuantityDTO thisQty, QuantityDTO thatQty) {
        return service.add(thisQty, thatQty);
    }

    public QuantityDTO performSubtraction(QuantityDTO thisQty, QuantityDTO thatQty) {
        return service.subtract(thisQty, thatQty);
    }

    public double performDivision(QuantityDTO thisQty, QuantityDTO thatQty) {
        return service.divide(thisQty, thatQty);
    }

    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return service.getAllMeasurements();
    }
}