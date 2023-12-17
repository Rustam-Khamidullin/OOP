package ru.nsu.khamidullin.operators;

import java.util.Stack;

/**
 * An interface representing a mathematical operator that can be applied to a stack of numbers.
 */
public interface Operator {
    /**
     * Applies the operator to the top elements of the stack and updates the stack accordingly.
     *
     * @param stack The stack of numbers on which the operator is applied.
     * @throws IllegalArgumentException If the operator cannot be applied due to invalid stack state or other reasons.
     */
    double apply(Stack<Double> stack);
}
