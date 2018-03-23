package main.java.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements OutStream interface and uses OutputStream to write bytes.
 */
public class OutputByteStream implements OutStream {

    /**
     * Used for writing bytes into the destination file.
     */
    private OutputStream out;

    /**
     * Initializes OutputStream.
     * @param path path for the destination file.
     */
    public OutputByteStream(String path) {
        try {
            out = new BufferedOutputStream(new FileOutputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            // File will be created if not found, no need for FileNotFoundException.
        }
    }

    @Override
    public void write(int b) {
        try {
            out.write(b);
        } catch (IOException ex) {
            System.out.println("Could not write to file: " + ex);
        }
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException ex) {
            System.out.println("Could not close OutputStream: " + ex);
        }
    }

}
