
package main.java.huffman;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTreeTest {
    
    public HuffmanTreeTest() {
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
    public void testCanCreateHuffmanLeaf() {
        HuffmanLeaf leaf = new HuffmanLeaf(3, 6);
        assertNotNull(leaf);
        assertEquals(3, leaf.frequency);
        assertEquals(6, leaf.value);
    }
    
    @Test
    public void testCanCreateHuffmanLeafWithNoParameters() {
        HuffmanLeaf leaf = new HuffmanLeaf();
        assertNotNull(leaf);
    }
    
    @Test
    public void testCantCreateHuffmanLeafWithNegativeValue() {
        HuffmanLeaf leaf = null;
        
        try {
            leaf = new HuffmanLeaf(3, -6);
        } catch (Exception e) {
        }
        
        assertNull(leaf);
    }
    
    @Test
    public void testCantCreateHuffmanLeafWithNegativeFrequency() {
        HuffmanLeaf leaf = null;
        
        try {
            leaf = new HuffmanLeaf(-3, 6);
        } catch (Exception e) {
        }
        
        assertNull(leaf);
    }
    
    @Test
    public void testCanCreateHuffmanInternalNode() {
        HuffmanLeaf leaf1 = new HuffmanLeaf(3, 6);
        HuffmanLeaf leaf2 = new HuffmanLeaf(1, 2);
        HuffmanInternalNode node = new HuffmanInternalNode(leaf1, leaf2);
        assertNotNull(node);
        assertEquals(3, node.left.frequency);
        assertEquals(1, node.right.frequency);
    }
    
    @Test
    public void testCanCreateHuffmanInternalNodeWithNoParameters() {
        HuffmanInternalNode node = new HuffmanInternalNode();
        assertNotNull(node);
    }
}
