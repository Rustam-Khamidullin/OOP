package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Cos implements Operator {
    @Override
    public double apply(Stack<Double> stack) {
        double value;

        try {
            value = stack.pop();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Incorrect expression");
        }

        return Math.cos(value);
    }
}
