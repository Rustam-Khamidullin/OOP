package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Log implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        try {
            var first = stack.pop();
            var second = stack.pop();

            stack.push(Math.log(second) / Math.log(first));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }
}
