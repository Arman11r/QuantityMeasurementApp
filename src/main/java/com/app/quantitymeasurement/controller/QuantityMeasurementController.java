package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;


    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compareQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.compare(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }


    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convertQuantity(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.convert(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }


    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> addQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.add(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }


    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtractQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.subtract(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }


    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divideQuantities(
            @Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.divide(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }


    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(
            @PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }


    @GetMapping("/history/type/{measurementType}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistoryByType(
            @PathVariable String measurementType) {
        return ResponseEntity.ok(service.getHistoryByMeasurementType(measurementType));
    }


    @GetMapping("/history/errored")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrorHistory() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationCount(operation));
    }
}