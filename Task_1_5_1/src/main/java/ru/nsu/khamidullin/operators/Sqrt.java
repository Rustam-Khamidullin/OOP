package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Sqrt implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        try {
            var value = stack.pop();

            if (value < 0) {
                throw new IllegalArgumentException();
            }

            stack.push(Math.sqrt(value));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }

}
