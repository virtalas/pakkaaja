
package huffman;

class HuffmanLeaf extends HuffmanTree {
    public final int value;
 
    public HuffmanLeaf(int frequency, int value) {
        super(frequency);
        this.value = value;
    }
}
