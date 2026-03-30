package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.measurement.*;
import com.app.quantitymeasurement.model.*;
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

    //COMPARE
    @Override
    public QuantityMeasurementDTO compare(QuantityDTO q1Dto, QuantityDTO q2Dto) {

        if (q1Dto == null || q2Dto == null) {
            throw new QuantityMeasurementException("Input cannot be null");
        }

        try {
            Quantity<IMeasurable> q1 = createQuantity(q1Dto);
            Quantity<IMeasurable> q2 = createQuantity(q2Dto);

            boolean result = q1.equals(q2);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    q1Dto.getValue(), q1Dto.getUnit(), q1Dto.getMeasurementType(),
                    q2Dto.getValue(), q2Dto.getUnit(), q2Dto.getMeasurementType(),
                    "compare",
                    0, null, null,
                    String.valueOf(result),
                    false, null
            );

            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            throw new QuantityMeasurementException("Compare failed: " + e.getMessage());
        }
    }

    //CONVERT
    @Override
    public QuantityMeasurementDTO convert(QuantityDTO source, QuantityDTO target) {

        if (source == null || target == null) {
            throw new QuantityMeasurementException("Input cannot be null");
        }

        try {
            Quantity<IMeasurable> quantity = createQuantity(source);
            IMeasurable targetUnit = getUnit(target.getUnit(), target.getMeasurementType());

            Quantity<IMeasurable> result = quantity.convertTo(targetUnit);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    source.getValue(), source.getUnit(), source.getMeasurementType(),
                    0, null, null,
                    "convert",
                    result.getValue(),
                    result.getUnit().getUnitName(),
                    result.getUnit().getClass().getSimpleName(),
                    null, false, null
            );

            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            throw new QuantityMeasurementException("Convert failed: " + e.getMessage());
        }
    }

    //ADD
    @Override
    public QuantityMeasurementDTO add(QuantityDTO q1Dto, QuantityDTO q2Dto) {
        return calculate(q1Dto, q2Dto, "add");
    }

    //SUBTRACT
    @Override
    public QuantityMeasurementDTO subtract(QuantityDTO q1Dto, QuantityDTO q2Dto) {
        return calculate(q1Dto, q2Dto, "subtract");
    }

    //DIVIDE
    @Override
    public QuantityMeasurementDTO divide(QuantityDTO q1Dto, QuantityDTO q2Dto) {

        if (q1Dto == null || q2Dto == null) {
            throw new QuantityMeasurementException("Input cannot be null");
        }

        try {
            Quantity<IMeasurable> q1 = createQuantity(q1Dto);
            Quantity<IMeasurable> q2 = createQuantity(q2Dto);

            double result = q1.divide(q2);

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    q1Dto.getValue(), q1Dto.getUnit(), q1Dto.getMeasurementType(),
                    q2Dto.getValue(), q2Dto.getUnit(), q2Dto.getMeasurementType(),
                    "divide",
                    result, "SCALAR", "None",
                    null, false, null
            );

            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            throw new QuantityMeasurementException("Divide failed: " + e.getMessage());
        }
    }

    //COMMON CALC
    private QuantityMeasurementDTO calculate(QuantityDTO q1Dto, QuantityDTO q2Dto, String operation) {

        if (q1Dto == null || q2Dto == null) {
            throw new QuantityMeasurementException("Input cannot be null");
        }

        try {
            Quantity<IMeasurable> q1 = createQuantity(q1Dto);
            Quantity<IMeasurable> q2 = createQuantity(q2Dto);

            Quantity<IMeasurable> result;

            if (operation.equals("add")) {
                result = q1.add(q2);
            } else {
                result = q1.subtract(q2);
            }

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    q1Dto.getValue(), q1Dto.getUnit(), q1Dto.getMeasurementType(),
                    q2Dto.getValue(), q2Dto.getUnit(), q2Dto.getMeasurementType(),
                    operation,
                    result.getValue(),
                    result.getUnit().getUnitName(),
                    result.getUnit().getClass().getSimpleName(),
                    null, false, null
            );

            repository.save(entity);
            return QuantityMeasurementDTO.fromEntity(entity);

        } catch (Exception e) {
            throw new QuantityMeasurementException(operation + " failed: " + e.getMessage());
        }
    }

    //HISTORY
    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(String operation) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByOperation(operation.toLowerCase()));
    }

    @Override
    public List<QuantityMeasurementDTO> getHistoryByMeasurementType(String type) {
        return QuantityMeasurementDTO.fromEntityList(repository.findByThisMeasurementType(type));
    }

    @Override
    public long getOperationCount(String operation) {
        return repository.countByOperationAndIsErrorFalse(operation.toLowerCase());
    }

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {
        return QuantityMeasurementDTO.fromEntityList(repository.findByIsErrorTrue());
    }

    //HELPER METHODS
    private Quantity<IMeasurable> createQuantity(QuantityDTO dto) {
        IMeasurable unit = getUnit(dto.getUnit(), dto.getMeasurementType());
        return new Quantity<>(dto.getValue(), unit);
    }

    private IMeasurable getUnit(String unitName, String type) {

        switch (type) {
            case "LengthUnit":
                return findUnit(LengthUnit.values(), unitName);

            case "WeightUnit":
                return findUnit(WeightUnit.values(), unitName);

            case "VolumeUnit":
                return findUnit(VolumeUnit.values(), unitName);

            case "TemperatureUnit":
                return findUnit(TemperatureUnit.values(), unitName);

            default:
                throw new QuantityMeasurementException("Invalid measurement type");
        }
    }

    private <T extends IMeasurable> T findUnit(T[] units, String name) {
        for (T unit : units) {
            if (unit.getUnitName().equalsIgnoreCase(name)) {
                return unit;
            }
        }
        throw new QuantityMeasurementException("Invalid unit");
    }
}