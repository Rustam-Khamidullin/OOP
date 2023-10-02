package ru.nsu.khamidullin;

import static ru.nsu.khamidullin.HeapSort.heapsort;

/**
 * Class Main
 */
public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 4, 3, 2, 1};

        arr = heapsort(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}