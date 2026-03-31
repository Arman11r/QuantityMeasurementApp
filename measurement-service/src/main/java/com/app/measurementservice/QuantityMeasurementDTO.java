package com.app.measurementservice;

import lombok
        .AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {

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

    private String errorMessage;
    private boolean error;


    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setThisValue(entity.getThisValue());
        dto.setThisUnit(entity.getThisUnit());
        dto.setThisMeasurementType(entity.getThisMeasurementType());
        dto.setThatValue(entity.getThatValue());
        dto.setThatUnit(entity.getThatUnit());
        dto.setThatMeasurementType(entity.getThatMeasurementType());
        dto.setOperation(entity.getOperation());
        dto.setResultValue(entity.getResultValue());
        dto.setResultUnit(entity.getResultUnit());
        dto.setResultMeasurementType(entity.getResultMeasurementType());
        dto.setResultString(entity.getResultString());
        dto.setErrorMessage(entity.getErrorMessage());
        dto.setError(entity.isError());
        return dto;
    }


    public QuantityMeasurementEntity toEntity() {
        return new QuantityMeasurementEntity(
                thisValue, thisUnit, thisMeasurementType,
                thatValue, thatUnit, thatMeasurementType,
                operation,
                resultValue, resultUnit, resultMeasurementType,
                resultString,
                error, errorMessage
        );
    }


    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> entities) {
        return entities.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> dtos) {
        return dtos.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
    }
}