package structures;

import main.java.huffman.*;
import main.java.structures.MinHeap;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public class MinHeapTest {

    private MinHeap heap;

    @Before
    public void setUp() {
        heap = new MinHeap(256);
    }

    @Test
    public void testGetHeapSize() {
        assertEquals(0, heap.size());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        assertEquals(4, heap.size());
    }

    @Test
    public void testMin() {
        heap.insert(new HuffmanLeaf(3, 0));
        assertEquals(3, heap.min().frequency);
        heap.insert(new HuffmanLeaf(7, 0));
        assertEquals(3, heap.min().frequency);
        heap.insert(new HuffmanLeaf(2, 0));
        assertEquals(2, heap.min().frequency);
        heap.insert(new HuffmanLeaf(10, 0));
        assertEquals(2, heap.min().frequency);
    }

    @Test
    public void testMinReturnsNullWhenEmpty() {
        assertNull(heap.min());
    }

    @Test
    public void testDelMinReturnsNullWhenEmpty() {
        assertNull(heap.delMin());
    }

    @Test
    public void testDelMin() {
        heap.insert(new HuffmanLeaf(5, 0));
        heap.insert(new HuffmanLeaf(2, 0));
        heap.insert(new HuffmanLeaf(7, 0));
        heap.insert(new HuffmanLeaf(1, 0));
        heap.insert(new HuffmanLeaf(5, 0));

        assertEquals(1, heap.delMin().frequency);
        assertEquals(4, heap.size());
        assertEquals(2, heap.delMin().frequency);
        assertEquals(3, heap.size());
        assertEquals(5, heap.delMin().frequency);
        assertEquals(2, heap.size());
        assertEquals(5, heap.delMin().frequency);
        assertEquals(1, heap.size());
        assertEquals(7, heap.delMin().frequency);
        assertEquals(0, heap.size());
    }

    @Test
    public void testInsert() {
        heap.insert(new HuffmanLeaf(235, 0));
        assertEquals(1, heap.size());
        assertEquals(235, heap.min().frequency);
        heap.insert(new HuffmanLeaf(100, 0));
        assertEquals(2, heap.size());
        assertEquals(100, heap.min().frequency);
        heap.insert(new HuffmanLeaf(77, 0));
        assertEquals(3, heap.size());
        assertEquals(77, heap.min().frequency);
    }

    @Test
    public void testParent() {
        heap.A = exampleTreeMaxStructure();
        assertEquals(8, heap.A[heap.parent(7)].frequency);
        assertEquals(14, heap.A[heap.parent(3)].frequency);
        assertEquals(16, heap.A[heap.parent(1)].frequency);
        assertEquals(10, heap.A[heap.parent(6)].frequency);
        assertEquals(7, heap.A[heap.parent(9)].frequency);
    }

    @Test
    public void testLeft() {
        heap.A = exampleTreeMaxStructure();
        assertEquals(14, heap.A[heap.left(0)].frequency);
        assertEquals(8, heap.A[heap.left(1)].frequency);
        assertEquals(2, heap.A[heap.left(3)].frequency);
        assertEquals(1, heap.A[heap.left(4)].frequency);
        assertEquals(9, heap.A[heap.left(2)].frequency);
    }

    @Test
    public void testRight() {
        heap.A = exampleTreeMaxStructure();
        assertEquals(10, heap.A[heap.right(0)].frequency);
        assertEquals(7, heap.A[heap.right(1)].frequency);
        assertEquals(4, heap.A[heap.right(3)].frequency);
        assertEquals(3, heap.A[heap.right(2)].frequency);
    }
    
    @Test
    public void testSwap() {
        insertExampleTreeMinStructure();
        
        assertEquals(1, heap.A[0].frequency);
        assertEquals(4, heap.A[1].frequency);
        heap.swap(0, 1);
        assertEquals(4, heap.A[0].frequency);
        assertEquals(1, heap.A[1].frequency);
        
        assertEquals(7, heap.A[3].frequency);
        assertEquals(16, heap.A[9].frequency);
        heap.swap(9, 3);
        assertEquals(16, heap.A[3].frequency);
        assertEquals(7, heap.A[9].frequency);
    }

    @Test
    public void testHeapify() {
        insertExampleTreeMinStructure();
        heap.A[3] = new HuffmanLeaf(4, 0);
        heap.A[1] = new HuffmanLeaf(10, 0);
        heap.A[8] = new HuffmanLeaf(7, 0);
        heap.heapify(1);
        
        assertEquals(4, heap.A[1].frequency);
        assertEquals(7, heap.A[3].frequency);
        assertEquals(10, heap.A[8].frequency);
    }

    private HuffmanTree[] exampleTreeMaxStructure() {
        // Only for testing parent/child relations. (structured like a max heap)
        HuffmanLeaf leaf2 = new HuffmanLeaf(2, 0);
        HuffmanLeaf leaf8 = new HuffmanLeaf(8, 0);
        HuffmanLeaf leaf1 = new HuffmanLeaf(1, 0);
        HuffmanLeaf leaf14 = new HuffmanLeaf(14, 0);
        HuffmanLeaf leaf7 = new HuffmanLeaf(7, 0);
        HuffmanLeaf leaf9 = new HuffmanLeaf(9, 0);
        HuffmanLeaf leaf3 = new HuffmanLeaf(3, 0);
        HuffmanLeaf leaf4 = new HuffmanLeaf(4, 0);
        HuffmanLeaf leaf10 = new HuffmanLeaf(10, 0);
        HuffmanLeaf leaf16 = new HuffmanLeaf(16, 0);

        return new HuffmanTree[]{
            leaf16, // root
            leaf14, leaf10, // level 1
            leaf8, leaf7, leaf9, leaf3, // level 2
            leaf2, leaf4, leaf1 // level 3, filled from left
        };
    }

    /*
                1
              /   \
             4      2
           /  \    /  \
         7     8  9     3
        / \   /
      14  10 16

     */
    private void insertExampleTreeMinStructure() {
        heap.insert(new HuffmanLeaf(2, 0));
        heap.insert(new HuffmanLeaf(8, 0));
        heap.insert(new HuffmanLeaf(1, 0));
        heap.insert(new HuffmanLeaf(14, 0));
        heap.insert(new HuffmanLeaf(7, 0));
        heap.insert(new HuffmanLeaf(9, 0));
        heap.insert(new HuffmanLeaf(3, 0));
        heap.insert(new HuffmanLeaf(4, 0));
        heap.insert(new HuffmanLeaf(10, 0));
        heap.insert(new HuffmanLeaf(16, 0));
    }
}
