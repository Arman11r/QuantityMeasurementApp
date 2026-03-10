package com.feetmeasurement;

import com.measurement.Quantity;
import com.length.LengthUnit;
import com.weight.WeightUnit;
import com.volume.VolumeUnit;

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

    public static void main(String[] args) {


        Quantity<LengthUnit> length1 =
                new Quantity<>(1.0, LengthUnit.FEET);

        Quantity<LengthUnit> length2 =
                new Quantity<>(12.0, LengthUnit.INCHES);

        System.out.println(demonstrateEquality(length1, length2));
        System.out.println(demonstrateConversion(length1, LengthUnit.INCHES));
        System.out.println(demonstrateAddition(length1, length2, LengthUnit.FEET));



        Quantity<WeightUnit> weight1 =
                new Quantity<>(1.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> weight2 =
                new Quantity<>(1000.0, WeightUnit.GRAM);

        System.out.println(demonstrateEquality(weight1, weight2));
        System.out.println(demonstrateConversion(weight1, WeightUnit.GRAM));
        System.out.println(demonstrateAddition(weight1, weight2, WeightUnit.KILOGRAM));



        Quantity<VolumeUnit> volume1 =
                new Quantity<>(1.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> volume2 =
                new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> volume3 =
                new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println(demonstrateEquality(volume1, volume2));
        System.out.println(demonstrateConversion(volume1, VolumeUnit.MILLILITRE));
        System.out.println(demonstrateAddition(volume1, volume2, VolumeUnit.LITRE));

        System.out.println(demonstrateConversion(volume3, VolumeUnit.LITRE));
        System.out.println(demonstrateAddition(volume1, volume3, VolumeUnit.MILLILITRE));
    }
}