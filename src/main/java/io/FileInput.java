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
     * Initializes the InStream
     *
     * @param path path for the source file
     */
    public FileInput(InStream in) {
        this.in = in;
    }

    /**
     * Reads the next byte.
     *
     * @return the next byte or -1 if the file has ended or could not be read
     */
    public int readNext() {
        return in.read();
    }
}
