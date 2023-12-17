package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * Multiplication operator.
 */
public class Multiplication implements Operator {
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

        return first * second;
    }
}
