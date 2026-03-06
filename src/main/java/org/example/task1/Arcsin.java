package org.example.task1;

public class Arcsin {
    public double calculate(double x, double tolerance, int n) {
        if (x < -1.0 || x > 1.0) {
            throw new IllegalArgumentException("Arcsin определён только для значений: [-1; 1], вы ввели: " + x + " !");
        }
        if (Math.abs(Math.abs(x) - 1.0) < 1E-10) {
            return (Double.compare(x, 0.0) >= 0 ? 1 : -1) * (Math.PI / 2);
        }
        if (tolerance <= 0.0) {
            throw new IllegalArgumentException("Точность eps должна быть положительной, получено: " + tolerance);
        }

        double sum = x;
        double term = x;

        for (int i = 1; i <= n; i++) {
            term *= ((2.0 * i - 1) * (2.0 * i - 1) * x * x) / ((2.0 * i) * (2.0 * i + 1));
            sum += term;

            if (Math.abs(term) < tolerance) {
                return sum;
            }
        }

        throw new ArithmeticException("Ряд не сошёлся за " + n + " итераций!");
    }
}
