package com.quantitymeasurement;

import com.length.LengthUnit;
import com.measurement.IMeasurable;
import com.measurement.Quantity;
import com.temperature.TemperatureUnit;
import com.volume.VolumeUnit;
import com.weight.WeightUnit;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private enum ArithmeticOp { ADD, SUBTRACT }

    @Override
    public boolean compare(QuantityDTO thisQty, QuantityDTO thatQty) {

        validateNotNull(thisQty, "First quantity");
        validateNotNull(thatQty, "Second quantity");

        try {

            Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
            Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

            boolean result = q1.equals(q2);

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, "COMPARISON", String.valueOf(result)));

            return result;

        } catch (Exception e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, "COMPARISON", e.getMessage(), true));

            throw new QuantityMeasurementException(
                    "Comparison failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO thisQty, QuantityDTO targetUnit) {

        validateNotNull(thisQty, "Source quantity");
        validateNotNull(targetUnit, "Target unit");

        try {

            Quantity<IMeasurable> q = toQuantityModel(thisQty);

            IMeasurable target =
                    resolveUnit(targetUnit.getUnit(), targetUnit.getMeasurementType());

            Quantity<IMeasurable> converted = q.convertTo(target);

            QuantityDTO result = toDTO(converted);

            repository.save(new QuantityMeasurementEntity(
                    thisQty, targetUnit, "CONVERSION", result));

            return result;

        } catch (QuantityMeasurementException e) {
            throw e;

        } catch (Exception e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, targetUnit, "CONVERSION", e.getMessage(), true));

            throw new QuantityMeasurementException(
                    "Conversion failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQty, QuantityDTO thatQty) {
        return add(thisQty, thatQty, thisQty);
    }

    @Override
    public QuantityDTO add(
            QuantityDTO thisQty,
            QuantityDTO thatQty,
            QuantityDTO targetUnit) {

        return performArithmetic(thisQty, thatQty, targetUnit, ArithmeticOp.ADD);
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQty, QuantityDTO thatQty) {
        return subtract(thisQty, thatQty, thisQty);
    }

    @Override
    public QuantityDTO subtract(
            QuantityDTO thisQty,
            QuantityDTO thatQty,
            QuantityDTO targetUnit) {

        return performArithmetic(thisQty, thatQty, targetUnit, ArithmeticOp.SUBTRACT);
    }

    @Override
    public double divide(QuantityDTO thisQty, QuantityDTO thatQty) {

        validateNotNull(thisQty, "Dividend");
        validateNotNull(thatQty, "Divisor");

        try {

            Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
            Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

            double result = q1.divide(q2);

            repository.save(new QuantityMeasurementEntity(
                    thisQty,
                    thatQty,
                    "DIVIDE",
                    new QuantityDTO(result, "SCALAR", "None")));

            return result;

        } catch (ArithmeticException e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, "DIVIDE", e.getMessage(), true));

            throw e;

        } catch (UnsupportedOperationException e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, "DIVIDE", e.getMessage(), true));

            throw new QuantityMeasurementException(e.getMessage(), e);

        } catch (IllegalArgumentException e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, "DIVIDE", e.getMessage(), true));

            throw new QuantityMeasurementException(
                    "Division failed: " + e.getMessage(), e);
        }
    }

    private QuantityDTO performArithmetic(
            QuantityDTO thisQty,
            QuantityDTO thatQty,
            QuantityDTO targetUnitDTO,
            ArithmeticOp op) {

        validateNotNull(thisQty, "First quantity");
        validateNotNull(thatQty, "Second quantity");

        try {

            Quantity<IMeasurable> q1 = toQuantityModel(thisQty);
            Quantity<IMeasurable> q2 = toQuantityModel(thatQty);

            IMeasurable target =
                    resolveUnit(targetUnitDTO.getUnit(),
                            targetUnitDTO.getMeasurementType());

            Quantity<IMeasurable> result;

            if (op == ArithmeticOp.ADD) {
                result = q1.add(q2, target);
            } else {
                result = q1.subtract(q2, target);
            }

            QuantityDTO resultDTO = toDTO(result);

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, op.name(), resultDTO));

            return resultDTO;

        } catch (UnsupportedOperationException e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, op.name(), e.getMessage(), true));

            throw new QuantityMeasurementException(e.getMessage(), e);

        } catch (IllegalArgumentException e) {

            repository.save(new QuantityMeasurementEntity(
                    thisQty, thatQty, op.name(), e.getMessage(), true));

            throw new QuantityMeasurementException(
                    "Cannot perform arithmetic between different measurement categories: "
                            + thisQty.getMeasurementType()
                            + " and "
                            + thatQty.getMeasurementType(), e);
        }
    }

    private Quantity<IMeasurable> toQuantityModel(QuantityDTO dto) {

        IMeasurable unit =
                resolveUnit(dto.getUnit(), dto.getMeasurementType());

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

        throw new QuantityMeasurementException(
                "Unknown measurement type: " + measurementType);
    }

    private <U extends IMeasurable> U findUnit(U[] values, String unitName) {

        for (U u : values) {
            if (u.getUnitName().equalsIgnoreCase(unitName))
                return u;
        }

        throw new QuantityMeasurementException("Invalid unit: " + unitName);
    }

    private QuantityDTO toDTO(Quantity<IMeasurable> q) {

        return new QuantityDTO(
                q.getValue(),
                q.getUnit().getUnitName(),
                q.getUnit().getClass().getSimpleName());
    }

    private void validateNotNull(QuantityDTO dto, String name) {

        if (dto == null)
            throw new QuantityMeasurementException(name + " cannot be null");
    }
}