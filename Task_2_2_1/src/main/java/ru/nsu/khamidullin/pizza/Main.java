package ru.nsu.khamidullin.pizza;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * The {@code Main} class serves as the entry point for the pizzeria simulation program.
 * It creates and runs a pizzeria instance, adds orders to it, and waits
 * for the simulation to complete.
 */
public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    /**
     * The main method of the program.
     *
     * @param args Command-line arguments (not used in this program).
     * @throws InterruptedException If an interruption occurs during the main execution.
     */
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;

        try {
            pizzeria = new Pizzeria(20000, true);
        } catch (IllegalAccessException | IOException e) {
            logger.error("Invalid format in pizzeria.json");
            return;
        }

        pizzeria.start();

        for (int i = 1; i <= 41; i++) {
            pizzeria.addOrder(i);
        }

        pizzeria.join();
    }
}
