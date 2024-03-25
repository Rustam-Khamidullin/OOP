import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.pizza.BlockingQueue;
import ru.nsu.khamidullin.pizza.Deliveryman;

/**
 * Test class.
 */
public class DeliverymanTest {

    @Test
    public void testDeliveryman() throws InterruptedException {
        BlockingQueue<Integer> storage = new BlockingQueue<>();
        int capacity = 2;

        Deliveryman deliveryman = new Deliveryman(capacity, storage);
        deliveryman.start();

        for (int i = 1; i <= 3; i++) {
            storage.push(i);
        }

        Thread.sleep(1000);

        deliveryman.interrupt();
        deliveryman.join();

        Assertions.assertFalse(storage.isEmpty());
        Assertions.assertEquals(1, storage.getQueue().size());
    }
}