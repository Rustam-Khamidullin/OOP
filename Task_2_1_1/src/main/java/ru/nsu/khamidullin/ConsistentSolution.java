package ru.nsu.khamidullin;

import static ru.nsu.khamidullin.Checker.isPrime;

public class ConsistentSolution {
    public static boolean findPrimeConsistent(int[] array) {
        for (var elem : array) {
            if (isPrime(elem)) {
                return true;
            }
        }
        return false;
    }
}
