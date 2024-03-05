package ru.nsu.khamidullin.pizza;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;
        try {
            pizzeria = new Pizzeria();
        } catch (IllegalAccessException | IOException e) {
            System.out.println("Невверный формат pizzeria.json");
            return;
        }
        
        pizzeria.runPizzeria();

        int id = 1;
        while (true) {
            pizzeria.addOrder(id++);

            Thread.sleep(500);
        }
    }
}
