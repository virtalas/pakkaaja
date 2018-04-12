package main.java.huffman;

/**
 * A leaf in the Huffman tree that has a value and its frequency.
 */
public class HuffmanLeaf extends HuffmanTree {

    /**
     * The value is the character/byte as an integer.
     */
    public int value;
    
    /**
     * Initializes an empty leaf.
     */
    public HuffmanLeaf() {
        super(0);
        this.value = 0;
    }

    /**
     * Sets the value and its frequency.
     *
     * @param frequency
     * @param value
     */
    public HuffmanLeaf(int frequency, int value) {
        super(frequency);
        if (value < 0) {
            throw new RuntimeException("Leaf value has to be above zero to represent a character code.");
        }
        this.value = value;
    }
}
