package com.app.quantitymeasurement.entity;

import java.sql.Timestamp;

public class QuantityMeasurementEntity {

    private int id;

    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String resultString;

    private boolean isError;
    private String errorMessage;

    private Timestamp createdAt;
    private Timestamp updatedAt;

    public QuantityMeasurementEntity() {
    }

    public QuantityMeasurementEntity(
            double thisValue,
            String thisUnit,
            String thisMeasurementType,
            double thatValue,
            String thatUnit,
            String thatMeasurementType,
            String operation,
            double resultValue,
            String resultUnit,
            String resultMeasurementType,
            String resultString,
            boolean isError,
            String errorMessage
    ) {
        this.thisValue = thisValue;
        this.thisUnit = thisUnit;
        this.thisMeasurementType = thisMeasurementType;

        this.thatValue = thatValue;
        this.thatUnit = thatUnit;
        this.thatMeasurementType = thatMeasurementType;

        this.operation = operation;

        this.resultValue = resultValue;
        this.resultUnit = resultUnit;
        this.resultMeasurementType = resultMeasurementType;

        this.resultString = resultString;

        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getThisValue() {
        return thisValue;
    }

    public void setThisValue(double thisValue) {
        this.thisValue = thisValue;
    }

    public String getThisUnit() {
        return thisUnit;
    }

    public void setThisUnit(String thisUnit) {
        this.thisUnit = thisUnit;
    }

    public String getThisMeasurementType() {
        return thisMeasurementType;
    }

    public void setThisMeasurementType(String thisMeasurementType) {
        this.thisMeasurementType = thisMeasurementType;
    }


    public double getThatValue() {
        return thatValue;
    }

    public void setThatValue(double thatValue) {
        this.thatValue = thatValue;
    }

    public String getThatUnit() {
        return thatUnit;
    }

    public void setThatUnit(String thatUnit) {
        this.thatUnit = thatUnit;
    }

    public String getThatMeasurementType() {
        return thatMeasurementType;
    }

    public void setThatMeasurementType(String thatMeasurementType) {
        this.thatMeasurementType = thatMeasurementType;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }


    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }

    public String getResultMeasurementType() {
        return resultMeasurementType;
    }

    public void setResultMeasurementType(String resultMeasurementType) {
        this.resultMeasurementType = resultMeasurementType;
    }


    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }


    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }


    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}