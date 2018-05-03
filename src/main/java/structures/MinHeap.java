package structures;

import huffman.HuffmanTree;

/**
 * Implemetation of a minumum heap.
 */
public class MinHeap {

    /**
     * The values are stored in a list.
     */
    public HuffmanTree A[];
    
    /**
     * Determines where the last value / the end of the heap is in the list.
     */
    private int heapSize;

    public MinHeap(int alphabetSize) {
        heapSize = -1;
        A = new HuffmanTree[alphabetSize];
    }

    public int size() {
        return heapSize + 1;
    }

    /**
     * Get the minimum.
     * @return the tree with the lowest frequency
     */
    public HuffmanTree min() {
        return A[0];
    }

    /**
     * Get and delete the minimum.
     * @return the deleted tree with the lowest frequency.
     */
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

    /**
     * The parent of the node.
     * @param i index of the element to inspect
     * @return index of the parent
     */
    public int parent(int i) {
        return (i+1) / 2 - 1;
    }

    /**
     * The left child of the node.
     * @param i index of the element to inspect
     * @return index of the left child
     */
    public int left(int i) {
        return 2 * (i+1) - 1;
    }
    
    /**
     * The right child of the node.
     * @param i index of the element to inspect
     * @return index of the right child
     */
    public int right(int i) {
        return 2 * (i+1);
    }

    /**
     * Move the element to a correct position in the heap.
     * @param i index of the element to move
     */
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
