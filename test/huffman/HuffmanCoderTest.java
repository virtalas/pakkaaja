
package huffman;

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
        16  15
       / \   '2'
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
        HuffmanInternalNode node16 = (HuffmanInternalNode) node31.left;
        HuffmanLeaf leaf15 = (HuffmanLeaf) node31.right;
        HuffmanInternalNode node6 = (HuffmanInternalNode) node16.left;
        HuffmanLeaf leaf10 = (HuffmanLeaf) node16.right;
        HuffmanLeaf leaf1 = (HuffmanLeaf) node6.left;
        HuffmanLeaf leaf5 = (HuffmanLeaf) node6.left;
       
        assertEquals(31, node31.frequency);
        assertEquals(16, node16.frequency);
        assertEquals(6, node6.frequency);
        assertEquals(1, leaf1.frequency);
        assertEquals(5, leaf5.frequency);
        assertEquals(10, leaf10.frequency);
        assertEquals(15, leaf15.frequency);
        
        assertEquals(3, leaf1.value);
        assertEquals(0, leaf5.value);
        assertEquals(1, leaf10.value);
        assertEquals(2, leaf15.value);
    }
    
    @Test
    public void testBuildCodeList() {
        HuffmanCoder coder = new HuffmanCoder(bFreq, 256);
        HuffmanTree root = coder.buildTree();
        coder.buildCodeList(root, new StringBuffer());
        
        assertEquals("000", coder.codes[3]);
        assertEquals("001", coder.codes[0]);
        assertEquals("01", coder.codes[1]);
        assertEquals("1", coder.codes[2]);
    }
}
