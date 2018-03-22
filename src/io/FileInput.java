
package io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileInput {
    
    InputStream is;
    String path;

    public FileInput(String path) throws FileNotFoundException {
        is = new BufferedInputStream(new FileInputStream(new File(path)));
        path = path;
    }
    
    public int read() throws IOException {
        return is.read();
    }
}
