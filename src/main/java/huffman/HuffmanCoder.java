package main.java.huffman;

import java.util.PriorityQueue;
import main.java.io.FileInput;
import main.java.io.FileOutput;
import main.java.io.InputByteStream;
import main.java.io.OutputByteStream;

/**
 * Implements the Huffman coding and writes the compressed file.
 */
public class HuffmanCoder {

    /**
     * An integer array where the index represents the character/byte, and the value represents the frequency of the character/byte.
     */
    private final int[] byteFrequencies;
    
    /**
     * A string array where the index represents the character/byte, and the value represents the huffman code of the character/byte.
     */
    public String[] codes;

    /**
     * Initializes byteFrequencies and codes.
     * @param bytes array of character/byte frequencies
     * @param alphabetSize size of the alphabet, or how many unique characters there can be.
     */
    public HuffmanCoder(int[] bytes, int alphabetSize) {
        this.byteFrequencies = bytes;
        this.codes = new String[alphabetSize];
    }

    /**
     * Run the Huffman coding algorithm and write the compressed file
     * @param sourcePath path of the source file
     * @param destinationPath path of the destination file
     */
    public void compress(String sourcePath, String destinationPath) {
        HuffmanTree root = buildTree();
        buildCodeList(root, new StringBuffer());

        // TODO: Save root tree to the beginning of the file.
        
        writeCompressedContent(sourcePath, destinationPath);
    }

    /**
     * Reads the source file and writes the compressed destination file using huffman codes.
     * @param sourcePath path of the source file
     * @param destinationPath path of the destination file
     */
    public void writeCompressedContent(String sourcePath, String destinationPath) {
        FileInput in = new FileInput(new InputByteStream(sourcePath));
        FileOutput out = new FileOutput(new OutputByteStream(destinationPath));

        int readByte = in.readNext();

        while (readByte != -1) {
            out.writeBits(codes[readByte]);
            readByte = in.readNext();
        }
    }

    /**
     * Builds the Huffman tree by combining the least frequent trees until the tree is complete.
     * @return the root of the tree
     */
    public HuffmanTree buildTree() {
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
        // Initially, there are only leaves, one for each non-empty character
        for (int i = 0; i < byteFrequencies.length; i++) {
            if (byteFrequencies[i] > 0) {
                trees.offer(new HuffmanLeaf(byteFrequencies[i], (char) i));
            }
        }

        // Loop until there is only one tree left
        while (trees.size() > 1) {
            // two trees with least frequency
            HuffmanTree a = trees.poll();
            HuffmanTree b = trees.poll();

            // Put into new node and re-insert into queue
            trees.offer(new HuffmanInternalNode(a, b));
        }
        return trees.poll();
    }

    /**
     * Builds an array of characters and their corresponding Huffman codes
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
