package com.feetmeasurement;

import com.measurement.Quantity;
import com.measurement.IMeasurable;
import com.length.LengthUnit;
import com.weight.WeightUnit;
import com.volume.VolumeUnit;
import com.temperature.TemperatureUnit;
import com.quantitymeasurement.IQuantityMeasurementRepository;
import com.quantitymeasurement.IQuantityMeasurementService;
import com.quantitymeasurement.QuantityDTO;
import com.quantitymeasurement.QuantityMeasurementCacheRepository;
import com.quantitymeasurement.QuantityMeasurementController;
import com.quantitymeasurement.QuantityMeasurementEntity;
import com.quantitymeasurement.QuantityMeasurementException;
import com.quantitymeasurement.QuantityMeasurementServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementTest {

    private static final double EPSILON = 1e-6;

    private static final QuantityMeasurementController controller =
            QuantityMeasurementApp.getInstance().controller;

    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        service = new QuantityMeasurementServiceImpl(new InMemoryTestRepository());
    }


    @Test
    void testIMeasurableInterface_LengthUnitImplementation() {
        IMeasurable unit = LengthUnit.FEET;
        assertNotNull(unit.getConversionFactor());
    }

    @Test
    void testIMeasurableInterface_WeightUnitImplementation() {
        IMeasurable unit = WeightUnit.KILOGRAM;
        assertNotNull(unit.getConversionFactor());
    }

    @Test
    void testIMeasurableInterface_VolumeUnitImplementation() {
        IMeasurable unit = VolumeUnit.LITRE;
        assertNotNull(unit.getConversionFactor());
    }

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testVolumeEquality_LitreMillilitre() {
        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testVolumeEquality_LitreGallon() {
        Quantity<VolumeUnit> q1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1.0, VolumeUnit.GALLON);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> converted = q.convertTo(LengthUnit.INCHES);
        assertEquals(12.0, converted.getValue(), EPSILON);
    }

    @Test
    void testWeightConversion() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> converted = q.convertTo(WeightUnit.GRAM);
        assertEquals(1000.0, converted.getValue(), EPSILON);
    }

    @Test
    void testVolumeConversion() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, result.getValue(), EPSILON);
    }

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = q1.add(q2, LengthUnit.FEET);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        Quantity<WeightUnit> result = q1.add(q2, WeightUnit.KILOGRAM);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAddition() {
        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.LITRE);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testVolumeAdditionWithGallon() {
        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> q2 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = q1.add(q2, VolumeUnit.GALLON);
        assertEquals(2.0, result.getValue(), 1e-3);
    }

    @Test
    void testCrossCategoryPrevention_LengthVsWeight() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(length.equals(weight));
    }

    @Test
    void testCrossCategoryPrevention_VolumeVsLength() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        assertFalse(volume.equals(length));
    }

    @Test
    void testConstructorValidation_NullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructorValidation_InvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testSubtraction_Length() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = q1.subtract(q2, LengthUnit.FEET);
        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testSubtraction_Volume() {
        Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> result = q1.subtract(q2, VolumeUnit.LITRE);
        assertEquals(4.5, result.getValue(), EPSILON);
    }

    @Test
    void testDivision_Length() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
        assertEquals(5.0, q1.divide(q2), EPSILON);
    }

    @Test
    void testDivision_Volume() {
        Quantity<VolumeUnit> q1 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(10.0, VolumeUnit.LITRE);
        assertEquals(0.5, q1.divide(q2), EPSILON);
    }

    @Test
    void testDivisionByZero() {
        Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> q1.divide(q2));
    }

    @Test
    void testTemperatureEquality_CelsiusToFahrenheit() {
        Quantity<TemperatureUnit> c = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(c.equals(f));
    }

    @Test
    void testTemperatureConversion() {
        Quantity<TemperatureUnit> c = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> f = c.convertTo(TemperatureUnit.FAHRENHEIT);
        assertEquals(212.0, f.getValue(), EPSILON);
    }

    @Test
    void testTemperatureUnsupportedAddition() {
        Quantity<TemperatureUnit> t1 = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> t2 = new Quantity<>(50.0, TemperatureUnit.CELSIUS);
        assertThrows(UnsupportedOperationException.class, () -> t1.add(t2));
    }


    @Test
    void testEntity_ComparisonConstruction() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(q1, q2, "COMPARISON", "true");
        assertEquals("COMPARISON", entity.getOperation());
        assertEquals("true", entity.getResultString());
        assertFalse(entity.hasError());
    }

    @Test
    void testEntity_ArithmeticConstruction() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(q1, q2, "ADD", result);
        assertEquals("ADD", entity.getOperation());
        assertEquals(2.0, entity.getResultValue(), EPSILON);
        assertEquals("FEET", entity.getResultUnit());
        assertFalse(entity.hasError());
    }

    @Test
    void testEntity_ErrorConstruction() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity(q1, q2, "ADD", "Different categories", true);
        assertTrue(entity.hasError());
        assertNotNull(entity.getErrorMessage());
    }

    @Test
    void testService_Compare_SameUnit() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        assertTrue(service.compare(q1, q2));
    }

    @Test
    void testService_Compare_DifferentUnit() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(service.compare(q1, q2));
    }

   
    @Test
    void testService_Convert_FeetToInches() {
        QuantityDTO source = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO result = service.convert(source, target);
        assertEquals(24.0, result.getValue(), EPSILON);
        assertEquals("INCHES", result.getUnit());
    }

    @Test
    void testService_Convert_CelsiusToFahrenheit() {
        QuantityDTO source = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO result = service.convert(source, target);
        assertEquals(212.0, result.getValue(), EPSILON);
    }

    @Test
    void testService_Add_Length() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = service.add(q1, q2, target);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("FEET", result.getUnit());
    }

    @Test
    void testService_Add_Weight() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO result = service.add(q1, q2, target);
        assertEquals(2.0, result.getValue(), EPSILON);
    }

    @Test
    void testService_Add_Temperature_ThrowsException() {
        QuantityDTO q1 = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO q2 = new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        assertThrows(QuantityMeasurementException.class, () -> service.add(q1, q2, target));
    }

    @Test
    void testService_Add_CrossCategory_ThrowsException() {
        QuantityDTO q1 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
        assertThrows(QuantityMeasurementException.class, () -> service.add(q1, q2));
    }

    @Test
    void testService_Subtract_Length() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = service.subtract(q1, q2, target);
        assertEquals(9.5, result.getValue(), EPSILON);
    }

    @Test
    void testService_Divide_Length() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        assertEquals(5.0, service.divide(q1, q2), EPSILON);
    }

    @Test
    void testService_Divide_ByZero_ThrowsArithmeticException() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);
        assertThrows(ArithmeticException.class, () -> service.divide(q1, q2));
    }

    @Test
    void testService_NullInput_ThrowsException() {
        assertThrows(QuantityMeasurementException.class,
                () -> service.compare(null, new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET)));
    }

    @Test
    void testController_Comparison_LengthEquality() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(controller.performComparison(q1, q2));
    }

    @Test
    void testController_Conversion_CelsiusToFahrenheit() {
        QuantityDTO source = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
        assertEquals(32.0, controller.performConversion(source, target).getValue(), EPSILON);
    }

    @Test
    void testController_Addition_FeetAndInches() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertEquals(2.0, controller.performAddition(q1, q2).getValue(), EPSILON);
    }

    @Test
    void testController_Subtraction() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(6.0, QuantityDTO.LengthUnit.INCHES);
        assertEquals(9.5, controller.performSubtraction(q1, q2).getValue(), EPSILON);
    }

    @Test
    void testController_Division() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
        assertEquals(5.0, controller.performDivision(q1, q2), EPSILON);
    }

    @Test
    void testLayerSeparation_ServiceIndependence() {
        IQuantityMeasurementService independentService =
                new QuantityMeasurementServiceImpl(new InMemoryTestRepository());
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        assertTrue(independentService.compare(q1, q2));
    }

    @Test
    void testLayerSeparation_ControllerIndependence() {
        QuantityMeasurementController ctrl = new QuantityMeasurementController(
                new QuantityMeasurementServiceImpl(new InMemoryTestRepository()));
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);
        assertTrue(ctrl.performComparison(q1, q2));
    }

    @Test
    void testIntegration_EndToEnd_LengthAddition() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
        QuantityDTO target = new QuantityDTO(0.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO result = controller.performAddition(q1, q2, target);
        assertNotNull(result);
        assertEquals(2.0, result.getValue(), EPSILON);
        assertEquals("FEET", result.getUnit());
        assertEquals("LengthUnit", result.getMeasurementType());
    }

    @Test
    void testIntegration_EndToEnd_TemperatureUnsupported() {
        assertThrows(QuantityMeasurementException.class,
                () -> controller.performAddition(
                        new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
                        new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
                        new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS)));
    }



    private static class InMemoryTestRepository implements IQuantityMeasurementRepository {
        private final List<QuantityMeasurementEntity> store = new ArrayList<>();

        @Override
        public void save(QuantityMeasurementEntity entity) { store.add(entity); }

        @Override
        public List<QuantityMeasurementEntity> getAllMeasurements() {
            return Collections.unmodifiableList(store);
        }
    }
}