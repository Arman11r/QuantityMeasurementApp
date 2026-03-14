package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.measurement.IMeasurable;
import com.app.quantitymeasurement.measurement.LengthUnit;
import com.app.quantitymeasurement.measurement.WeightUnit;
import com.app.quantitymeasurement.measurement.VolumeUnit;
import com.app.quantitymeasurement.measurement.TemperatureUnit;
import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.quantity.Quantity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    private enum ArithmeticOp { ADD, SUBTRACT }

    // ── Compare ───────────────────────────────────────────────────────────────
    @Override
    public QuantityMeasurementDTO compare(QuantityDTO thisQty, QuantityDTO thatQty) {
        validateNotNull(thisQty);
        validateNotNull(thatQty);

        QuantityMeasurementEntity entity;
        try {
            Quantity<IMeasurable> q1 = convertDtoToModel(thisQty);
            Quantity<IMeasurable> q2 = convertDtoToModel(thatQty);
            boolean result = q1.equals(q2);

            entity = new QuantityMeasurementEntity(
                    thisQty.getValue(), thisQty.getUnit(), thisQty.getMeasurementType(),
                    thatQty.getValue(), thatQty.getUnit(), thatQty.getMeasurementType(),
                    "compare",
                    0, null, null,
                    String.valueOf(result),
                    false, null
            );
        } catch (Exception e) {
            entity = buildErrorEntity(thisQty, thatQty, "compare", "compare Error: " + e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("compare Error: " + e.getMessage());
        }

        repository.save(entity);
        return QuantityMeasurementDTO.fromEntity(entity);
    }

    // ── Convert ───────────────────────────────────────────────────────────────
    @Override
    public QuantityMeasurementDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit) {
        validateNotNull(thisQty);
        validateNotNull(targetUnit);

        QuantityMeasurementEntity entity;
        try {
            Quantity<IMeasurable> quantity = convertDtoToModel(thisQty);
            IMeasurable target = resolveUnit(targetUnit.getUnit(), targetUnit.getMeasurementType());
            Quantity<IMeasurable> result = quantity.convertTo(target);

            entity = new QuantityMeasurementEntity(
                    thisQty.getValue(), thisQty.getUnit(), thisQty.getMeasurementType(),
                    0, null, null,
                    "convert",
                    result.getValue(), result.getUnit().getUnitName(),
                    result.getUnit().getClass().getSimpleName(),
                    null, false, null
            );
        } catch (Exception e) {
            entity = buildErrorEntity(thisQty, targetUnit, "convert", "convert Error: " + e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("convert Error: " + e.getMessage());
        }

        repository.save(entity);
        return QuantityMeasurementDTO.fromEntity(entity);
    }

    // ── Add ───────────────────────────────────────────────────────────────────
    @Override
    public QuantityMeasurementDTO add(QuantityDTO thisQty, QuantityDTO thatQty) {
        return performArithmetic(thisQty, thatQty, ArithmeticOp.ADD);
    }

    // ── Subtract ──────────────────────────────────────────────────────────────
    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty) {
        return performArithmetic(thisQty, thatQty, ArithmeticOp.SUBTRACT);
    }

    // ── Divide ────────────────────────────────────────────────────────────────
    @Override
    public QuantityMeasurementDTO divide(QuantityDTO thisQty, QuantityDTO thatQty) {
        validateNotNull(thisQty);
        validateNotNull(thatQty);

        QuantityMeasurementEntity entity;
        try {
            Quantity<IMeasurable> q1 = convertDtoToModel(thisQty);
            Quantity<IMeasurable> q2 = convertDtoToModel(thatQty);
            double result = q1.divide(q2);

            entity = new QuantityMeasurementEntity(
                    thisQty.getValue(), thisQty.getUnit(), thisQty.getMeasurementType(),
                    thatQty.getValue(), thatQty.getUnit(), thatQty.getMeasurementType(),
                    "divide",
                    result, "SCALAR", "None",
                    null, false, null
            );
        } catch (Exception e) {
            entity = buildErrorEntity(thisQty, thatQty, "divide", "divide Error: " + e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException("divide Error: " + e.getMessage());
        }

        repository.save(entity);
        return QuantityMeasurementDTO.fromEntity(entity);
    }

    // ── History & Count ───────────────────────────────────────────────────────
    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByOperation(operation.toLowerCase())
        );
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String measurementType) {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByThisMeasurementType(measurementType)
        );
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toLowerCase());
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(
                repository.findByIsErrorTrue()
        );
    }

    // ── Private Helpers ───────────────────────────────────────────────────────
    private QuantityMeasurementDTO performArithmetic(
            QuantityDTO thisQty, QuantityDTO thatQty, ArithmeticOp op) {

        validateNotNull(thisQty);
        validateNotNull(thatQty);

        QuantityMeasurementEntity entity;
        try {
            Quantity<IMeasurable> q1 = convertDtoToModel(thisQty);
            Quantity<IMeasurable> q2 = convertDtoToModel(thatQty);

            Quantity<IMeasurable> result = (op == ArithmeticOp.ADD)
                    ? q1.add(q2)
                    : q1.subtract(q2);

            entity = new QuantityMeasurementEntity(
                    thisQty.getValue(), thisQty.getUnit(), thisQty.getMeasurementType(),
                    thatQty.getValue(), thatQty.getUnit(), thatQty.getMeasurementType(),
                    op.name().toLowerCase(),
                    result.getValue(), result.getUnit().getUnitName(),
                    result.getUnit().getClass().getSimpleName(),
                    null, false, null
            );
        } catch (Exception e) {
            entity = buildErrorEntity(thisQty, thatQty, op.name().toLowerCase(),
                    op.name().toLowerCase() + " Error: " + e.getMessage());
            repository.save(entity);
            throw new QuantityMeasurementException(op.name().toLowerCase() + " Error: " + e.getMessage());
        }

        repository.save(entity);
        return QuantityMeasurementDTO.fromEntity(entity);
    }

    private Quantity<IMeasurable> convertDtoToModel(QuantityDTO dto) {
        IMeasurable unit = resolveUnit(dto.getUnit(), dto.getMeasurementType());
        return new Quantity<>(dto.getValue(), unit);
    }

    private IMeasurable resolveUnit(String unitName, String measurementType) {
        return switch (measurementType) {
            case "LengthUnit"      -> findUnit(LengthUnit.values(), unitName);
            case "WeightUnit"      -> findUnit(WeightUnit.values(), unitName);
            case "VolumeUnit"      -> findUnit(VolumeUnit.values(), unitName);
            case "TemperatureUnit" -> findUnit(TemperatureUnit.values(), unitName);
            default -> throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
        };
    }

    private <U extends IMeasurable> U findUnit(U[] values, String unitName) {
        for (U u : values) {
            if (u.getUnitName().equalsIgnoreCase(unitName)) return u;
        }
        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    private void validateNotNull(QuantityDTO dto) {
        if (dto == null) throw new QuantityMeasurementException("Quantity cannot be null");
    }

    private QuantityMeasurementEntity buildErrorEntity(
            QuantityDTO thisQty, QuantityDTO thatQty, String operation, String errorMessage) {
        return new QuantityMeasurementEntity(
                thisQty != null ? thisQty.getValue() : 0,
                thisQty != null ? thisQty.getUnit() : null,
                thisQty != null ? thisQty.getMeasurementType() : null,
                thatQty != null ? thatQty.getValue() : 0,
                thatQty != null ? thatQty.getUnit() : null,
                thatQty != null ? thatQty.getMeasurementType() : null,
                operation,
                0, null, null, null,
                true, errorMessage
        );
    }
}