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
    public void testCosDivisionPiSqrt() {
        String expression = "- cos / pi 4 / sqrt 2 2";
        double result = calculate(expression);
        assertEquals(0, result, 1e-6);
    }

    @Test
    public void testCosPi() {
        String expression = "cos pi";
        double result = calculate(expression);
        assertEquals(-1, result, 1e-6);
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
        assertThrows(ArithmeticException.class, () -> calculate(expression));
    }

    @Test
    public void testDivisionByZero() {
        String expression = "/ 1324 0";
        assertThrows(ArithmeticException.class, () -> calculate(expression));
    }

    @Test
    public void testIncorrectLog1() {
        String expression = "log 1 100";
        assertThrows(ArithmeticException.class, () -> calculate(expression));
    }

    @Test
    public void testIncorrectLog2() {
        String expression = "log -10 100";
        assertThrows(ArithmeticException.class, () -> calculate(expression));
    }

    @Test
    public void testIncorrectLog3() {
        String expression = "log 10 -100";
        assertThrows(ArithmeticException.class, () -> calculate(expression));
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

    @Test
    public void testInvalidAdditional() {
        String expression = "+ 1";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidSubtraction() {
        String expression = "- 1";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidMultiplication() {
        String expression = "* 1";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidPow() {
        String expression = "pow 1";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidSin() {
        String expression = "sin";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidCos() {
        String expression = "cos";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }

    @Test
    public void testInvalidSqrt() {
        String expression = "sqrt";
        assertThrows(IllegalArgumentException.class, () -> calculate(expression));
    }
}