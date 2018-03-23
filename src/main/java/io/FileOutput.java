package main.java.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class for writing bytes to the destination file.
 */
public class FileOutput {

    /**
     * Used for writing bytes into the destination file.
     */
    private OutputStream os;
    
    /**
     * Keeps count for how many bits out of a byte are to be written.
     */
    private int bitCount;
    
    /**
     * Bits that are to be written are added to this byte.
     * It is written when bitCount reaches eight.
     */
    private int currentByte;

    /**
     * Initializes OutputStream, bitCount and currentByte.
     * @param path path for the destination file.
     */
    public FileOutput(String path) {
        try {
            os = new BufferedOutputStream(new FileOutputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            // File will be created if not found, no need for FileNotFoundException.
        }
        bitCount = 0;
        currentByte = 0;
    }

    /**
     * Write a string of bits to the destination file.
     * @param bitsToWrite a string representation of the bits to be written
     */
    public void writeBits(String bitsToWrite) {
        for (int i = 0; i < bitsToWrite.length(); i++) {
            char c = bitsToWrite.charAt(i);
            // Convert char to integer
            writeBit(c - '0');
        }
    }

    /**
     * Private method for writing a single bit.
     * @param bitToWrite bit to write as an integer that must be 0 or 1
     */
    private void writeBit(int bitToWrite) {
        try {
            if (bitToWrite != 0 && bitToWrite != 1) {
                throw new IllegalArgumentException("bitToWrite must be 0 or 1");
            }
            
            currentByte = (currentByte << 1) | bitToWrite; // Shift bits to the left by one and place the new bit to the end.
            bitCount++;
            
            if (bitCount == 8) {
                // Full byte reached, write byte and start again.
                os.write(currentByte);
                currentByte = 0;
                bitCount = 0;
            }
        } catch (IOException ex) {
            System.out.println("Could not write to file: " + ex);
        }
    }

    /**
     * Closes the OutputStream.
     * If the current byte is not full, zeros are written to the end so the final byte can be written.
     */
    public void close() {
        try {
            while (bitCount != 0) {
                writeBit(0);
            }
            os.close();
        } catch (IOException ex) {
            System.out.println("Could not close OutputStream: " + ex);
        }
    }
}
