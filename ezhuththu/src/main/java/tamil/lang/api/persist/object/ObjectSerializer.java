package tamil.lang.api.persist.object;

/**
 * <p>
 *     The interface for object serializer
 * </p>
 *
 * @author velsubra
 * @see tamil.lang.api.job.JobManager
 */
public interface ObjectSerializer<T> {
    /**
     * The class type that this can serialize and  de-serialize
     * @return   the class
     */
    public Class<T> getTypeToSerialize();

    /**
     * De-serialize the byte data into an object
     * @param data the data
     * @return the de-serialized object
     * @throws Exception
     */
    public T deserialize(byte[] data) throws Exception;

    /**
     * Serializes a given object
     * @param object the object to be serialized
     * @return  the serialized data.
     * @throws Exception
     */
    public byte[] serialize(T object) throws  Exception;

}
