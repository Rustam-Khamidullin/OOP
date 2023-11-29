package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Addition implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        try {
            var first = stack.pop();
            var second = stack.pop();

            stack.push(first + second);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }
}
