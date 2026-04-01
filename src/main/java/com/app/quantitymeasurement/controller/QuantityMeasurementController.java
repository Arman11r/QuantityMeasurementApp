package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // Helper — grabs username from JWT (set by JwtFilter)
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return auth.getName(); // this is the email/username from the token
        }
        return null;
    }

    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDTO> compare(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.compare(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), getCurrentUsername()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDTO> convert(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.convert(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), getCurrentUsername()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDTO> add(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.add(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), getCurrentUsername()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDTO> subtract(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.subtract(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), getCurrentUsername()
        );
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDTO> divide(@Valid @RequestBody QuantityInputDTO input) {
        QuantityMeasurementDTO result = service.divide(
                input.getThisQuantityDTO(), input.getThatQuantityDTO(), getCurrentUsername()
        );
        return ResponseEntity.ok(result);
    }

    // Per-user history (NEW) ← frontend will call this
    @GetMapping("/history/me")
    public ResponseEntity<List<QuantityMeasurementDTO>> getMyHistory() {
        String username = getCurrentUsername();
        return ResponseEntity.ok(service.getHistoryByUsername(username));
    }

    // Existing endpoints unchanged
    @GetMapping("/history/operation/{operation}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByOperation(@PathVariable String operation) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation));
    }

    @GetMapping("/history/type/{type}")
    public ResponseEntity<List<QuantityMeasurementDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(service.getHistoryByMeasurementType(type));
    }

    @GetMapping("/history/errors")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErrors() {
        return ResponseEntity.ok(service.getErrorHistory());
    }

    @GetMapping("/count/{operation}")
    public ResponseEntity<Long> count(@PathVariable String operation) {
        return ResponseEntity.ok(service.getOperationCount(operation));
    }
}