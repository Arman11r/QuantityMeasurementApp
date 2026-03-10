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
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> length2 =
                new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(demonstrateEquality(length1, length2));
        System.out.println(demonstrateConversion(length1, LengthUnit.INCHES));
        System.out.println(demonstrateAddition(length1, length2, LengthUnit.FEET));
        System.out.println(demonstrateSubtraction(length1, length2, LengthUnit.FEET));
        System.out.println(demonstrateDivision(length1, length2));



        Quantity<WeightUnit> weight1 =
                new Quantity<>(10.0, WeightUnit.KILOGRAM);

        Quantity<WeightUnit> weight2 =
                new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println(demonstrateAddition(weight1, weight2, WeightUnit.KILOGRAM));
        System.out.println(demonstrateSubtraction(weight1, weight2, WeightUnit.KILOGRAM));
        System.out.println(demonstrateDivision(weight1, weight2));



        Quantity<VolumeUnit> volume1 =
                new Quantity<>(5.0, VolumeUnit.LITRE);

        Quantity<VolumeUnit> volume2 =
                new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(demonstrateAddition(volume1, volume2, VolumeUnit.LITRE));
        System.out.println(demonstrateSubtraction(volume1, volume2, VolumeUnit.LITRE));
        System.out.println(demonstrateDivision(volume1, volume2));
    }
}