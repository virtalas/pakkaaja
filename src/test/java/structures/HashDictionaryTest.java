
package structures;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashDictionaryTest {
    
    private HashDictionary dict;

    @Before
    public void setUp() {
        dict = new HashDictionary(10);
    }

    @Test
    public void testPutGet() {
        dict.put(1, "one");
        dict.put(2, "two");
        dict.put("three", 3);
        
        assertEquals("one", dict.get(1));
        assertEquals(3, dict.get("three"));
        assertEquals("two", dict.get(2));
    }

    @Test
    public void testContains() {
        assertFalse(dict.containsKey(1));
        
        dict.put(1, "one");
        dict.put(2, "two");
        dict.put("three", 3);
        
        assertTrue(dict.containsKey(1));
        assertTrue(dict.containsKey(2));
        assertTrue(dict.containsKey("three"));
    }
    
    @Test
    public void testWorksWithCollisions() {
        dict = new HashDictionary(1);
        
        dict.put(1, "one");
        dict.put(2, "two");
        dict.put("three", 3);
        
        assertTrue(dict.containsKey(1));
        assertTrue(dict.containsKey(2));
        assertTrue(dict.containsKey("three"));
        
        assertEquals("one", dict.get(1));
        assertEquals(3, dict.get("three"));
        assertEquals("two", dict.get(2));
    }
}
