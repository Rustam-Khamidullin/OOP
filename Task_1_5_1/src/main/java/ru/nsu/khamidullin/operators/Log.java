package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Log implements Operator {
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

        double result = Math.log(second) / Math.log(first);

        if (!Double.isFinite(result)) {
            throw new ArithmeticException("Incorrect log arguments");
        }

        return result;
    }
}
