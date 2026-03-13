package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO thisQty, QuantityDTO thatQty);

    QuantityDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit);

    QuantityDTO add(QuantityDTO thisQty, QuantityDTO thatQty);

    QuantityDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty);

    double divide(QuantityDTO thisQty, QuantityDTO thatQty);

    List<QuantityMeasurementEntity> getAllMeasurements();
}