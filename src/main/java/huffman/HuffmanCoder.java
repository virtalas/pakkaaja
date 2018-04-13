package main.java.huffman;

import main.java.io.FileInput;
import main.java.io.FileOutput;
import main.java.structures.MinHeap;

/**
 * Implements the Huffman coding and writes the compressed file.
 */
public class HuffmanCoder {

    /**
     * An integer array where the index represents the character/byte, and the
     * value represents the frequency of the character/byte.
     */
    private final int[] byteFrequencies;
    
    private final int alphabetSize;

    /**
     * A string array where the index represents the character/byte, and the
     * value represents the huffman code of the character/byte.
     */
    public String[] codes;

    /**
     * Initializes byteFrequencies and codes.
     *
     * @param bytes array of character/byte frequencies
     * @param alphabetSize size of the alphabet, or how many unique characters
     * there can be.
     */
    public HuffmanCoder(int[] bytes, int alphabetSize) {
        this.byteFrequencies = bytes;
        this.codes = new String[alphabetSize];
        this.alphabetSize = alphabetSize;
    }

    /**
     * Run the Huffman coding algorithm and write the compressed file. Writes
     * the Huffman tree first and then the compressed content.
     */
    public void compress(FileInput in, FileOutput out) {
        initEndOfFileCharacterFrequency();
        HuffmanTree root = buildTree();
        buildCodeList(root, new StringBuffer());

        writeTree(out, root);
        writeCompressedContent(in, out);
    }

    /**
     * 0 = NUL, used as the end of file character. Frequency is once at the end
     * of the file.
     */
    public void initEndOfFileCharacterFrequency() {
        byteFrequencies[0] = 1;
    }

    /**
     * Reads the source file and writes the compressed destination file using
     * huffman codes.
     */
    public void writeCompressedContent(FileInput in, FileOutput out) {
        int readByte = in.readByte();

        while (readByte != -1) {
            out.writeBits(codes[readByte]);
            readByte = in.readByte();
        }

        out.writeBits(codes[0]); // Write the end of file character at the end.
        in.close();
        out.close();
    }

    /**
     * First the structure of the tree is written, ending with the output at the
     * beginning of the next byte. Then the leaf values are written, one value
     * per byte.
     *
     * @param out FileOutput for the bits.
     * @param root Root of the tree to be written.
     */
    public void writeTree(FileOutput out, HuffmanTree root) {
        writeTreeStructure(out, root);
        out.advanceToNextByte();
        writeTreeLeaves(out, root);
    }

    /**
     * Traverses the tree in pre-order and writes 0 for leaf, 1 for internal
     * node.
     *
     * @param out FileOutput for the bits.
     * @param tree subtree to be written and traversed
     */
    public void writeTreeStructure(FileOutput out, HuffmanTree tree) {
        if (tree instanceof HuffmanLeaf) {
            out.writeBit(0);
        } else {
            out.writeBit(1);
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            writeTreeStructure(out, node.left);
            writeTreeStructure(out, node.right);
        }
    }

    /**
     * Traverses the tree in pre-order and writes leaf values, one value/leaf
     * per byte.
     *
     * @param out FileOutput for the bytes.
     * @param tree subtree to be written and traversed
     */
    public void writeTreeLeaves(FileOutput out, HuffmanTree tree) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            out.writeByte(leaf.value);
        } else {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            writeTreeLeaves(out, node.left);
            writeTreeLeaves(out, node.right);
        }
    }

    /**
     * Builds the Huffman tree by combining the least frequent trees until the
     * tree is complete.
     *
     * @return the root of the tree
     */
    public HuffmanTree buildTree() {
        MinHeap trees = new MinHeap(alphabetSize);
        // Initially, there are only leaves, one for each non-empty character
        for (int i = 0; i < byteFrequencies.length; i++) {
            if (byteFrequencies[i] > 0) {
                trees.insert(new HuffmanLeaf(byteFrequencies[i], (char) i));
            }
        }

        // Loop until there is only one tree left
        while (trees.size() > 1) {
            // Two trees with least frequency
            HuffmanTree a = trees.delMin();
            HuffmanTree b = trees.delMin();

            // Put into new node and re-insert into queue
            trees.insert(new HuffmanInternalNode(a, b));
        }
        return trees.min();
    }

    /**
     * Builds an array of characters and their corresponding Huffman codes
     *
     * @param tree complete Huffman tree
     * @param prefix initializes the prefix/code
     */
    public void buildCodeList(HuffmanTree tree, StringBuffer prefix) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;

            System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix);

            codes[leaf.value] = prefix.toString();

        } else if (tree instanceof HuffmanInternalNode) {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;

            // Traverse left
            prefix.append('0');
            buildCodeList(node.left, prefix);
            prefix.deleteCharAt(prefix.length() - 1);

            // Traverse right
            prefix.append('1');
            buildCodeList(node.right, prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
