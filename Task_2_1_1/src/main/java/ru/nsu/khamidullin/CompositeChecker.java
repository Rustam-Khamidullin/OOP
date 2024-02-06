package ru.nsu.khamidullin;

public class PrimeChecker {
    public static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        }
        int sqrt = (int) Math.floor(Math.sqrt(x));
        for (int i = 2; i <= sqrt; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
