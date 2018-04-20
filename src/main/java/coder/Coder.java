
package coder;

import main.java.io.FileInput;
import main.java.io.FileOutput;

public interface Coder {
    public void compress(FileInput in, FileOutput out);
}
