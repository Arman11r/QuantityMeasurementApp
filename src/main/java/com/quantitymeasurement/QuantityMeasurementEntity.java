package com.quantitymeasurement;

import java.io.Serializable;
import java.util.Objects;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private final double thisValue;
    private final String thisUnit;
    private final String thisMeasurementType;
    private final double thatValue;
    private final String thatUnit;
    private final String thatMeasurementType;
    private final String operation;
    private final double resultValue;
    private final String resultUnit;
    private final String resultMeasurementType;
    private final String resultString;
    private final boolean isError;
    private final String errorMessage;

    private QuantityMeasurementEntity(
            QuantityDTO thisQty, QuantityDTO thatQty, String operation,
            double resultValue, String resultUnit, String resultMeasurementType,
            String resultString, boolean isError, String errorMessage) {
        this.thisValue = thisQty.getValue();
        this.thisUnit = thisQty.getUnit();
        this.thisMeasurementType = thisQty.getMeasurementType();
        this.thatValue = thatQty.getValue();
        this.thatUnit = thatQty.getUnit();
        this.thatMeasurementType = thatQty.getMeasurementType();
        this.operation = operation;
        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;
        this.resultString = resultString;
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty,
            String operation, String resultString) {
        this(thisQty, thatQty, operation, 0.0, null, null, resultString, false, null);
    }

    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty,
            String operation, QuantityDTO result) {
        this(thisQty, thatQty, operation,
                result.getValue(), result.getUnit(), result.getMeasurementType(),
                null, false, null);
    }

    public QuantityMeasurementEntity(QuantityDTO thisQty, QuantityDTO thatQty,
            String operation, String errorMessage, boolean isError) {
        this(thisQty, thatQty, operation, 0.0, null, null, null, isError, errorMessage);
    }

    public double getThisValue()             { return thisValue; }
    public String getThisUnit()              { return thisUnit; }
    public String getThisMeasurementType()   { return thisMeasurementType; }
    public double getThatValue()             { return thatValue; }
    public String getThatUnit()              { return thatUnit; }
    public String getThatMeasurementType()   { return thatMeasurementType; }
    public String getOperation()             { return operation; }
    public double getResultValue()           { return resultValue; }
    public String getResultUnit()            { return resultUnit; }
    public String getResultMeasurementType() { return resultMeasurementType; }
    public String getResultString()          { return resultString; }
    public boolean hasError()                { return isError; }
    public String getErrorMessage()          { return errorMessage; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantityMeasurementEntity)) return false;
        QuantityMeasurementEntity other = (QuantityMeasurementEntity) obj;
        return Double.compare(thisValue, other.thisValue) == 0
                && Objects.equals(thisUnit, other.thisUnit)
                && Objects.equals(operation, other.operation)
                && Double.compare(thatValue, other.thatValue) == 0
                && Objects.equals(thatUnit, other.thatUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(thisValue, thisUnit, thatValue, thatUnit, operation);
    }

    @Override
    public String toString() {
        if (isError)
            return "[" + operation + "] " + thisValue + " " + thisUnit
                    + " OP " + thatValue + " " + thatUnit + " => ERROR: " + errorMessage;
        if (resultString != null)
            return "[" + operation + "] " + thisValue + " " + thisUnit
                    + " OP " + thatValue + " " + thatUnit + " => " + resultString;
        return "[" + operation + "] " + thisValue + " " + thisUnit
                + " OP " + thatValue + " " + thatUnit
                + " => " + resultValue + " " + resultUnit;
    }
}