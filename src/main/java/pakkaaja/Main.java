package main.java.pakkaaja;

import main.java.huffman.HuffmanCoder;
import main.java.huffman.HuffmanDecoder;
import main.java.io.FileInput;
import main.java.io.FileOutput;
import main.java.LZW.LZWCoder;
import main.java.LZW.LZWDecoder;

/**
 * Main class.
 */
public class Main {

    /**
     * (Huffman coding) Size of the array used to store character frequencies,
     * or how many unique characters there are. In this case it is the different
     * values of one byte.
     */
    public static final int ALPHABET_SIZE = 256;

    /**
     * (LZW) Specifies how many bits are read at
     * one time as one value.
     */
    public static final int CODE_LENGTH = 12;

    /**
     * Starts the program. Used for testing.
     */
    public static void main(String[] args) {
        System.out.println(start(args));

        // View some of the destination content
        System.out.println("\n\nDestination content:");
        FileInput in2 = new FileInput(args[2]);
        int readByte2 = in2.readByte();
        for (int i = 0; i < 40 && readByte2 != -1; i++) {
            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
            readByte2 = in2.readByte();
        }
        in2.close();
    }

    /**
     * Main program for compressing. A character is assumed to be one byte long.
     * In the case it is not, the program will still work, just not as
     * efficiently space saving wise.
     *
     * The command argument is either hc/hd for Huffman compress/decompress, or
     * lc/ld for Lempel-Ziv-Welch compress/decompress.
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
            case "lc":
                lempelZivWelchCompress(sourcePath, destinationPath);
                return "Compressed using Lempel-Ziv-Welch.";
            case "ld":
                lempelZivWelchDecompress(sourcePath, destinationPath);
                return "Decompressed using Lempel-Ziv-Welch.";
            default:
                return usageInstructions();
        }
    }

    /**
     * Reads the file and generates the character frequencies into a list that
     * is passed on to the coder that will huffman compress the file.
     *
     * @param sourcePath
     * @param destinationPath
     */
    public static void huffmanCompress(String sourcePath, String destinationPath) {
        int[] byteFrequencies = byteFrequencies(sourcePath);
        HuffmanCoder coder = new HuffmanCoder(byteFrequencies, ALPHABET_SIZE);
        FileInput coderInput = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        coder.compress(coderInput, out);
    }

    public static int[] byteFrequencies(String sourcePath) {
        int[] byteFrequencies = new int[ALPHABET_SIZE];

        FileInput freqInput = new FileInput(sourcePath);
        int readByte = freqInput.readByte();

        while (readByte != -1) {
            byteFrequencies[readByte]++; // Character as index, frequency count as value.
            readByte = freqInput.readByte();
        }

        freqInput.close();
        return byteFrequencies;
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

    public static void lempelZivWelchCompress(String sourcePath, String destinationPath) {
        FileInput in = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        LZWCoder coder = new LZWCoder(ALPHABET_SIZE, CODE_LENGTH);
        coder.compress(in, out);
    }

    public static void lempelZivWelchDecompress(String sourcePath, String destinationPath) {
        FileInput in = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        LZWDecoder decoder = new LZWDecoder(ALPHABET_SIZE, CODE_LENGTH);
        decoder.decompress(in, out);
    }
    
    public static int twoToPower(int power) {
        int n = 1;
        for (int i = 1; i <= power; i++) {
            n *= 2;
        }
        return n;
    }

    /**
     * Returns usage instructions.
     */
    public static String usageInstructions() {
        return "Usage: [hc|hd|lc|ld] [source] [destination]\n"
                + "hc = Huffman compress\n"
                + "hd = Huffman decompress\n"
                + "lc = Lempel-Ziv-Welch compress\n"
                + "ld = Lempel-Ziv-Welch decompress\n";
    }
}
