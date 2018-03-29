package main.java.huffman;

import main.java.huffman.HuffmanLeaf;
import main.java.huffman.HuffmanTree;
import main.java.huffman.HuffmanCoder;
import main.java.huffman.HuffmanInternalNode;
import main.java.io.FileInput;
import main.java.io.FileOutput;
import main.java.io.MockInStream;
import main.java.io.MockOutStream;
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

    @Test
    public void testCompress() {
        MockInStream in = new MockInStream("test");
    }

    @Test
    public void testWriteTreeSimple() {
        HuffmanTree root = createSimpleTree();

        int[] freq = new int[256];
        freq[101] = 1;
        freq[115] = 1;
        freq[116] = 2;
        HuffmanCoder coder = new HuffmanCoder(freq, 256);
        MockOutStream out = new MockOutStream();
        coder.writeTree(new FileOutput(out), root);
                
        assertEquals("10100000", out.output.get(0)); // tree structure "10100" + padding
        assertEquals("01110100", out.output.get(1)); // leaf1 = 116 = t
        assertEquals("01100101", out.output.get(2)); // leaf2 = 101 = e
        assertEquals("01110011", out.output.get(3)); // leaf3 = 115 = s
    }
    
    @Test
    public void testWriteCompressedContent() {
        HuffmanTree root = createSimpleTree();

        int[] freq = new int[256];
        freq[101] = 1;
        freq[115] = 1;
        freq[116] = 2;
        HuffmanCoder coder = new HuffmanCoder(freq, 256);
        MockInStream in = new MockInStream("test");
        MockOutStream out = new MockOutStream();
        coder.compress(new FileInput(in), new FileOutput(out));
        
        assertEquals("01011000", out.output.get(4)); // compressed content "010110"
    }
    
    private HuffmanTree createSimpleTree() {
        HuffmanLeaf leaf1 = new HuffmanLeaf(2, 116);
        HuffmanLeaf leaf2 = new HuffmanLeaf(1, 101);
        HuffmanLeaf leaf3 = new HuffmanLeaf(1, 115);

        HuffmanInternalNode node1 = new HuffmanInternalNode(leaf2, leaf3);
        HuffmanInternalNode root = new HuffmanInternalNode(leaf1, node1);
        return root;
    }
}
