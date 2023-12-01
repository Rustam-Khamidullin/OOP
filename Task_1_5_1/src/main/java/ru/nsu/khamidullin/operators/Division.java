package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Division implements Operator {
    @Override
    public double apply(Stack<Double> stack) {
        double first;
        double second;

        try {
            first = stack.pop();
            second = stack.pop();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect expression");
        }

        double result = first / second;

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Division by zero");
        }

        return result;
    }
}
