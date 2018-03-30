package main.java.pakkaaja;

import main.java.huffman.HuffmanCoder;
import main.java.huffman.HuffmanDecoder;
import main.java.io.FileInput;
import main.java.io.FileOutput;

/**
 * Main class.
 */
public class Main {

    /**
     * Size of the array used to store character frequencies, or how many unique
     * characters there are. In this case it is the different values of one
     * byte.
     */
    public static final int ALPHABET_SIZE = 256;

    /**
     * Starts the program. Used for testing.
     */
    public static void main(String[] args) {
        System.out.println(start(args));
    }

    /**
     * Main program for compressing. A character is assumed to be one byte long.
     * In the case it is not, the program will still work, just not as
     * efficiently space saving wise.
     *
     * The command argument is either hc for huffman compress or hd for huffman
     * decompress.
     *
     * @param args the command line arguments are: command, source, destination.
     */
    public static String start(String[] args) {
        // TODO: agruments for compression/decompression
        if (args.length != 3) {
            return usageInstructions();
        }

        String command = args[0];
        String sourcePath = args[1];
        String destinationPath = args[2];

        // Pre-coded content:
//        FileInput in3 = new FileInput(sourcePath);
//        int readByte3 = in3.readByte();
//        while (readByte3 != -1) {
//            System.out.println(String.format("%8s", Integer.toBinaryString(readByte3 & 0xFF)).replace(' ', '0'));
//            readByte3 = in3.readByte();
//        }
//        in3.close();

        switch (command) {
            case "hc":
                huffmanCompress(sourcePath, destinationPath);
                return "Compressed using Huffman coding.";
            case "hd":
                huffmanDecompress(sourcePath, destinationPath);
                return "Decompressed using Huffman coding.";
            default:
                return usageInstructions();
        }

        // View some of the destination content
//        System.out.println("\n\nDestination content:");
//        FileInput in2 = new FileInput(destinationPath);
//        int readByte2 = in2.readByte();
//        for (int i = 0; i < 30 && readByte2 != -1; i++) {
//            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
//            readByte2 = in2.readByte();
//        }
//        in2.close();
    }

    /**
     * Reads the file and generates the character frequencies into a list that
     * is passed on to the coder that will huffman compress the file.
     *
     * @param sourcePath
     * @param destinationPath
     */
    public static void huffmanCompress(String sourcePath, String destinationPath) {
        int[] byteFrequencies = new int[ALPHABET_SIZE];

        FileInput freqInput = new FileInput(sourcePath);
        int readByte = freqInput.readByte();

        while (readByte != -1) {
            byteFrequencies[readByte]++; // Character as index, frequency count as value.
            readByte = freqInput.readByte();
        }
        freqInput.close();

        // TODO:
        // Currently the last byte has extra characters that are read, resulting in extra letters at the end.
        // Fix by inserting an EOF-character.
        HuffmanCoder coder = new HuffmanCoder(byteFrequencies, ALPHABET_SIZE);
        FileInput coderInput = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        coder.compress(coderInput, out);
    }

    /**
     * Initiates the huffman decompressing.
     *
     * @param sourcePath
     * @param destinationPath
     */
    public static void huffmanDecompress(String sourcePath, String destinationPath) {
        HuffmanDecoder decoder = new HuffmanDecoder();
        FileInput in = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        decoder.decompress(in, out);
    }

    /**
     * Returns usage instructions.
     */
    public static String usageInstructions() {
        return "Usage: [hc|hd] [source] [destination]\n"
                + "hc = Huffman compress\n"
                + "hd = Huffman decompress\n";
    }
}
