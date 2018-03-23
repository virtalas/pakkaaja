package main.java.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOutput {

    private OutputStream os;
    private int bitCount;
    private int currentByte;

    public FileOutput(String path) {
        try {
            os = new BufferedOutputStream(new FileOutputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            // File will be created if not found, no need for FileNotFoundException.
        }
        bitCount = 0;
        currentByte = 0;
    }

    public void writeBits(String bitsToWrite) {
        for (int i = 0; i < bitsToWrite.length(); i++) {
            char c = bitsToWrite.charAt(i);
            // Convert char to integer
            writeBit(c - '0');
        }
    }

    private void writeBit(int bitToWrite) {
        try {
            if (bitToWrite != 0 && bitToWrite != 1) {
                throw new IllegalArgumentException("Argument must be 0 or 1");
            }
            currentByte = (currentByte << 1) | bitToWrite;
            bitCount++;
            if (bitCount == 8) {
                os.write(currentByte);
                currentByte = 0;
                bitCount = 0;
            }
        } catch (IOException ex) {
            System.out.println("Could not write to file.");
        }
    }

    public void close() {
        try {
            while (bitCount != 0) {
                writeBit(0);
            }
            os.close();
        } catch (IOException ex) {
            System.out.println("Could not close OutputStream.");
        }
    }
}
