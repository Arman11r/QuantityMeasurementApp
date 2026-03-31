package com.app.measurementservice;

import com.app.measurementservice.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measure")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    //COMPARE
    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.compare(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }

    //CONVERT
    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.convert(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }

    //ADD
    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.add(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }

    //SUBTRACT
    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtract(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.subtract(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }

    //DIVIDE
    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divide(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.divide(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
        return ResponseEntity.ok(result);
    }

    //HISTORY BY OPERATION
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByOperation(@PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    //HISTORY BY TYPE
    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getHistoryByMeasurementType(type));
    }

    //ERROR HISTORY
    @GetMapping("/history/errors")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrors() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    //COUNT
    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> count(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationCount(operation));
    }
}