package tamil.lang.api.persist.object;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface ObjectSerializerManager {

    public <T> ObjectSerializer<T> findSerializer(Class<T> type);
}
