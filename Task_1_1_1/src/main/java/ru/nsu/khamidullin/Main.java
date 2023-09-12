package ru.nsu.khamidullin;


public class Main {
    static void sift_up(int[] arr, int pos) {
        int parent = (pos + 1) / 2 - 1;
        if (pos != 0 && arr[parent] > arr[pos]) {
            int tmp = arr[pos];
            arr[pos] = arr[parent];
            arr[parent] = tmp;
            sift_up(arr, parent);
        }
    }

    static void sift_down(int[] arr, int pos, int len) {
        int child1 = pos * 2 + 1;
        int child2 = child1 + 1;
        int posMin = pos;

        if (child1 < len && arr[posMin] > arr[child1])
            posMin = child1;
        if (child2 < len && arr[posMin] > arr[child2])
            posMin = child2;

        if (posMin != pos) {
            int tmp = arr[pos];
            arr[pos] = arr[posMin];
            arr[posMin] = tmp;
            sift_down(arr, posMin, len);
        }
    }

    public static int[] heapsort(int[] arr) {
        int len = arr.length;

        for (int i = 1; i < len; i++)
            sift_up(arr, i);


        for (int i = 0; i < len; i++) {
            int tmp = arr[0];
            arr[0] = arr[len - i - 1];
            arr[len - i - 1] = tmp;
            sift_down(arr, 0, len - i - 1);
        }

        for (int i = 0; i < len / 2; i++) {
            int tmp = arr[i];
            arr[i] = arr[len - 1 - i];
            arr[len - 1 - i] = tmp;
        }

        return arr;
    }


    public static void main(String[] args) {
    }
}