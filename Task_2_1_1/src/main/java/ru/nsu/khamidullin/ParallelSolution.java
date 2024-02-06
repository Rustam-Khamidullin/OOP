package ru.nsu.khamidullin;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static ru.nsu.khamidullin.Checker.isPrime;
import static ru.nsu.khamidullin.ConsistentSolution.findPrimeConsistent;

public class ParallelSolution {
    public static boolean findPrimeParallel(int[] array, int threadNumber) {
        if (threadNumber <= 0) {
            throw new IllegalArgumentException("Incorrect thread number");
        }

        int blockSize = array.length / threadNumber;
        int start = 0;
        int end = blockSize;
        var futures = new Future[threadNumber];
        try (var executorService = Executors.newFixedThreadPool(threadNumber)) {
            for (int i = 0; i < threadNumber; i++) {
                futures[i] = executorService.submit(new FindPrimeTask(array, start, end));

                start = end;
                end = i == threadNumber - 2 ? array.length : end + blockSize;
            }

        }
        return false;
    }

    static class FindPrimeTask implements Callable<Boolean> {
        int start;
        int end;
        int[] arr;

        public FindPrimeTask(int[] arr, int start, int end) {
            this.start = start;
            this.end = end;
            this.arr = arr;
        }

        @Override
        public Boolean call() {
            for (int i = start; i < end; i++) {
                if (isPrime(arr[i])) {
                    return true;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {

        int[] arr = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};

        System.out.println(findPrimeParallel(arr, 1));

    }
}
