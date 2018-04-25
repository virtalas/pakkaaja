
package coder;

import io.FileInput;
import io.FileOutput;

public interface Decoder {
    public void decompress(FileInput in, FileOutput out);
}
