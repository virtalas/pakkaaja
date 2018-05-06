package structures;

/**
 * Implements a linked list structure with a key and a value.
 */
public class LinkedDictionaryEntry {

    public Object key;
    public Object value;
    public LinkedDictionaryEntry next;

    /**
     * Adds a new key and its value to a new or empty LinkedDictionaryEntry. If
     * it can't be added to the current one, it is passed to the next one. For
     * already existing keys the value is overridden.
     * @param key key to add
     * @param value value to add
     */
    public void add(Object key, Object value) {
        if (empty()) {
            this.key = key;
            this.value = value;
        } else if (this.key.equals(key)) {
            this.value = value;
        } else if (this.next == null) {
            this.next = new LinkedDictionaryEntry();
            this.next.add(key, value);
        } else {
            this.next.add(key, value);
        }

    }

    /**
     * Finds and returns the value by a key if it exists. If it is not found it
     * the current entry, it is passed to the next entry.
     * @param key key
     * @return the value
     */
    public Object get(Object key) {
        if (key == null) {
            return null;
        }
        if (this.key != null && this.key.equals(key)) {
            return this.value;
        } else if (this.next != null) {
            return this.next.get(key);
        }
        return null;
    }

    public boolean contains(Object key) {
        return get(key) != null;
    }

    public boolean empty() {
        return key == null && value == null && next == null;
    }
}
