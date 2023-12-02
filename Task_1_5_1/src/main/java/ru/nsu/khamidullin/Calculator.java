package ru.nsu.khamidullin;

import java.util.Scanner;
import java.util.Stack;
import ru.nsu.khamidullin.operators.Operator;
import ru.nsu.khamidullin.operators.OperatorFactory;

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

                stack.push(operator.apply(stack));
            }
        }

        if (stack.size() == 1) {
            return stack.pop();
        } else {
            throw new IllegalArgumentException("Incorrect expression");
        }
    }

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            while (true) {
                var expression = scanner.nextLine();

                if (expression.isEmpty()) {
                    break;
                }

                try {
                    var result = calculate(expression);
                    System.out.println(result);
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
