package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementApp {

    public final QuantityMeasurementController controller;

    private static QuantityMeasurementApp instance;

    private QuantityMeasurementApp() {

        IQuantityMeasurementRepository repository =
                QuantityMeasurementDatabaseRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        controller = new QuantityMeasurementController(service);
    }

    public static QuantityMeasurementApp getInstance() {

        if (instance == null) {
            instance = new QuantityMeasurementApp();
        }

        return instance;
    }

    public static void main(String[] args) {

        QuantityMeasurementApp app = getInstance();

        System.out.println("Quantity Measurement App Started");

        System.out.println("Stored Measurements: "
                + app.controller.getAllMeasurements().size());
    }
}