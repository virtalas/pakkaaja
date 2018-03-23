
package main.java.huffman;

/**
 * A leaf in the Huffman tree that has a value and its frequency.
 */
class HuffmanLeaf extends HuffmanTree {
    
    /**
     * The value is the character/byte as an integer.
     */
    public final int value;
 
    /**
     * Sets the value and its frequency.
     * @param frequency
     * @param value 
     */
    public HuffmanLeaf(int frequency, int value) {
        super(frequency);
        this.value = value;
    }
}
