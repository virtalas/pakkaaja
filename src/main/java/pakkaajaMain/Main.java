package pakkaajaMain;

import huffman.HuffmanCoder;
import huffman.HuffmanDecoder;
import io.FileInput;
import io.FileOutput;
import LZW.LZWCoder;
import LZW.LZWDecoder;

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
     * (LZW) Specifies how many bits are read at one time as one value.
     */
    public static final int CODE_LENGTH = 12;

    /**
     * Starts the program. Used for testing.
     */
    public static void main(String[] args) {
        System.out.println(start(args));

        // View some of the destination content
//        System.out.println("\n\nDestination content:");
//        FileInput in2 = new FileInput(args[2]);
//        int readByte2 = in2.readByte();
//        for (int i = 0; i < 40 && readByte2 != -1; i++) {
//            System.out.println(String.format("%8s", Integer.toBinaryString(readByte2 & 0xFF)).replace(' ', '0'));
//            readByte2 = in2.readByte();
//        }
//        in2.close();
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
        String command = null;
        String sourcePath = null;
        String destinationPath = null;

        switch (args.length) {
            case 1:
                command = args[0];
                break;
            case 3:
                command = args[0];
                sourcePath = args[1];
                destinationPath = args[2];
                break;
            default:
                return usageInstructions();
        }

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
            case "hlc":
                lzwHuffmanCompress(sourcePath, destinationPath);
                return "Compressed using Lempel-Ziv-Welch and Huffman coding.";
            case "hld":
                lzwHuffmanDecompress(sourcePath, destinationPath);
                return "Decompressed using Lempel-Ziv-Welch and Huffman coding.";
            case "test":
                Benchmark.runBenchmarkTests();
                return "";
            default:
                return usageInstructions();
        }
    }

    public static void huffmanCompress(String sourcePath, String destinationPath) {
        HuffmanCoder coder = new HuffmanCoder(ALPHABET_SIZE);
        FileInput coderInput = new FileInput(sourcePath);
        FileOutput out = new FileOutput(destinationPath);
        coder.compress(coderInput, out);
    }

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

    public static void lzwHuffmanCompress(String sourcePath, String destinationPath) {
        String tempFilePath = destinationPath + "_temp";
        lempelZivWelchCompress(sourcePath, tempFilePath);
        huffmanCompress(tempFilePath, destinationPath);
    }

    public static void lzwHuffmanDecompress(String sourcePath, String destinationPath) {
        String tempFilePath = destinationPath + "_temp";
        huffmanDecompress(sourcePath, tempFilePath);
        lempelZivWelchDecompress(tempFilePath, destinationPath);
    }

    /**
     * Returns usage instructions.
     */
    public static String usageInstructions() {
        return "Usage: [hc|hd|lc|ld|lhc|lhd] [source] [destination]\n"
                + "Compress or decompress [source] into [destination] using:\n"
                + "hc = Huffman compress\n"
                + "hd = Huffman decompress\n"
                + "lc = Lempel-Ziv-Welch compress\n"
                + "ld = Lempel-Ziv-Welch decompress\n"
                + "lhc = Lempel-Ziv-Welch and Huffman compress\n"
                + "lhd = Lempel-Ziv-Welch and Huffman decompress\n";
    }
}
