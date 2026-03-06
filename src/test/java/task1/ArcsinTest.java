package task1;

import static org.junit.jupiter.api.Assertions.*;

import org.example.task1.Arcsin;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class ArcsinTest {
    private final Arcsin arcsin = new Arcsin();
    private static final double ESP = 1E-8;
    private static final int MAX_ITER = 10000;
    private static final double MAX_ERROR = 1E-6;

    @ParameterizedTest
    @ValueSource(doubles = {-0.9, -0.5, -0.25, -0.1, 0.0, 0.1, 0.25, 0.5, 0.9})
    void testArbitraryPointsWithinDomain(double x) {
        double result = arcsin.calculate(x, ESP, MAX_ITER);
        double expected = Math.asin(x);
        double diff = Math.abs(result - expected);

        assertTrue(diff < MAX_ERROR, String.format("Для x=%.10f разница %.10f слишком велика", x, diff));
    }

    @Test
    void testBoundaryPoints() {
        assertEquals(
                -Math.PI / 2, arcsin.calculate(-1.0, ESP, MAX_ITER), MAX_ERROR, "arcsin(-1) должно быть равно -π/2");

        assertEquals(Math.PI / 2, arcsin.calculate(1.0, ESP, MAX_ITER), MAX_ERROR, "arcsin(1) должно быть равно π/2");
    }

    @Test
    void testCenterPoint() {
        assertEquals(0.0, arcsin.calculate(0.0, ESP, MAX_ITER), MAX_ERROR, "arcsin(0) должно быть равно 0");
    }

    @Test
    void testInvalidInputPoints() {
        assertThrows(IllegalArgumentException.class, () -> arcsin.calculate(1.001, ESP, MAX_ITER));
        assertThrows(IllegalArgumentException.class, () -> arcsin.calculate(-1.001, ESP, MAX_ITER));
        assertThrows(IllegalArgumentException.class, () -> arcsin.calculate(Double.POSITIVE_INFINITY, ESP, MAX_ITER));
    }

    @ParameterizedTest
    @CsvSource({"0.5, 1000", "0.5, 500", "0.5, 200", "0.1, 500", "0.1, 200", "0.01, 200"})
    void testConvergenceDifferentTolerance(double x, int iterations) {
        double result = arcsin.calculate(x, ESP, iterations);
        double expected = Math.asin(x);

        assertTrue(Math.abs(result - expected) < MAX_ERROR);
    }

    @Test
    void testNearBoundaryPoints() {
        double resultPos = arcsin.calculate(0.99, ESP, MAX_ITER);
        double resultNeg = arcsin.calculate(-0.99, ESP, MAX_ITER);

        assertEquals(Math.asin(0.99), resultPos, MAX_ERROR);
        assertEquals(Math.asin(-0.99), resultNeg, MAX_ERROR);
    }

    @Test
    void testNonConvergence() {
        assertThrows(ArithmeticException.class, () -> arcsin.calculate(0.9, 1e-20, 10));
    }

    @Test
    void testNegativeZero() {
        double result = arcsin.calculate(-0.0, ESP, MAX_ITER);
        assertEquals(0.0, result, MAX_ERROR);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void testInvalidSpecialValues(double x) {
        assertThrows(IllegalArgumentException.class, () -> arcsin.calculate(x, ESP, MAX_ITER));
    }

    @ParameterizedTest
    @CsvSource({"-1e-8, 100", "0, 100"})
    void testInvalidToleranceAndN(double tolerance, int n) {
        assertThrows(IllegalArgumentException.class, () -> arcsin.calculate(0.5, tolerance, n));
    }

    @Test
    void testFuzzy() {
        int iterations = Integer.getInteger("fuzzy.iterations", 100000);
        java.util.Random rand = new java.util.Random(42);

        for (int i = 0; i < iterations; i++) {
            double x = -0.999 + rand.nextDouble() * 1.998; // [-0.999, 0.999]

            double result = arcsin.calculate(x, ESP, MAX_ITER);
            double expected = Math.asin(x);

            assertEquals(expected, result, 1E-4, String.format("Fuzzy fail на итерации %d, x=%.10f", i, x));
        }

        System.out.println("Fuzzy тест: " + iterations + " итераций пройдено");
    }
}
