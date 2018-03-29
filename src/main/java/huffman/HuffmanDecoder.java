package main.java.huffman;

import main.java.io.FileInput;
import main.java.io.FileOutput;

/**
 * Implements decompressing a huffman coded file.
 */
public class HuffmanDecoder {

    /**
     * Creates the Huffman tree and writes the decompressed file. First the tree
     * structire is read from the beginning of the file. Then the values for the
     * tree leaves are read and updated to the tree. Finally the file is read
     * again and decompressed and written out according to the tree.
     *
     * @param sourcePath path of the compressed file
     * @param destinationPath path of the file to be decompressed
     */
    public void decompress(String sourcePath, String destinationPath) {
        FileInput in = new FileInput(sourcePath);
        HuffmanTree root = readHuffmanTreeStructure(in);

        readTreeLeaves(in, root);

        FileOutput out = new FileOutput(destinationPath);
        writeDecompressedContent(in, out, root);
        out.close();
    }

    /**
     * Reads the tree's structure. The byte containing the final part of the
     * structure is possibly not a full byte padded with zeros at the end, so
     * the FileInput is advanced to the beginning of the next full byte.
     *
     * @param in reads the file
     * @return the root of the HuffmanTree that contains the structure with
     * empty leaves.
     */
    private HuffmanTree readHuffmanTreeStructure(FileInput in) {
        HuffmanTree root = readTreeStructure(in);
        in.advanceToNextByte();
        return root;
    }

    /**
     * Traverses the tree in pre-order and creates the tree with empty leaves. A
     * leaf is marked with 0 and an internal node with 1.
     *
     * @param in reads the file
     * @return HuffmanTree with empty leaves
     */
    private HuffmanTree readTreeStructure(FileInput in) {
        int nextBit = in.readBit();
        if (nextBit == 0) {
            HuffmanLeaf leaf = new HuffmanLeaf();
            return leaf;
        } else {
            HuffmanInternalNode node = new HuffmanInternalNode();
            node.left = readTreeStructure(in);
            node.right = readTreeStructure(in);
            return node;
        }
    }

    /**
     * Traverses the tree in pre-order and updates the leaf values with
     * corresponding bytes read from the file.
     *
     * @param in reads the file
     * @param tree HuffmanTree with empty leaves, but correct structure
     */
    private void readTreeLeaves(FileInput in, HuffmanTree tree) {
        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            leaf.value = in.readByte();
        } else {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readTreeLeaves(in, node.left);
            readTreeLeaves(in, node.right);
        }
    }

    /**
     * Reads the compressed content from the source file and writes the
     * corresonding decompressed content into the destination file.
     *
     * @param in
     * @param out
     * @param root
     */
    private void writeDecompressedContent(FileInput in, FileOutput out, HuffmanTree root) {
        int readByte = readNextCharacterByte(in, root, in.readBit());

        while (readByte != -1) {
            out.writeByte(readByte);
            readByte = readNextCharacterByte(in, root, in.readBit());
        }
    }

    /**
     * Traverses the tree according to the Huffman coding. 0 means traversing
     * left and 1 traversing right. When a leaf is found, its value is returned.
     *
     * @param in reads the file
     * @param tree Huffman tree to traverse
     * @param readBit tells which direction to traverse to next, unless a leaf is found
     * @return
     */
    private int readNextCharacterByte(FileInput in, HuffmanTree tree, int readBit) {
        if (readBit == -1) {
            return readBit;
        }

        int readByte = -1;

        if (tree instanceof HuffmanLeaf) {
            HuffmanLeaf leaf = (HuffmanLeaf) tree;
            readByte = leaf.value;
        } else if (readBit == 0) {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readByte = readNextCharacterByte(in, node.left, readBit);
        } else {
            HuffmanInternalNode node = (HuffmanInternalNode) tree;
            readByte = readNextCharacterByte(in, node.right, in.readBit());
        }

        return readByte;
    }
}
