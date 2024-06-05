package ru.nsu.khamidullin;

public class HeapSort {
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

        if (child1 < len && arr[posMin] > arr[child1]) {
            posMin = child1;
        }
        if (child2 < len && arr[posMin] > arr[child2]) {
            posMin = child2;
        }

        if (posMin != pos) {
            int tmp = arr[pos];
            arr[pos] = arr[posMin];
            arr[posMin] = tmp;
            sift_down(arr, posMin, len);
        }
    }

    /**
     * Sorting function
     *
     * @param arr is the array to be sorted
     * @return Sorted array
     */
    public static int[] heapsort(int[] arr) {
        int []res = arr.clone();
        int len = res.length;

        for (int i = 1; i < len; i++) {
            sift_up(res, i);
        }


        for (int i = 0; i < len; i++) {
            int tmp = res[0];
            res[0] = res[len - i - 1];
            res[len - i - 1] = tmp;
            sift_down(res, 0, len - i - 1);
        }

        for (int i = 0; i < len / 2; i++) {
            int tmp = res[i];
            res[i] = res[len - 1 - i];
            res[len - 1 - i] = tmp;
        }

        return res;
    }

}
