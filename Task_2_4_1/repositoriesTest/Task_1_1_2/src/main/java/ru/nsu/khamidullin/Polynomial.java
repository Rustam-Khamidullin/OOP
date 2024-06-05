package ru.nsu.khamidullin;

public class Polynomial {
    private double[] coefficients;
    private double error_rate = 0.0000001;


    public Polynomial(double[] inputCoef) {
        if (inputCoef.length == 0) {
            coefficients = new double[]{0};
        } else {
            coefficients = inputCoef.clone();
        }
        delZero();
    }

    public Polynomial(int[] inputCoef) {
        if (inputCoef.length == 0) {
            coefficients = new double[]{0};
        } else {
            coefficients = new double[inputCoef.length];
            for (int i = 0; i < inputCoef.length; i++) {
                coefficients[i] = inputCoef[i];
            }
        }
        delZero();
    }

    private String doubleToString(double x) {
        if (x % 1 == 0) {
            return String.valueOf((int) x);
        }
        return String.valueOf(x);
    }

    private boolean doubleEquals(double x, double y) {
        return Math.abs(x - y) < error_rate;
    }

    private void delZero() {
        int len = coefficients.length;
        while (len > 1 && doubleEquals(coefficients[len - 1], 0)) {
            len -= 1;
        }
        double[] newCoef = new double[len];
        System.arraycopy(coefficients, 0, newCoef, 0, len);
        coefficients = newCoef;
    }

    public double getError_rate() {
        return error_rate;
    }

    public void setError_rate(double error_rate) {
        this.error_rate = error_rate;
    }

    public double[] getCoefficients() {
        return coefficients.clone();
    }

    public Polynomial plus(Polynomial polynomial) {
        double[] coef = polynomial.getCoefficients();

        double[] res = new double[Math.max(coef.length, coefficients.length)];
        for (int i = 0; i < res.length; i++) {
            if (i < coef.length) {
                res[i] += coef[i];
            }
            if (i < coefficients.length) {
                res[i] += coefficients[i];
            }
        }
        return new Polynomial(res);
    }

    public Polynomial minus(Polynomial polynomial) {
        var negCoef = polynomial.getCoefficients();
        for (int i = 0; i < negCoef.length; i++) {
            negCoef[i] = -negCoef[i];
        }

        return plus(new Polynomial(negCoef));
    }

    public Polynomial times(Polynomial polynomial) {
        double[] coef = polynomial.getCoefficients();
        double[] newCoef = new double[coefficients.length + coef.length];

        for (int i = 0; i < coef.length; i++) {
            for (int j = 0; j < coefficients.length; j++) {
                newCoef[i + j] += coefficients[j] * coef[i];
            }
        }
        return new Polynomial(newCoef);
    }


    public double evaluate(double x) {
        double res = 0;
        double X = 1;
        for (double coefficient : coefficients) {
            res += coefficient * X;
            X *= x;
        }
        return res;
    }


    public Polynomial differentiate(int n) {
        double[] newCoef = new double[coefficients.length - n];

        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }

        for (int i = 0; i < newCoef.length; i++) {
            newCoef[i] = coefficients[i + n] * fact;
            fact = fact * (i + n + 1) / (i + 1);
        }
        return new Polynomial(newCoef);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        var polynomial = (Polynomial) obj;

        double[] coef = polynomial.getCoefficients();

        if (coefficients.length != coef.length) {
            return false;
        }
        for (int i = 0; i < coefficients.length; i++) {
            if (!doubleEquals(coefficients[i], coef[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        int len = coefficients.length;

        if (len > 2) {
            if (coefficients[len - 1] < 0) {
                str.append("- ");
            }
            str.append(doubleToString(Math.abs(coefficients[len - 1])));
            str.append("x^" + (len - 1));
        }

        for (int i = coefficients.length - 2; i > 0; i--) {
            if (coefficients[i] != 0) {
                if (coefficients[i] > 0) {
                    str.append(" + ");
                    if (coefficients[i] != 1) {
                        str.append(doubleToString(coefficients[i]));
                    }
                } else if (coefficients[i] < 0) {
                    str.append(" - ");
                    if (coefficients[i] != -1) {
                        str.append(doubleToString(-coefficients[i]));
                    }
                }
                if (i > 1) {
                    str.append("x^").append(i);
                } else {
                    str.append("x");
                }
            }
        }

        if (coefficients[0] < 0) {
            str.append(" - ").append(doubleToString(-coefficients[0]));
        } else if (len > 1 && coefficients[0] != 0) {
            str.append(" + ").append(doubleToString(coefficients[0]));
        } else if (len == 1) {
            str.append(doubleToString(coefficients[0]));
        }
        return str.toString();
    }
}