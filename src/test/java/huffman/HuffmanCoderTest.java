package huffman;

import huffman.HuffmanLeaf;
import huffman.HuffmanTree;
import huffman.HuffmanCoder;
import huffman.HuffmanInternalNode;
import io.FileInput;
import io.FileOutput;
import io.MockInStream;
import io.MockOutStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCoderTest {

    private final int[] bFreq = new int[]{1, 5, 10, 15, 1};

    private MockOutStream out;

    @Before
    public void setUp() {
        out = new MockOutStream();
    }

    /*
    Huffman tree shoud look like (index 0 gets frequency of 1):
    
           32
          / \
         15  17
        '3' / \
           7   10
          / \  '2'
         2   5
        / \ '1'
       1   1
      '0' '4'
      EOF
    
     */
    @Test
    public void testBuildTree() {
        HuffmanCoder coder = new HuffmanCoder(bFreq, 256);
        HuffmanTree root = coder.buildTree();

        HuffmanInternalNode node31 = (HuffmanInternalNode) root;
        HuffmanTree leaf15 = node31.left;
        assertEquals(15, leaf15.frequency);

        HuffmanTree node16 = node31.right;
        assertEquals(17, node16.frequency);
    }

    @Test
    public void testBuildCodeList() {
        HuffmanCoder coder = new HuffmanCoder(bFreq, 256);
        HuffmanTree root = coder.buildTree();
        coder.buildCodeList(root, new StringBuffer());

        assertEquals("1000", coder.codes[0]);
        assertEquals("101", coder.codes[1]);
        assertEquals("11", coder.codes[2]);
        assertEquals("0", coder.codes[3]);
        assertEquals("1001", coder.codes[4]);
    }

    @Test
    public void testCompress() {
        MockInStream in = new MockInStream("test");
    }

    /*
    Simple test
    file="test"
     */
    @Test
    public void testWriteTreeSimple() {
        HuffmanTree root = createSimpleTree();

        int[] freq = new int[256];
        freq[101] = 1;
        freq[115] = 1;
        freq[116] = 2;
        HuffmanCoder coder = new HuffmanCoder(freq, 256);
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
        coder.compress(new FileInput(in), new FileOutput(out));

        assertEquals("01011101", out.output.get(5)); // compressed content "01011101" + "10" + padding("000000")
        assertEquals("10000000", out.output.get(6));
    }

    @Test
    public void testWriteTreeLeavesAscii() {
        HuffmanLeaf leaf1 = new HuffmanLeaf(2, 116); // "t"
        HuffmanLeaf leaf2 = new HuffmanLeaf(1, 33); // "!"
        HuffmanLeaf leaf3 = new HuffmanLeaf(1, 84); // "T"

        HuffmanInternalNode node1 = new HuffmanInternalNode(leaf2, leaf3);
        HuffmanInternalNode root = new HuffmanInternalNode(leaf1, node1);

        HuffmanCoder coder = new HuffmanCoder(null, 256);
        coder.writeTreeLeaves(new FileOutput(out), root);

        assertEquals("01110100", out.output.get(0));
        assertEquals("00100001", out.output.get(1));
        assertEquals("01010100", out.output.get(2));
    }

    @Test
    public void testWriteTreeLeavesNegativeCharacterNotWritten() {
        HuffmanInternalNode root = null;

        try {
            HuffmanLeaf leaf1 = new HuffmanLeaf(2, 32); // "(space)" ok
            HuffmanLeaf leaf2 = new HuffmanLeaf(1, -116);
            HuffmanLeaf leaf3 = new HuffmanLeaf(1, 32);

            HuffmanInternalNode node1 = new HuffmanInternalNode(leaf2, leaf3);
            root = new HuffmanInternalNode(leaf1, node1);

            HuffmanCoder coder = new HuffmanCoder(null, 256);
            coder.writeTreeLeaves(new FileOutput(out), root);
        } catch (Exception e) {
        }

        assertEquals(0, out.output.size());
    }
    
    @Test
    public void testWriteTreeLeavesTooLargeCharacterNotWritten() {
        HuffmanInternalNode root = null;

        try {
            HuffmanLeaf leaf1 = new HuffmanLeaf(2, 24179); // "平"
            HuffmanLeaf leaf2 = new HuffmanLeaf(1, 32);
            HuffmanLeaf leaf3 = new HuffmanLeaf(1, 32);

            HuffmanInternalNode node1 = new HuffmanInternalNode(leaf2, leaf3);
            root = new HuffmanInternalNode(leaf1, node1);

            HuffmanCoder coder = new HuffmanCoder(null, 256);
            coder.writeTreeLeaves(new FileOutput(out), root);
        } catch (Exception e) {
        }

        assertEquals(0, out.output.size());
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
