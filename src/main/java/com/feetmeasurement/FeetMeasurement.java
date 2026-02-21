package com.feetmeasurement;

import com.length.Length;
import com.length.Length.LengthUnit;

public class FeetMeasurement{

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }

    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return Length.convert(value, from, to);
    }

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        Length resultFeet = demonstrateLengthAddition(l1, l2, LengthUnit.FEET);
        Length resultInches = demonstrateLengthAddition(l1, l2, LengthUnit.INCHES);
        Length resultYards = demonstrateLengthAddition(l1, l2, LengthUnit.YARDS);

        System.out.println(resultFeet);
        System.out.println(resultInches);
        System.out.println(resultYards);
    }
}