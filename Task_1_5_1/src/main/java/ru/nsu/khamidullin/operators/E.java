package ru.nsu.khamidullin.operators;

import java.util.Stack;

public class E implements Operator {
    @Override
    public double apply(Stack<Double> stack) {
        return Math.E;
    }
}
