package structures;

import java.util.LinkedList;

public class HashDictionary {

    private LinkedList[] lists;

    private int listSize;
    private int numberOfElements;

    public HashDictionary(int initSize) {
        listSize = initSize;
        numberOfElements = 0;
        lists = new LinkedList[initSize];
        for (int i = 0; i < initSize; i++) {
            lists[i] = new LinkedList();
        }
    }

    public void put(Object key, Object value) {
//        listFor(key).add(value).;
        numberOfElements++;
    }

    public boolean contains(Object key) {
        LinkedList list = listFor(key);
        if (key == null || list == null) {
            return false;
        }
        return list.contains(key);
    }
    
    public Object get(Object key) {
        
        return null;
    }

    private LinkedList listFor(Object key) {
        LinkedList list = null;

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
