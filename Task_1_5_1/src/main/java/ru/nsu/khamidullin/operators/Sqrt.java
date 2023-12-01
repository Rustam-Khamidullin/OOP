package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Sqrt implements Operator {
    @Override
    public double apply(Stack<Double> stack) throws IllegalArgumentException {
        double value;

        try {
            value = stack.pop();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect expression");
        }

        double result = Math.sqrt(value);

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Incorrect sqrt argument");
        }

        return result;
    }

}
