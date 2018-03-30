package main.java.huffman;

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

public class HuffmanDecoderTest {

    private MockOutStream out;
    private HuffmanDecoder decoder;

    public HuffmanDecoderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        out = new MockOutStream();
        decoder = new HuffmanDecoder();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDecompress() {
        MockInStream in = new MockInStream(simpleCompressedContent(), false);
        decoder.decompress(new FileInput(in), new FileOutput(out));

        // Currently the last byte has extra characters that are read, resulting in extra letters at the end.
        // Fix by inserting an EOF-character.
        assertEquals("[01110100, 01100101, 01110011, 01110100, 01110100]", out.output.toString()); // = "testt"
    }

    @Test
    public void testReadHuffmanTreeStructureIncludingRestOfFile() {
        String structureIncludingRestOfFile = simpleCompressedContent();

        MockInStream inIncludingRestOfFile = new MockInStream(structureIncludingRestOfFile, false);
        HuffmanTree tree1 = decoder.readHuffmanTree(new FileInput(inIncludingRestOfFile));
        assertNotNull(tree1);
        
        HuffmanInternalNode root1 = (HuffmanInternalNode) tree1;
        HuffmanLeaf leaf11 = (HuffmanLeaf) root1.left;
        assertNotNull(leaf11);
        
        HuffmanInternalNode node2 = (HuffmanInternalNode) root1.right;
        assertNotNull(node2);
        
        HuffmanLeaf leaf2 = (HuffmanLeaf) node2.right;
        assertNotNull(leaf2);
        
        HuffmanLeaf leaf3 = (HuffmanLeaf) node2.left;
        assertNotNull(leaf3);
    }
    
    @Test
    public void testReadHuffmanTreeStructureSymmetricalTree() {
        String structure2 = "11001000"; // symmetric three-layer tree: "1100100" + padding "0"
        
        MockInStream in = new MockInStream(structure2, false);
        HuffmanTree tree = decoder.readHuffmanTree(new FileInput(in));
        assertNotNull(tree);
        
        HuffmanInternalNode root = (HuffmanInternalNode) tree;
        HuffmanInternalNode nodeLeft = (HuffmanInternalNode) root.left;
        assertNotNull(nodeLeft);
        
        HuffmanInternalNode nodeRight = (HuffmanInternalNode) root.right;
        assertNotNull(nodeRight);
        
        HuffmanLeaf leaf1 = (HuffmanLeaf) nodeLeft.left;
        assertNotNull(leaf1);
        
        HuffmanLeaf leaf2 = (HuffmanLeaf) nodeLeft.right;
        assertNotNull(leaf2);
        
        HuffmanLeaf leaf3 = (HuffmanLeaf) nodeRight.left;
        assertNotNull(leaf3);
        
        HuffmanLeaf leaf4 = (HuffmanLeaf) nodeRight.right;
        assertNotNull(leaf4);
    }
    
    @Test
    public void testReadHuffmanTreeStructureWithPaddingAtEnd() {
        String structure3 = "10000000"; // symmetric two-layer tree: "100" + padding "00000"
        
        MockInStream in = new MockInStream(structure3, false);
        HuffmanTree tree = decoder.readHuffmanTree(new FileInput(in));
        assertNotNull(tree);
        
        HuffmanInternalNode root = (HuffmanInternalNode) tree;
        
        HuffmanLeaf leaf1 = (HuffmanLeaf) root.left;
        assertNotNull(leaf1);
        
        HuffmanLeaf leaf2 = (HuffmanLeaf) root.right;
        assertNotNull(leaf2);
    }
    
    @Test
    public void testReadTreeLeavesSimple() {
        MockInStream in = new MockInStream(simpleCompressedContent(), false);
        HuffmanTree tree = decoder.readHuffmanTree(new FileInput(in));
        
        HuffmanInternalNode root = (HuffmanInternalNode) tree;
        HuffmanLeaf leaf1 = (HuffmanLeaf) root.left;
        assertEquals(116, leaf1.value);
        
        HuffmanInternalNode node2 = (HuffmanInternalNode) root.right;      
        
        HuffmanLeaf leaf3 = (HuffmanLeaf) node2.left;
        assertEquals(101, leaf3.value);
        
        HuffmanLeaf leaf2 = (HuffmanLeaf) node2.right;
        assertEquals(115, leaf2.value);
    }
    
    @Test
    public void testReadNextCharacterByteSimple() {
        MockInStream in = new MockInStream(simpleCompressedContent(), false);
        FileInput input = new FileInput(in);
        HuffmanTree tree = decoder.readHuffmanTree(input);
        
        assertEquals(116, decoder.readNextCharacterByte(input, tree, input.readBit()));
        assertEquals(101, decoder.readNextCharacterByte(input, tree, input.readBit()));
        assertEquals(115, decoder.readNextCharacterByte(input, tree, input.readBit()));
        assertEquals(116, decoder.readNextCharacterByte(input, tree, input.readBit()));
    }

    private String simpleCompressedContent() {
        return "10100000"
                + "01110100"
                + "01100101"
                + "01110011"
                + "01011000";
    }
}
