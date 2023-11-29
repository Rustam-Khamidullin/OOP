package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Cos implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        try {
            stack.push(Math.cos(stack.pop()));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException();
        }
    }
}
