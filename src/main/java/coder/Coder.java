
package coder;

import io.FileInput;
import io.FileOutput;

public interface Coder {
    public void compress(FileInput in, FileOutput out);
}
