package my.interest.lang.tamil.impl.persist;

import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.api.persist.object.ObjectSerializerManager;
import tamil.lang.known.IKnownWord;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ObjectSerializerManagerImpl implements ObjectSerializerManager {
    private static Map<Class, ObjectSerializer> map = new HashMap<Class, ObjectSerializer>();

    static {
        map.put(IKnownWord.class, new KnownWordSerializer());
        map.put(Long.class, new CountSerializer());
        map.put(SearchResultSnippet.class, new SearchSnippetSerializer());
    }


    public <T> ObjectSerializer<T> findSerializer(Class<T> type) {
        return map.get(type);
    }
}
