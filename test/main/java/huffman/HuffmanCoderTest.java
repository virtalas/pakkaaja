
package main.java.huffman;

import main.java.huffman.HuffmanLeaf;
import main.java.huffman.HuffmanTree;
import main.java.huffman.HuffmanCoder;
import main.java.huffman.HuffmanInternalNode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCoderTest {
    
    private final int[] bFreq = new int[]{5, 10, 15, 1};
    
    public HuffmanCoderTest() {
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

    /*
    Huffman tree shoud look like:
    
           31
          / \
         15  16
        '2' / \
           6   10
          / \  '1'
         1   5
        '3' '0'
    
    */
    
    @Test
    public void testBuildTree() {
        HuffmanCoder coder = new HuffmanCoder(bFreq, 256);
        HuffmanTree root = coder.buildTree();
        
        HuffmanInternalNode node31 = (HuffmanInternalNode) root;
        HuffmanTree leaf15 = node31.left;
        assertEquals(15, leaf15.frequency);
        
        HuffmanTree node16 = node31.right;
        assertEquals(16, node16.frequency);
    }
    
    @Test
    public void testBuildCodeList() {
        HuffmanCoder coder = new HuffmanCoder(bFreq, 256);
        HuffmanTree root = coder.buildTree();
        coder.buildCodeList(root, new StringBuffer());
        
        assertEquals("100", coder.codes[3]);
        assertEquals("101", coder.codes[0]);
        assertEquals("11", coder.codes[1]);
        assertEquals("0", coder.codes[2]);
    }
    
}
