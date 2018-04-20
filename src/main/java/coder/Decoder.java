
package coder;

import main.java.io.FileInput;
import main.java.io.FileOutput;

public interface Decoder {
    public void decompress(FileInput in, FileOutput out);
}
