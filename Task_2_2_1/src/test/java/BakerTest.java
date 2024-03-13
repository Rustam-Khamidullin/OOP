import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.pizza.Baker;
import ru.nsu.khamidullin.pizza.BlockingQueue;

/**
 * Test class.
 */
public class BakerTest {

    @Test
    public void testBaker() throws InterruptedException {
        BlockingQueue<Integer> orders = new BlockingQueue<>();
        BlockingQueue<Integer> storage = new BlockingQueue<>();
        int cookingTime = 500;

        Baker baker = new Baker(cookingTime, orders, storage);
        baker.start();

        for (int i = 1; i <= 3; i++) {
            orders.push(i);
        }

        Thread.sleep(2000);

        baker.interrupt();
        baker.join();

        for (int i = 1; i <= 3; i++) {
            Assertions.assertFalse(orders.getQueue().contains(i));
            Assertions.assertTrue(storage.getQueue().contains(i));
        }
    }
}