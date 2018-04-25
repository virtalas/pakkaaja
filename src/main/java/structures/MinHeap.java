package structures;

import huffman.HuffmanTree;

public class MinHeap {

    public HuffmanTree A[];
    private int heapSize;

    public MinHeap(int alphabetSize) {
        heapSize = -1;
        A = new HuffmanTree[alphabetSize];
    }

    public int size() {
        return heapSize + 1;
    }

    public HuffmanTree min() {
        return A[0];
    }

    public HuffmanTree delMin() {
        if (heapSize == -1) {
            return null;
        }
        
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

    public int parent(int i) {
        return (i+1) / 2 - 1;
    }

    public int left(int i) {
        return 2 * (i+1) - 1;
    }

    public int right(int i) {
        return 2 * (i+1);
    }

    public void heapify(int i) {
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

    public void swap(int a, int b) {
        HuffmanTree aTree = A[a];
        HuffmanTree bTree = A[b];
        A[a] = bTree;
        A[b] = aTree;
    }
}
