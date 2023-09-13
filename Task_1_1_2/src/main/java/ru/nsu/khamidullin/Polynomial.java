package ru.nsu.khamidullin;

public class Polynomial {
    static int[] coefficients;

    public Polynomial(int[] coefficients) {
        if (coefficients.length == 0) {
            this.coefficients = new int[0];
        } else {
            this.coefficients = coefficients.clone();
        }
    }

    public static int[] getCof() {
        return coefficients;
    }

    public Polynomial plus(Polynomial polynomial) {
        int[] cof = polynomial.getCof().clone();
        if (cof.length > coefficients.length) {
            for (int i = 0; i < coefficients.length; i++) {
                cof[i] += coefficients[i];
            }
            coefficients = cof;
        } else {
            for (int i = 0; i < cof.length; i++) {
                coefficients[i] += cof[i];
            }
        }
        return this;
    }


    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = coefficients.length - 1; i > 0; i--) {
            if (coefficients[i] != 0) {
                if (coefficients[i] > 0) {
                    str.append(" + ");
                } else if (coefficients[i] < 0) {
                    str.append(" - ");
                }
                if (coefficients[i] != 1 && coefficients[i] != -1) {
                    str.append(coefficients[i]);
                }

                str.append("x^").append(i);
            }
        }

        if (coefficients.length > 1) {
            if (coefficients[0] > 0) {
                str.append(" + ").append(coefficients[0]);
            } else if (coefficients[0] < 0) {
                str.append(" - ").append(-coefficients[0]);
            }
        }

        return str.toString();
    }


}
