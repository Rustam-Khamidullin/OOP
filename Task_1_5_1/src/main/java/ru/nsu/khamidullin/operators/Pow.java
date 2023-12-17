package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * Pow operator.
 */
public class Pow implements Operator {
    @Override
    public double apply(Stack<Double> stack) throws IllegalArgumentException {
        double first;
        double second;

        try {
            first = stack.pop();
            second = stack.pop();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect expression");
        }

        double result = Math.pow(first, second);

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Incorrect pow argument");
        }

        return result;
    }
}
