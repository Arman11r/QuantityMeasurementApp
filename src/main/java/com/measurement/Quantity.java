package com.measurement;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }



    private enum ArithmeticOperation {

        ADD {
            @Override
            public double compute(double a, double b) {
                return a + b;
            }
        },

        SUBTRACT {
            @Override
            public double compute(double a, double b) {
                return a - b;
            }
        },

        DIVIDE {
            @Override
            public double compute(double a, double b) {

                if (Math.abs(b) < EPSILON)
                    throw new ArithmeticException("Cannot divide by zero");

                return a / b;
            }
        };

        public abstract double compute(double a, double b);
    }



    private void validateArithmeticOperands(
            Quantity<U> other, U targetUnit, boolean targetUnitRequired) {

        if (other == null)
            throw new IllegalArgumentException("Second operand cannot be null");

        if (unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Cross category arithmetic not allowed");

        if (!Double.isFinite(value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Invalid numeric value");

        if (targetUnitRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }



    private double performBaseArithmetic(
            Quantity<U> other, ArithmeticOperation operation) {

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return operation.compute(base1, base2);
    }

    private double roundTwoDecimals(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

 

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double resultBase =
                performBaseArithmetic(other, ArithmeticOperation.ADD);

        double result =
                targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(roundTwoDecimals(result), targetUnit);
    }



    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        validateArithmeticOperands(other, targetUnit, true);

        double resultBase =
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

        double result =
                targetUnit.convertFromBaseUnit(resultBase);

        return new Quantity<>(roundTwoDecimals(result), targetUnit);
    }

   

    public double divide(Quantity<U> other) {

        validateArithmeticOperands(other, null, false);

        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }



    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        if (unit.getClass() != other.unit.getClass())
            return false;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}