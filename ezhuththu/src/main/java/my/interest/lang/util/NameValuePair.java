package my.interest.lang.util;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class NameValuePair<K, V> implements Serializable {
    private K name;
    private V value;

    public NameValuePair(K name, V value) {
        this.name = name;
        this.value = value;
    }

    public NameValuePair() {
    }

    public void setName(K name) {
        this.name = name;
    }

    public K getName() {
        return name;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void setPropertyName(String name) {
        ((NameValuePair<String, String>)this).setName(name);
    }

    public void setPropertyValue(String value) {
        ((NameValuePair<String, String>)this).setValue(value);
    }


    public V getValue() {
        return value;
    }

//    public int compareTo(Object o) {
//
//        return ((Comparable)name).compareTo(((NameValuePair)o).name);
//    }
}
