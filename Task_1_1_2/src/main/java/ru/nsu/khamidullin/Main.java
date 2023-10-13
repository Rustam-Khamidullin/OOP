package ru.nsu.khamidullin;


public class Main {
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new double[] {0.9});
        Polynomial p2 = new Polynomial(new double[] {0.3});

        System.out.println(p1.plus(p2.differentiate(1)).toString());
        System.out.println(p1.times(p2).evaluate(2));
        System.out.println(p1.times(p2));
    }
}