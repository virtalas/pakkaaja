package main.java.structures;

import main.java.huffman.HuffmanTree;

public class MinHeap {

    private HuffmanTree A[];
    private int heapSize;

    public MinHeap(int alphabetSize) {
        heapSize = -1;
        A = new HuffmanTree[alphabetSize];
    }

    public int getHeapSize() {
        return heapSize + 1;
    }

    public HuffmanTree min() {
        return A[0];
    }

    public HuffmanTree delMin() {
        HuffmanTree max = A[0];
        A[0] = A[heapSize];
        heapSize--;
        heapify(0);
        return max;
    }

    public void insert(HuffmanTree k) {
        heapSize++;
        int i = heapSize;
        while (i > 0 && A[parent(i)].frequency > k.frequency) {
            A[i] = A[parent(i)];
            i = parent(i);
        }
        A[i] = k;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest;

        if (r <= heapSize) {
            if (A[l].frequency < A[r].frequency) {
                smallest = l;
            } else {
                smallest = r;
            }
            if (A[i].frequency > A[smallest].frequency) {
                swap(i, smallest);
                heapify(smallest);
            }
        } else if (l == heapSize && A[i].frequency > A[l].frequency) {
            swap(i, l);
        }
    }

    private void swap(int a, int b) {
        HuffmanTree aTree = A[a];
        HuffmanTree bTree = A[b];
        A[a] = bTree;
        A[b] = aTree;
    }
}
