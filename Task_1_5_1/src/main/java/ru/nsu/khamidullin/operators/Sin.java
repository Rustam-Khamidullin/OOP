package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * Sin operator.
 */
public class Sin implements Operator {
    @Override
    public double apply(Stack<Double> stack) throws IllegalArgumentException {
        double value;

        try {
            value = stack.pop();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect expression");
        }

        return Math.sin(value);
    }
}
