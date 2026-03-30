package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotEmpty(message = "Unit cannot be empty")
    private String unit;

    @NotEmpty(message = "Measurement type cannot be empty")
    private String measurementType;

    // Valid measurement types
    private static final Set<String> VALID_TYPES = Set.of(
            "LengthUnit", "WeightUnit", "VolumeUnit", "TemperatureUnit"
    );

    // Valid units per type
    private static final Set<String> LENGTH_UNITS  = Set.of("FEET", "INCHES", "YARDS", "CENTIMETERS");
    private static final Set<String> WEIGHT_UNITS  = Set.of("KILOGRAM", "GRAM", "POUND", "TONNE");
    private static final Set<String> VOLUME_UNITS  = Set.of("LITRE", "MILLILITRE", "GALLON");
    private static final Set<String> TEMP_UNITS    = Set.of("CELSIUS", "FAHRENHEIT", "KELVIN");

    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isUnitValidForType() {
        if (unit == null || measurementType == null) return true; // let @NotNull/@NotEmpty handle nulls
        return switch (measurementType) {
            case "LengthUnit"      -> LENGTH_UNITS.contains(unit.toUpperCase());
            case "WeightUnit"      -> WEIGHT_UNITS.contains(unit.toUpperCase());
            case "VolumeUnit"      -> VOLUME_UNITS.contains(unit.toUpperCase());
            case "TemperatureUnit" -> TEMP_UNITS.contains(unit.toUpperCase());
            default                -> false;
        };
    }

    // Constructor using IMeasurableUnit (keeps backward compat with your existing code)
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

    // Keep your existing equals/hashCode/toString
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