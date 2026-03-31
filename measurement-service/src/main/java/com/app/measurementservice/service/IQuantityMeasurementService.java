package com.app.measurementservice.service;

import com.app.measurementservice.QuantityDTO;
import com.app.measurementservice.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO thisQty, QuantityDTO thatQty);

    QuantityMeasurementDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit);

    QuantityMeasurementDTO add(QuantityDTO thisQty, QuantityDTO thatQty);

    QuantityMeasurementDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty);

    QuantityMeasurementDTO divide(QuantityDTO thisQty, QuantityDTO thatQty);

    // History by operation type e.g. "COMPARE", "ADD"
    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

    // History by measurement type e.g. "LengthUnit"
    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

    // Count of successful operations
    long getOperationCount(String operation);


    // All errored records
    List<QuantityMeasurementDTO> getErrorHistory();
}