package main.java.io;

/**
 * Class for reading bytes from the source file.
 */
public class FileInput {

    /**
     * Used for reading bytes from the source file.
     */
    private InStream in;

    /**
     * The byte that is currently being read bit by bit.
     */
    private int currentByte;

    /**
     * Number of bits that have not yet been read from the current byte.
     */
    private int numBitsRemaining;

    /**
     * Default constructor that initializes an InputByteStream.
     *
     * @param sourcePath path of the file to read
     */
    public FileInput(String sourcePath) {
        this.in = new InputByteStream(sourcePath);
        currentByte = 0;
        numBitsRemaining = 0;
    }

    /**
     * Initializes the InStream.
     *
     * @param path path for the source file
     */
    public FileInput(InStream in) {
        this.in = in;
        currentByte = 0;
        numBitsRemaining = 0;
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte or -1 if the file has ended or could not be read
     */
    public int readByte() {
        if (numBitsRemaining != 0) {
            throw new RuntimeException("Tried to read a byte when a previous byte was not yet fully read.");
        }
        return in.read();
    }

    /**
     * Reads one bit from the current byte. If the end of the byte is reached, a
     * new byte is read into memory.
     *
     * @return
     */
    public int readBit() {
        if (currentByte == -1) {
            return -1;
        }
        if (numBitsRemaining == 0) {
            currentByte = in.read();
            if (currentByte == -1) {
                return -1;
            }
            numBitsRemaining = 8;
        }
        if (numBitsRemaining <= 0) {
            throw new AssertionError();
        }
        numBitsRemaining--;
        return (currentByte >>> numBitsRemaining) & 1;
    }

    /**
     * Advance to the beginning of the next byte, and don't read the rest of the
     * current byte.
     */
    public void advanceToNextByte() {
        while (numBitsRemaining != 0) {
            readBit();
        }
    }

    /**
     * Closes the input.
     */
    public void close() {
        in.close();
        currentByte = -1;
        numBitsRemaining = 0;
    }
}
