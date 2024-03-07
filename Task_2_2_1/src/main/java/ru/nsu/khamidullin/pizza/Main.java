package ru.nsu.khamidullin.pizza;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;
        try {
            pizzeria = new Pizzeria(100000);
        } catch (IllegalAccessException | IOException e) {
            System.out.println("Невверный формат pizzeria.json");
            return;
        }
        
        Thread pizzeriaThread = new Thread(pizzeria);
        pizzeriaThread.start();


        for (int i = 1; i < 150; i++) {
            pizzeria.addOrder(i);
        }
    }
}
