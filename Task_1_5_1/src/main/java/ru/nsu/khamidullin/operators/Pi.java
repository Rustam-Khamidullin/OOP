package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Pi implements Operator {
    @Override
    public void apply(Stack<Double> stack) throws IllegalArgumentException {
        stack.push(Math.PI);
    }
}
