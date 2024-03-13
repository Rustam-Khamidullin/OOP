import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import ru.nsu.khamidullin.pizza.BlockingQueue;

/**
 * Test class.
 */
public class BlockingQueueTest {

    @Test
    public void testPushAndPop() throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(3);

        blockingQueue.push(1);
        blockingQueue.push(2);
        blockingQueue.push(3);

        Assertions.assertEquals(1, blockingQueue.pop());
        Assertions.assertEquals(2, blockingQueue.pop());
        Assertions.assertEquals(3, blockingQueue.pop());
    }

    @Test
    public void testEmptyQueue() {
        BlockingQueue<String> blockingQueue = new BlockingQueue<>();

        Assertions.assertTrue(blockingQueue.isEmpty());
    }

    @Test
    public void testNonEmptyQueue() throws InterruptedException {
        BlockingQueue<String> blockingQueue = new BlockingQueue<>();
        blockingQueue.push("Test");

        Assertions.assertFalse(blockingQueue.isEmpty());
        Assertions.assertEquals("Test", blockingQueue.pop());
    }

    @Test
    public void testSetAndGetQueue() throws InterruptedException {
        BlockingQueue<Double> blockingQueue = new BlockingQueue<>();
        blockingQueue.push(3.14);

        BlockingQueue<Double> newQueue = new BlockingQueue<>();
        newQueue.setQueue(blockingQueue.getQueue());

        Assertions.assertFalse(newQueue.isEmpty());
        Assertions.assertEquals(3.14, newQueue.pop());
    }

    @RepeatedTest(100)
    public void testMultipleThreads() throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(3);

        Thread pushThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    blockingQueue.push(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread popThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Assertions.assertEquals(i, blockingQueue.pop());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        pushThread.start();
        popThread.start();

        pushThread.join();
        popThread.join();
    }
}