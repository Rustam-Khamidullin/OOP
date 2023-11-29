package ru.nsu.khamidullin;

import ru.nsu.khamidullin.operators.Operator;
import ru.nsu.khamidullin.operators.OperatorFactory;
import java.util.Stack;

/**
 * A simple calculator that evaluates mathematical expressions in reverse Polish (postfix) notation.
 * Supports basic arithmetic operations and custom functions.
 */
public class Calculator {

    /**
     * Calculates the result of a mathematical expression in reverse Polish notation.
     *
     * @param expression The mathematical expression in reverse Polish notation.
     * @return The result of the calculation.
     * @throws IllegalArgumentException If the expression is invalid or contains unknown functions.
     */
    public static double calculate(String expression) {
        var tokens = expression.split("\\s+");

        var stack = new Stack<Double>();
        for (int i = tokens.length - 1; i >= 0; i--) {
            var token = tokens[i];

            if (token.isEmpty()) {
                throw new IllegalArgumentException("Empty token encountered");
            }

            try {
                stack.push(Double.valueOf(token));

            } catch (NumberFormatException ignored) {
                Operator operator = OperatorFactory.createOperator(token);

                if (operator == null) {
                    throw new IllegalArgumentException("Unknown function: " + token);
                }

                try {
                    operator.apply(stack);
                } catch (RuntimeException e) {
                    throw new IllegalArgumentException("Error applying operator: " + token, e);
                }
            }
        }

        if (stack.size() == 1) {
            return stack.pop();
        } else {
            throw new IllegalArgumentException("Incorrect expression");
        }
    }
}
