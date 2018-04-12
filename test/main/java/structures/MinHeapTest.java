
package main.java.structures;

import main.java.huffman.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MinHeapTest {
    
    public MinHeapTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetHeapSize() {
        MinHeap heap = new MinHeap(256);
        assertEquals(0, heap.getHeapSize());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        heap.insert(new HuffmanInternalNode());
        assertEquals(4, heap.getHeapSize());
    }

    @Test
    public void testMin() {
    }

    @Test
    public void testDelMin() {
    }

    @Test
    public void testInsert() {
    }
    
}
