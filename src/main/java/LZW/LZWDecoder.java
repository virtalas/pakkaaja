package LZW;

import coder.Decoder;
import java.util.HashMap;
import io.FileInput;
import io.FileOutput;
import pakkaajaMain.MathUtils;

public class LZWDecoder implements Decoder {

    /**
     * The dictionary entries have a value and its character.
     */
    private HashMap<Integer, String> dictionary;

    private final int ALPHABET_SIZE;

    /**
     * Specifies the maximum dictionary size, in other words the largest value
     * one sequence of bits can have. Calculated as two to the power of (number
     * of bits read at one time).
     */
    private final int MAX_TABLE_SIZE;

    /**
     * Number of bits read at one time. This number of bits is treated as one
     * 'character' (or code for a number of characters).
     */
    private final int CODE_LENGTH;

    public LZWDecoder(int alphabetSize, int codeLength) {
        ALPHABET_SIZE = alphabetSize;
        CODE_LENGTH = codeLength;
        MAX_TABLE_SIZE = MathUtils.twoToPower(codeLength);
    }

    @Override
    public void decompress(FileInput in, FileOutput out) {
        initDictionary();
        decompressByBuildingDictionary(in, out);
        in.close();
        out.close();
    }

    /**
     * Initializes the dictionary by inputting every character code from 0 up to
     * ALPHABET_SIZE.
     */
    private void initDictionary() {
        dictionary = new HashMap<>();

        for (int i = 0; i < ALPHABET_SIZE; i++) {
            dictionary.put(i, "" + (char) i);
        }
    }

    /**
     * Builds the dictionary the same way the compressor did. Therefore the
     * codes it encounters are by that point known as a character or a code for
     * a combination of characters.
     */
    private void decompressByBuildingDictionary(FileInput in, FileOutput out) {
        int tableSize = ALPHABET_SIZE;
        String previosCharacters = "";

        while (true) {
            int readByte = in.readNumberOfBits(CODE_LENGTH);
            if (readByte == -1) {
                break;
            }

            String charactersToWrite = null;

            if (dictionary.containsKey(readByte)) {
                charactersToWrite = dictionary.get(readByte);
            } else if (readByte == tableSize) {
                charactersToWrite = previosCharacters + previosCharacters.charAt(0);
            }

            out.writeCharacters(charactersToWrite);

            if (tableSize < MAX_TABLE_SIZE && !previosCharacters.isEmpty()) {
                dictionary.put(tableSize++, previosCharacters + charactersToWrite.charAt(0));
            }

            previosCharacters = charactersToWrite;
        }
    }
}
