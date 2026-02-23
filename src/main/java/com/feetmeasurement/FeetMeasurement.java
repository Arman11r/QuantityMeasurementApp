package com.feetmeasurement;

import com.length.Length;
import com.length.Length.LengthUnit;

public class FeetMeasurement {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static Length demonstrateLengthConversion(double value,LengthUnit from, LengthUnit to) {
        double result = Length.convert(value, from, to);
        return new Length(result, to);
    }

    public static Length demonstrateLengthConversion(Length length,LengthUnit toUnit) {
        return length.convertTo(toUnit);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }

    public static void main(String[] args) {

        Length length1 = new Length(1.0, LengthUnit.FEET);
        Length length2 = new Length(12.0, LengthUnit.INCHES);

        Length resultFeet =
                demonstrateLengthAddition(length1, length2, LengthUnit.FEET);

        Length resultInches =
                demonstrateLengthAddition(length1, length2, LengthUnit.INCHES);

        Length resultYards =
                demonstrateLengthAddition(length1, length2, LengthUnit.YARDS);

        System.out.println(resultFeet);
        System.out.println(resultInches);
        System.out.println(resultYards);
    }
}