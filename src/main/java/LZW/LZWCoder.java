package LZW;

import coder.Coder;
import java.util.HashMap;
import io.FileInput;
import io.FileOutput;
import pakkaajaMain.MathUtils;

public class LZWCoder implements Coder {

    /**
     * The dictionary entries have a character and its value.
     */
    private HashMap<String, Integer> dictionary;

    private final int ALPHABET_SIZE;

    /**
     * Specifies the maximum dictionary size, in other words the largest value
     * one sequence of bits can have. Calculated as two to the power of (number of bits read at one time).
     */
    private final int MAX_TABLE_SIZE;

    public LZWCoder(int alphabetSize, int codeLength) {
        ALPHABET_SIZE = alphabetSize;
        MAX_TABLE_SIZE = MathUtils.twoToPower(codeLength);
    }

    @Override
    public void compress(FileInput in, FileOutput out) {
        initDictionary();
        compressFileByBuildingDictionary(in, out);
        in.close();
        out.close();
    }

    /**
     * Initializes the dictionary by inputting every character code from 0 up to
     * ALPHABET_SIZE.
     */
    public void initDictionary() {
        dictionary = new HashMap<>();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            dictionary.put("" + (char) i, i);
        }
    }

    /**
     * Reads characters from the source file until 'character + k' is not in the
     * dictionary. Then the code according to the dictionary is written to the
     * destination file.
     */
    private void compressFileByBuildingDictionary(FileInput in, FileOutput out) {
        StringBuffer w = new StringBuffer();
        int tableSize = ALPHABET_SIZE;

        while (true) {
            int readByte = in.readByte();
            char k = (char) readByte;

            if (dictionary.containsKey(w.toString() + k)) {
                w.append(k);
            } else {
                // Output dictionary code for w
                out.writeNumberOfBits(MAX_TABLE_SIZE, dictionary.get(w.toString()));
                w.append(k);
                if (tableSize < MAX_TABLE_SIZE) {
                    dictionary.put(w.toString(), tableSize++);
                }
                w.setLength(0);
                w.append(k);
            }

            if (readByte == -1) {
                break;
            }
        }
    }
}
