package ru.nsu.khamidullin.operators;

import java.lang.reflect.Constructor;

/**
 * Factory class responsible for creating instances of {@link Operator} based on input strings.
 * It supports basic arithmetic operators (+, -, *, /) and custom operators specified by their
 * class names.
 */
public class OperatorFactory {
    private static final String PACKAGE = "ru.nsu.khamidullin.operators.";

    /**
     * Creates an instance of {@link Operator} based on the provided operator string.
     *
     * @param operator The operator string representing an arithmetic
     *                 operation or a custom operator.
     * @return An instance of the corresponding {@link Operator} or {@code null} if the operator
     * is unknown.
     */
    public static Operator createOperator(String operator) {
        switch (operator) {
            case "+":
                return new Addition();
            case "-":
                return new Subtraction();
            case "*":
                return new Multiplication();
            case "/":
                return new Division();
            default:
                operator = PACKAGE + operator.substring(0, 1).toUpperCase()
                        + operator.substring(1).toLowerCase();

                try {
                    Class<?> operatorClass = Class.forName(operator);
                    Constructor<?> constructor = operatorClass.getConstructor();
                    return (Operator) constructor.newInstance();
                } catch (Exception e) {
                    return null;
                }
        }
    }
}
