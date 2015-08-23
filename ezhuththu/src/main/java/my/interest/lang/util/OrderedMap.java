package my.interest.lang.util;

import java.util.*;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class OrderedMap<K, V> extends HashMap<K, V> {
    protected List<NameValuePair<K, V>> list;

    public OrderedMap() {
        list = new ArrayList<NameValuePair<K, V>>();
    }

    public OrderedMap(List<NameValuePair<K, V>> list) {
        this.list = list;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Object key) {
        return keySet().contains(key);
    }

    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    public V get(Object key) {
        V obj = null;
        for (int i = 0; i < list.size(); i++) {
            if (key.equals(list.get(i).getName())) {
                obj = list.get(i).getValue();
                break;
            }
        }
        return obj;
    }

    public V put(K key, V value, boolean duplicatesAllowed) {
        V obj = null;
        if (!duplicatesAllowed && containsKey(key)) {
            obj = remove(key);
        }
        list.add(new NameValuePair(key, value));
        return obj;
    }

    public V put(K key, V value) {
        return put(key, value, false);
    }

    public V remove(Object key) {
        V obj = null;
        for (int i = 0; i < list.size(); i++) {
            if (key.equals(list.get(i).getName())) {
                obj = list.get(i).getValue();
                list.remove(i);
                break;
            }
        }
        return obj;
    }

    /*  public Object remove(Object key) {
        Object obj = null;
        for(int i=0;i<list.size();i++) {
            if(key.equals(list.get(i).getName())) {
                obj = list.get(i).getValue();
                list.remove(i);
                break;
            }
        }
        return obj;
    }*/

    public void putAll(Map t) {
        if (t == null)
            return;
        Set<Map.Entry<Object, Object>> set = t.entrySet();
        for (Map.Entry<Object, Object> e : set) {
            Object key = e.getKey();
            Object value = e.getValue();
            list.add(new NameValuePair(key, value));

        }
    }

    public void clear() {
        list.clear();
    }

    public Set<K> keySet() {
        Set<K> set = new LinkedHashSet<K>();
        for (int i = 0; i < list.size(); i++) {
            set.add(list.get(i).getName());
        }
        return set;
    }

    public Collection<V> values() {
        Collection<V> col = new ArrayList<V>();
        for (int i = 0; i < list.size(); i++) {
            col.add(list.get(i).getValue());
        }
        return col;
    }

    public Set<Map.Entry<K, V>> entrySet() {
        throw new RuntimeException("Not implemented");
    }

    public void setList(List<NameValuePair<K, V>> list) {
        this.list = list;
    }

    public List<NameValuePair<K, V>> getList() {
        return list;
    }

    public OrderedMap simpleClone() {
        OrderedMap map = new OrderedMap();
        map.setList((ArrayList)((ArrayList)getList()).clone());
        return map;
    }


}
