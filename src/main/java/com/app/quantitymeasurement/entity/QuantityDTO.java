package com.app.quantitymeasurement.entity;

import java.util.Objects;

public final class QuantityDTO {

    private final double value;
    private final String unit;
    private final String measurementType;

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return "LengthUnit"; }
    }

    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAM, GRAM, POUND, TONNE;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return "WeightUnit"; }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return "VolumeUnit"; }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT, KELVIN;
        public String getUnitName() { return name(); }
        public String getMeasurementType() { return "TemperatureUnit"; }
    }

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getMeasurementType() { return measurementType; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantityDTO)) return false;
        QuantityDTO other = (QuantityDTO) obj;
        return Double.compare(this.value, other.value) == 0
                && this.unit.equals(other.unit)
                && this.measurementType.equals(other.measurementType);
    }
    @Override
    public int hashCode() {
        return Objects.hash(value, unit, measurementType);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}