
package structures;


public class LinkedDictionaryEntry {

    public Object key;
    public Object value;
    public LinkedDictionaryEntry next;
    
    public LinkedDictionaryEntry() {
        
    }
    
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
