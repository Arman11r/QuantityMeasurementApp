package com.feetmeasurement;

import com.quantitymeasurement.IQuantityMeasurementRepository;
import com.quantitymeasurement.IQuantityMeasurementService;
import com.quantitymeasurement.QuantityMeasurementCacheRepository;
import com.quantitymeasurement.QuantityMeasurementController;
import com.quantitymeasurement.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    private static QuantityMeasurementApp instance;

    public final QuantityMeasurementController controller;
    public final IQuantityMeasurementRepository repository;

    private QuantityMeasurementApp() {
        this.repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = createQuantityMeasurementService(repository);
        this.controller = createQuantityMeasurementController(service);
    }

    public static QuantityMeasurementApp getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }
        return instance;
    }

    private static IQuantityMeasurementService createQuantityMeasurementService(
            IQuantityMeasurementRepository repository) {
        return new QuantityMeasurementServiceImpl(repository);
    }

    private static QuantityMeasurementController createQuantityMeasurementController(
            IQuantityMeasurementService service) {
        return new QuantityMeasurementController(service);
    }

    public static void main(String[] args) {
        QuantityMeasurementApp.getInstance().controller.demonstrateQuantityMeasurements();
    }
}