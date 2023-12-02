package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * Subtraction operator.
 */
public class Subtraction implements Operator {
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

        return first - second;
    }
}
