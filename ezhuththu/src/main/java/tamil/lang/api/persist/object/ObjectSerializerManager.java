package tamil.lang.api.persist.object;

/**
 * <p>
 *     The Object serializer manager
 * </p>
 *
 * @author velsubra
 */
public interface ObjectSerializerManager {

    /**
     * Finds the serializer for a type
     * @param type  the type for which the serializer is to be found
     * @param <T>
     * @return  the object serializer if found; null otherwise.
     */
    public <T> ObjectSerializer<T> findSerializer(Class<T> type);
}
