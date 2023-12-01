package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class Pi implements Operator {
    @Override
    public double apply(Stack<Double> stack) throws IllegalArgumentException {
        return Math.PI;
    }
}
