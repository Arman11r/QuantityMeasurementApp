package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO compare(QuantityDTO thisQty, QuantityDTO thatQty, String username);

    QuantityMeasurementDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit, String username);

    QuantityMeasurementDTO add(QuantityDTO thisQty, QuantityDTO thatQty, String username);

    QuantityMeasurementDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty, String username);

    QuantityMeasurementDTO divide(QuantityDTO thisQty, QuantityDTO thatQty, String username);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

    List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType);

    long getOperationCount(String operation);

    List<QuantityMeasurementDTO> getErrorHistory();

    List<QuantityMeasurementDTO> getHistoryByUsername(String username); // ← NEW
}