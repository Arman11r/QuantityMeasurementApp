package com.feetmeasurement;

import com.measurement.Quantity;
import com.length.LengthUnit;
import com.weight.WeightUnit;
import com.volume.VolumeUnit;
import com.temperature.TemperatureUnit;

public class QuantityMeasurementApp {

    public static <U extends com.measurement.IMeasurable>
    boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
        return q1.equals(q2);
    }

    public static <U extends com.measurement.IMeasurable>
    Quantity<U> demonstrateConversion(Quantity<U> q, U target) {
        return q.convertTo(target);
    }

    public static <U extends com.measurement.IMeasurable>
    Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U target) {
        return q1.add(q2, target);
    }

    public static <U extends com.measurement.IMeasurable>
    Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U target) {
        return q1.subtract(q2, target);
    }

    public static <U extends com.measurement.IMeasurable>
    double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
        return q1.divide(q2);
    }

    public static void main(String[] args) {


        Quantity<LengthUnit> length1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> length2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println(length1.equals(length2));
        System.out.println(length1.convertTo(LengthUnit.INCHES));
        System.out.println(length1.add(length2, LengthUnit.FEET));



        Quantity<WeightUnit> weight1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> weight2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println(weight1.equals(weight2));



        Quantity<VolumeUnit> volume1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> volume2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        System.out.println(volume1.equals(volume2));


        System.out.println("\n---- TEMPERATURE ----");

        Quantity<TemperatureUnit> temp1 =
                new Quantity<>(0.0, TemperatureUnit.CELSIUS);

        Quantity<TemperatureUnit> temp2 =
                new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

        System.out.println(temp1.equals(temp2));

        System.out.println(temp1.convertTo(TemperatureUnit.FAHRENHEIT));

        try {
            temp1.add(temp2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}