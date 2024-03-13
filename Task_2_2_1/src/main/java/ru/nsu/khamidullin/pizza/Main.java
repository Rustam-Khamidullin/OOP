package ru.nsu.khamidullin.pizza;

import java.io.IOException;

/**
 * The {@code Main} class serves as the entry point for the pizzeria simulation program.
 * It creates and runs a pizzeria instance, adds orders to it, and waits for the simulation to complete.
 */
public class Main {
    /**
     * The main method of the program.
     *
     * @param args Command-line arguments (not used in this program).
     * @throws InterruptedException If an interruption occurs during the main execution.
     */
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;

        try {
            // Create a pizzeria instance with a working time of 20,000 milliseconds
            pizzeria = new Pizzeria(20000);
        } catch (IllegalAccessException | IOException e) {
            System.out.println("Invalid format in pizzeria.json");
            return;
        }

        // Start the pizzeria simulation
        pizzeria.start();

        // Add orders to the pizzeria
        for (int i = 1; i <= 41; i++) {
            pizzeria.addOrder(i);
        }

        // Wait for the pizzeria simulation to complete
        pizzeria.join();
    }
}
