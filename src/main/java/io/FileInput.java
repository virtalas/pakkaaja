
package main.java.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for reading bytes from the source file.
 */
public class FileInput {
    
    /**
     * Used for reading bytes from the source file.
     */
    private InputStream is;

    /**
     * Initializes the InputStream
     * @param path path for the source file
     */
    public FileInput(String path) {
        try {
            is = new BufferedInputStream(new FileInputStream(new File(path)));
        } catch (FileNotFoundException ex) {
            System.out.println("File was not found: " + ex);
        }
    }
    
    /**
     * Reads the next byte.
     * @return the next byte or -1 if the file has ended or could not be read
     */
    public int readNext() {
        try {
            return is.read();
        } catch (IOException ex) {
            System.out.println("Could not read file: " + ex);
            return -1;
        }
    }
}
