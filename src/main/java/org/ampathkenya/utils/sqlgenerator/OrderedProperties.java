package org.ampathkenya.utils.sqlgenerator;

import java.util.*;

/**
 * Source thanks to Fyodor Kupolov
 * Additions by Roger Keay
 * {@link http://www.jroller.com/ejboy/entry/preserving_properties_order}
 */
public class OrderedProperties extends Properties {

    private Map<String, String> entries = new LinkedHashMap<String, String>();

    public Enumeration keys() {
        return Collections.enumeration(entries.keySet());
    }

    public Enumeration elements() {
        return Collections.enumeration(entries.values());
    }

    public boolean contains(Object value) {
        return entries.containsValue(value);
    }

    public void putAll(Map<? extends Object, ? extends Object> map) {
        entries.putAll((Map<? extends String, ? extends String>) map);
    }

    public int size() {
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public boolean containsKey(Object key) {
        return entries.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return entries.containsValue(value);
    }

    public String get(Object key) {
        return entries.get(key);
    }

    public String getProperty(String key) {
        Object oval = get(key);
        String sval = (oval instanceof String) ? (String)oval : null;
        return ((sval == null) && (defaults != null)) ? defaults.getProperty(key) : sval;
    }

    public String put(Object key, Object value) {
        return entries.put((String) key, (String) value);
    }

    public Object remove(Object key) {
        return entries.remove(key);
    }

    public void clear() {
        entries.clear();
    }

    public Set keySet() {
        return entries.keySet();
    }

    public Collection values() {
        return entries.values();
    }

    public Set entrySet() {
        return entries.entrySet();
    }

    public boolean equals(Object o) {
        return entries.equals(o);
    }

    public int hashCode() {
        return entries.hashCode();
    }
}