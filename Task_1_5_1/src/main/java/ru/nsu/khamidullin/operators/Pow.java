package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Pow implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        try {
            var first = stack.pop();
            var second = stack.pop();

            stack.push(Math.pow(first, second));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }
}
