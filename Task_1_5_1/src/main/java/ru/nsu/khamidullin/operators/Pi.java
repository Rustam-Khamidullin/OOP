package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * Pi constant.
 */
public class Pi implements Operator {
    @Override
    public double apply(Stack<Double> stack) throws IllegalArgumentException {
        return Math.PI;
    }
}
