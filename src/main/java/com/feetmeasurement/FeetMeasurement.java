package com.feetmeasurement;
import com.length.Length;
import com.length.LengthUnit;
public class FeetMeasurement {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static Length demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return new Length(value, from).convertTo(to);
    }

    public static Length demonstrateLengthAddition(Length l1, Length l2) {
        return l1.add(l2);
    }

    public static Length demonstrateLengthAddition(Length l1,
                                                   Length l2,
                                                   LengthUnit targetUnit) {
        return l1.add(l2, targetUnit);
    }

    public static void main(String[] args) {

        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.convertTo(LengthUnit.INCHES));
        System.out.println(l1.add(l2, LengthUnit.FEET));
        System.out.println(new Length(36.0, LengthUnit.INCHES)
                .equals(new Length(1.0, LengthUnit.YARDS)));
    }
}