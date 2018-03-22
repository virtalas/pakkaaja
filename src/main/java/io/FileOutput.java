
package main.java.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOutput {
    
    OutputStream os;
    String path;

    public FileOutput(String path) throws FileNotFoundException {
        os = new BufferedOutputStream(new FileOutputStream(new File(path)));
        path = path;
    }
    
    public void write(int byteToWrite) throws IOException {
        os.write(byteToWrite);
    }
    
    public void close() throws IOException {
        os.close();
    }
}
