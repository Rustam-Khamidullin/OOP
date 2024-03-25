import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.pizza.Pizzeria;
import ru.nsu.khamidullin.pizza.PizzeriaConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

class PizzeriaTest {
    private Pizzeria pizzeria;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        pizzeria = null;
        System.setOut(new java.io.PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testPizzeriaConfiguration() throws IOException, IllegalAccessException {
        PizzeriaConfiguration pizzeriaConfiguration = new PizzeriaConfiguration();
        pizzeriaConfiguration.setBakersCookingTime(List.of(1000, 1000));
        pizzeriaConfiguration.setDeliveriesCapacity(List.of(3, 5));
        pizzeriaConfiguration.setStorageCapacity(20);

        pizzeria = new Pizzeria(100, false, pizzeriaConfiguration);
        PizzeriaConfiguration pizzeriaConfigurationActual = pizzeria.getPizzeriaConfiguration();

        Assertions.assertIterableEquals(
                pizzeriaConfigurationActual.getBakersCookingTime(),
                List.of(1000, 1000));

        Assertions.assertIterableEquals(
                pizzeriaConfigurationActual.getDeliveriesCapacity(),
                List.of(3, 5));

        Assertions.assertEquals(
                pizzeriaConfigurationActual.getStorageCapacity(),
                20);
    }

    @Test
    void testPizzeriaBackers() throws IOException, IllegalAccessException, InterruptedException {
        PizzeriaConfiguration pizzeriaConfiguration = new PizzeriaConfiguration();
        pizzeriaConfiguration.setBakersCookingTime(List.of());
        pizzeriaConfiguration.setDeliveriesCapacity(List.of(3, 5));
        pizzeriaConfiguration.setStorageCapacity(20);

        pizzeria = new Pizzeria(500, false, pizzeriaConfiguration);

        pizzeria.start();

        pizzeria.addOrder(1);
        pizzeria.addOrder(2);
        pizzeria.addOrder(3);

        pizzeria.join();

        Assertions.assertEquals(pizzeria.getOrders().getQueue().size(), 3);
        Assertions.assertTrue(pizzeria.getOrders().getQueue().contains(1));
        Assertions.assertTrue(pizzeria.getOrders().getQueue().contains(2));
        Assertions.assertTrue(pizzeria.getOrders().getQueue().contains(3));
    }

    @Test
    void testPizzeriaStorage() throws IOException, IllegalAccessException, InterruptedException {
        PizzeriaConfiguration pizzeriaConfiguration = new PizzeriaConfiguration();
        pizzeriaConfiguration.setBakersCookingTime(List.of(10, 10, 10));
        pizzeriaConfiguration.setDeliveriesCapacity(List.of());
        pizzeriaConfiguration.setStorageCapacity(20);

        pizzeria = new Pizzeria(500, false, pizzeriaConfiguration);

        pizzeria.start();

        pizzeria.addOrder(1);
        pizzeria.addOrder(2);
        pizzeria.addOrder(3);

        pizzeria.join();

        Assertions.assertEquals(pizzeria.getOrders().getQueue().size(), 0);
        Assertions.assertEquals(pizzeria.getStorage().getQueue().size(), 3);
        Assertions.assertTrue(pizzeria.getStorage().getQueue().contains(1));
        Assertions.assertTrue(pizzeria.getStorage().getQueue().contains(2));
        Assertions.assertTrue(pizzeria.getStorage().getQueue().contains(3));
    }
}

