package com.app.quantitymeasurement.service;

import java.util.*;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.QuantityMeasurementException;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.unit.IMeasurable;
import com.app.quantitymeasurement.unit.LengthUnit;
import com.app.quantitymeasurement.unit.WeightUnit;
import com.app.quantitymeasurement.unit.VolumeUnit;
import com.app.quantitymeasurement.unit.TemperatureUnit;
import com.app.quantitymeasurement.quantity.Quantity;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private enum ArithmeticOp {
        ADD,
        SUBTRACT
    }

    @Override
    public boolean compare(QuantityDTO thisQty, QuantityDTO thatQty) {

        validateNotNull(thisQty);
        validateNotNull(thatQty);

        Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
        Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

        boolean result = q1.equals(q2);

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                thisQty.getValue(),
                thisQty.getUnit(),
                thisQty.getMeasurementType(),
                thatQty.getValue(),
                thatQty.getUnit(),
                thatQty.getMeasurementType(),
                "COMPARE",
                0,
                null,
                null,
                result ? "Equal" : "Not Equal",
                false,
                null
        );

        repository.save(entity);

        return result;
    }

    @Override
    public QuantityDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit) {

        validateNotNull(thisQty);
        validateNotNull(targetUnit);

        Quantity<IMeasurable> quantity = toQuantityModel(thisQty);

        IMeasurable target = resolveUnit(
                targetUnit.getUnit(),
                targetUnit.getMeasurementType()
        );

        Quantity<IMeasurable> result = quantity.convertTo(target);

        QuantityDTO dto = new QuantityDTO(
                result.getValue(),
                result.getUnit().getUnitName(),
                result.getUnit().getClass().getSimpleName()
        );

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                thisQty.getValue(),
                thisQty.getUnit(),
                thisQty.getMeasurementType(),
                0,
                null,
                null,
                "CONVERT",
                dto.getValue(),
                dto.getUnit(),
                dto.getMeasurementType(),
                null,
                false,
                null
        );

        repository.save(entity);

        return dto;
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQty, QuantityDTO thatQty) {
        return performArithmetic(thisQty, thatQty, ArithmeticOp.ADD);
    }
    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        return repository.getAllMeasurements();
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty) {
        return performArithmetic(thisQty, thatQty, ArithmeticOp.SUBTRACT);
    }

    @Override
    public double divide(QuantityDTO thisQty, QuantityDTO thatQty) {

        validateNotNull(thisQty);
        validateNotNull(thatQty);

        Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
        Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

        double result = q1.divide(q2);

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                thisQty.getValue(),
                thisQty.getUnit(),
                thisQty.getMeasurementType(),
                thatQty.getValue(),
                thatQty.getUnit(),
                thatQty.getMeasurementType(),
                "DIVIDE",
                result,
                "SCALAR",
                "None",
                null,
                false,
                null
        );

        repository.save(entity);

        return result;
    }

    private QuantityDTO performArithmetic(
            QuantityDTO thisQty,
            QuantityDTO thatQty,
            ArithmeticOp op
    ) {

        validateNotNull(thisQty);
        validateNotNull(thatQty);

        Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
        Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

        Quantity<IMeasurable> result;

        if (op == ArithmeticOp.ADD) {
            result = q1.add(q2);
        } else {
            result = q1.subtract(q2);
        }

        QuantityDTO dto = new QuantityDTO(
                result.getValue(),
                result.getUnit().getUnitName(),
                result.getUnit().getClass().getSimpleName()
        );

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                thisQty.getValue(),
                thisQty.getUnit(),
                thisQty.getMeasurementType(),
                thatQty.getValue(),
                thatQty.getUnit(),
                thatQty.getMeasurementType(),
                op.name(),
                dto.getValue(),
                dto.getUnit(),
                dto.getMeasurementType(),
                null,
                false,
                null
        );

        repository.save(entity);

        return dto;
    }

    private Quantity<IMeasurable> toQuantityModel(QuantityDTO dto) {

        IMeasurable unit = resolveUnit(dto.getUnit(), dto.getMeasurementType());

        return new Quantity<>(dto.getValue(), unit);
    }

    private IMeasurable resolveUnit(String unitName, String measurementType) {

        if (measurementType.equals("LengthUnit"))
            return findUnit(LengthUnit.values(), unitName);

        if (measurementType.equals("WeightUnit"))
            return findUnit(WeightUnit.values(), unitName);

        if (measurementType.equals("VolumeUnit"))
            return findUnit(VolumeUnit.values(), unitName);

        if (measurementType.equals("TemperatureUnit"))
            return findUnit(TemperatureUnit.values(), unitName);

        throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
    }

    private <U extends IMeasurable> U findUnit(U[] values, String unitName) {

        for (U u : values) {
            if (u.getUnitName().equalsIgnoreCase(unitName))
                return u;
        }

        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    private void validateNotNull(QuantityDTO dto) {

        if (dto == null)
            throw new QuantityMeasurementException("Quantity cannot be null");
    }
}