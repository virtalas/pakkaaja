package main.java.pakkaaja;

import huffman.HuffmanCoder;
import main.java.io.FileInput;
import java.io.IOException;

/**
 * Main class.
 */
public class Main {

    /**
     * Size of the array used to store character frequencies, or how many unique characters there are.
     */
    public static final int ALPHABET_SIZE = 256;

    /**
     * Main program for compressing.
     * A character is assumed to be one byte long. In the case it is not, the program will still work, just not as efficiently space saving wise.
     * It reads the file and generates the character frequencies into a list that is passed on to the coder that will compress the file.
     * @param args the command line arguments are: source, destination.
     */
    public static void main(String[] args) throws IOException {
        // TODO: agruments for compression/decompression
        if (args.length != 2) {
            System.out.println("Usage: [source] [destination]");
            return;
        }

        String sourcePath = args[0];
        String destinationPath = args[1];
        int[] byteFrequencies = new int[ALPHABET_SIZE];

        FileInput in = new FileInput(sourcePath);
        int readByte = in.readNext();

        while (readByte != -1) {
            byteFrequencies[readByte]++; // Character as index, frequency count as value.
            readByte = in.readNext();
        }

        HuffmanCoder coder = new HuffmanCoder(byteFrequencies, ALPHABET_SIZE);
        coder.compress(sourcePath, destinationPath);

        // View some of the compressed content
        FileInput in2 = new FileInput(destinationPath);
        int readByte2 = in2.readNext();
        for (int i = 0; i < 30; i++) {
            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
            readByte2 = in2.readNext();
        }
    }
}
