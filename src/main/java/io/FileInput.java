
package main.java.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileInput {
    
    InputStream is;
    String path;

    public FileInput(String path) throws FileNotFoundException {
        is = new BufferedInputStream(new FileInputStream(new File(path)));
        path = path;
    }
    
    public int readNext() {
        try {
            return is.read();
        } catch (IOException ex) {
            System.out.println("Could not read file.");
            return -1;
        }
    }
}
