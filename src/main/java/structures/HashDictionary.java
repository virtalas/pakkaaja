package structures;

/**
 * Implementation of a hashmap structure used by LZWCoder and LZWDecoder.
 * Supports Integers and Strings as keys and values, as recuired by the LZW
 * algorithm.
 */
public class HashDictionary {

    /**
     * Used to store the dictionary entries. Also handles collisions as it works
     * as a linked list.
     */
    private LinkedDictionaryEntry[] lists;

    /**
     * The size of the hashmap, meaning how many lists there are.
     */
    private int numberOfLists;

    /**
     * Initializes the lists as empty.
     *
     * @param initSize how many individual lists there will be
     */
    public HashDictionary(int initSize) {
        numberOfLists = initSize;
        lists = new LinkedDictionaryEntry[initSize];
        for (int i = 0; i < initSize; i++) {
            lists[i] = new LinkedDictionaryEntry();
        }
    }

    /**
     * Finds the list that corresponds with the key, and adds the key/value pair
     * to it.
     * @param key key
     * @param value value
     */
    public void put(Object key, Object value) {
        listFor(key).add(key, value);
    }

    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        return listFor(key).contains(key);
    }

    /**
     * Finds the list that corresponds with the key, and returns the search
     * result from that list.
     * @param key key for the value to get
     * @return the value
     */
    public Object get(Object key) {
        return listFor(key).get(key);
    }

    /**
     * Returns the correct list for a key, according to the hash function and
     * the class of the key.
     */
    private LinkedDictionaryEntry listFor(Object key) {
        LinkedDictionaryEntry list = null;

        if (key instanceof Integer) {
            list = lists[integerHashCode((int) key)];
        } else if (key instanceof String) {
            list = lists[stringHashCode((String) key)];
        }

        return list;
    }

    private int integerHashCode(int x) {
        return Math.abs(x) % numberOfLists;
    }

    private int stringHashCode(String s) {
        return Math.abs(s.hashCode()) % numberOfLists;
    }
}
