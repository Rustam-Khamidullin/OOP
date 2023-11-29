package ru.nsu.khamidullin;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.nsu.khamidullin.Calculator.calculate;

/**
 * Test class.
 */
public class TestCalculator {
    @Test
    public void testSinAddSubtract() {
        String expression = "sin + - 1 2 1";
        double result = calculate(expression);
        assertEquals(0, result, 1e-6);
    }

    @Test
    public void testPowMultiply() {
        String expression = "pow 2 * 3 4";
        double result = calculate(expression);
        assertEquals(4096, result, 1e-6);
    }

    @Test
    public void testSqrtAddPow() {
        String expression = "sqrt + pow 2 3 8";
        double result = calculate(expression);
        assertEquals(4, result, 1e-6);
    }

    @Test
    public void testCosDivide() {
        String expression = "cos / Pi 2";
        double result = calculate(expression);
        assertEquals(0, result, 1e-6);
    }

    @Test
    public void testLog() {
        String expression = "log e 10";
        double result = calculate(expression);
        assertEquals(Math.log(10), result, 1e-6);
    }

    @Test
    public void testSqrtNegative() {
        String expression = "sqrt -4";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidExpression() {
        String expression = "";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidFunction() {
        String expression = "unknownFunction 2";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }
}