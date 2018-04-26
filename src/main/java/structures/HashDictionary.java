package structures;

public class HashDictionary {

    private LinkedDictionaryEntry[] lists;

    private int listSize;

    public HashDictionary(int initSize) {
        listSize = initSize;
        lists = new LinkedDictionaryEntry[initSize];
        for (int i = 0; i < initSize; i++) {
            lists[i] = new LinkedDictionaryEntry();
        }
    }

    public void put(Object key, Object value) {
        listFor(key).add(key, value);
    }

    public boolean containsKey(Object key) {
        if (key == null) {
            return false;
        }
        return listFor(key).contains(key);
    }
    
    public Object get(Object key) {
        return listFor(key).get(key);
    }

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
        return Math.abs(x) % listSize;
    }

    private int stringHashCode(String s) {
        return Math.abs(s.hashCode()) % listSize;
    }
}
