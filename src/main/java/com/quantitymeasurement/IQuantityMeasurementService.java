package com.quantitymeasurement;

public interface IQuantityMeasurementService {
    boolean compare(QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit);
    QuantityDTO add(QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityDTO add(QuantityDTO thisQty, QuantityDTO thatQty, QuantityDTO targetUnit);
    QuantityDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty);
    QuantityDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty, QuantityDTO targetUnit);
    double divide(QuantityDTO thisQty, QuantityDTO thatQty);
}