
package main.java.lempelZivWelch;

import java.util.ArrayList;
import main.java.io.FileInput;
import main.java.io.FileOutput;


public class LempelZivWelchCoder {
    
    private ArrayList<String> dictionary;
    
    public void compress(FileInput in, FileOutput out) {
        StringBuffer w = new StringBuffer();
        
        while (true) {
            int readByte = in.readByte();
            if (readByte == -1) {
                break;
            }
            int k = (char) readByte;
            if (dictionary.contains(w.toString() + k)) {
                w.append(k);
            } else {
                // output code for w
                w.append(k);
                dictionary.add(w.toString());
            }
        }
    }
}
