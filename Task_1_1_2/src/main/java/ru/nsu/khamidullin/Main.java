package ru.nsu.khamidullin;


public class Main {
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new int[] {4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new int[] {3, 2, 8});

        System.out.println(p1.plus(p2.differentiate(1)).toString());
        System.out.println(p1.times(p2).evaluate(2));
        System.out.println(p1.times(p2));
    }
}