package main.java.io;

/**
 * Class for writing bytes to the destination file.
 */
public class FileOutput {

    /**
     * Used for writing bytes into the destination file.
     */
    private OutStream out;

    /**
     * Keeps count for how many bits out of a byte are to be written.
     */
    private int bitCount;

    /**
     * Bits that are to be written are added to this byte. It is written when
     * bitCount reaches eight.
     */
    private int currentByte;

    /**
     * Default constroctor that initializes an OutputByteStream.
     *
     * @param destinationPath
     */
    public FileOutput(String destinationPath) {
        out = new OutputByteStream(destinationPath);
        bitCount = 0;
        currentByte = 0;
    }

    /**
     * Initializes OutputStream, bitCount and currentByte.
     *
     * @param output used for writing to the destination file
     */
    public FileOutput(OutStream output) {
        out = output;
        bitCount = 0;
        currentByte = 0;
    }

    /**
     * Write a string of bits to the destination file.
     *
     * @param bitsToWrite a string representation of the bits to be written
     */
    public void writeBits(String bitsToWrite) {
        System.out.println("writeBits: "+bitsToWrite);
        for (int i = 0; i < bitsToWrite.length(); i++) {
            char c = bitsToWrite.charAt(i);
            // Convert char to integer
            writeBit(c - '0');
        }
    }

    /**
     * Method for writing a single bit.
     *
     * @param bitToWrite bit to write as an integer that must be 0 or 1
     */
    public void writeBit(int bitToWrite) {
        if (bitToWrite != 0 && bitToWrite != 1) {
            throw new IllegalArgumentException("bitToWrite must be 0 or 1");
        }

        currentByte = (currentByte << 1) | bitToWrite; // Shift bits to the left by one and place the new bit to the end.
        bitCount++;

        if (bitCount == 8) {
            // Full byte reached, write byte and start again.
            out.write(currentByte);
            currentByte = 0;
            bitCount = 0;
        }
    }

    /**
     * Writes a full byte at once. En error is produced if a previos byte is not
     * completed and written yet.
     *
     * @param byteToWrite byte to be written, must be < 256
     */
    public void writeByte(int byteToWrite) {
        if (byteToWrite > 255) {
            throw new RuntimeException("Tried to write an integer bigger than 255 into one byte.");
        }
        if (bitCount != 0) {
            throw new RuntimeException("Tried to write a byte when the previos byte was not yet completed and written.");
        }
        out.write(byteToWrite);
    }
    
    /**
     * Writes the value with a specific amount of bits with leading zeros.
     *
     * @param length amount of bits to use to write the value
     * @param value value to write
     */
    public void writeNumberOfBits(int length, int value) {
        if (length == 1) {
            writeBit(value);
            return;
        }
        StringBuilder finishedBitSequence = new StringBuilder();
        for (int i = 1; i < length; i = i << 1) {
            int nextBit = (value & i) > 0 ? 1 : 0;
            finishedBitSequence.insert(0, nextBit);
        }
        writeBits(finishedBitSequence.toString());
    }
    
    public void writeCharacters(String value) {
        for (char c : value.toCharArray()) {
            writeByte(c);
        }
    }

    /**
     * If the current byte is not full, write zeros until the beginning of the
     * next byte.
     */
    public void advanceToNextByte() {
        while (bitCount != 0) {
            writeBit(0);
        }
    }

    /**
     * Closes the OutputStream. If the current byte is not full, zeros are
     * written to the end so the final byte can be written.
     */
    public void close() {
        advanceToNextByte();
        out.close();
    }
}
