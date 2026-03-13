package com.quantitymeasurement;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService quantityMeasurementService;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.quantityMeasurementService = service;
    }

    public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {
        try {
            boolean result = quantityMeasurementService.compare(q1, q2);
            System.out.println("--- Equality Demonstration ---");
            System.out.println("  Operation     : COMPARISON");
            System.out.println("  This Quantity : " + q1);
            System.out.println("  That Quantity : " + q2);
            System.out.println("  Result        : " + result);
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR [COMPARISON]: " + e.getMessage());
            throw e;
        }
    }

    public QuantityDTO performConversion(QuantityDTO source, QuantityDTO targetUnit) {
        try {
            QuantityDTO result = quantityMeasurementService.convert(source, targetUnit);
            System.out.println("--- Conversion Demonstration ---");
            System.out.println("  Operation : CONVERSION");
            System.out.println("  Input     : " + source);
            System.out.println("  Target    : " + targetUnit.getUnit());
            System.out.println("  Result    : " + result);
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("ERROR [CONVERSION]: " + e.getMessage());
            throw e;
        }
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {
        return performAddition(q1, q2, q1);
    }

    public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnit) {
        try {
            QuantityDTO result = quantityMeasurementService.add(q1, q2, targetUnit);
            System.out.println("--- Addition Demonstration ---");
            System.out.println("  Operation     : ADD");
            System.out.println("  This Quantity : " + q1);
            System.out.println("  That Quantity : " + q2);
            System.out.println("  Result        : " + result);
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("\u274C Error: " + e.getMessage());
            throw e;
        }
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {
        return performSubtraction(q1, q2, q1);
    }

    public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2, QuantityDTO targetUnit) {
        try {
            QuantityDTO result = quantityMeasurementService.subtract(q1, q2, targetUnit);
            System.out.println("--- Subtraction Demonstration ---");
            System.out.println("  Operation     : SUBTRACT");
            System.out.println("  This Quantity : " + q1);
            System.out.println("  That Quantity : " + q2);
            System.out.println("  Result        : " + result);
            return result;
        } catch (QuantityMeasurementException e) {
            System.err.println("\u274C Error: " + e.getMessage());
            throw e;
        }
    }

    public double performDivision(QuantityDTO q1, QuantityDTO q2) {
        try {
            double result = quantityMeasurementService.divide(q1, q2);
            System.out.println("--- Division Demonstration ---");
            System.out.println("  Operation     : DIVIDE");
            System.out.println("  This Quantity : " + q1);
            System.out.println("  That Quantity : " + q2);
            System.out.println("  Result        : " + result);
            return result;
        } catch (QuantityMeasurementException | ArithmeticException e) {
            System.err.println("ERROR [DIVISION]: " + e.getMessage());
            throw e;
        }
    }

    public void demonstrateQuantityMeasurements() {


        performComparison(
                new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES));
        System.out.println();

        performConversion(
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT));
        System.out.println();

        performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
                new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET));
        System.out.println();

        try {
            performAddition(
                    new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
                    new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
                    new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS));
        } catch (QuantityMeasurementException e) {
            System.out.println("  Temperature addition not supported: " + e.getMessage());
        }
        System.out.println();

        try {
            performAddition(
                    new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET),
                    new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM));
        } catch (QuantityMeasurementException e) {
            System.out.println("  Cross-category not supported: " + e.getMessage());
        }
        System.out.println();

        performDivision(
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET));

        System.out.println("\n========================================");
    }
}