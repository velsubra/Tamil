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

    public static enum SERIALIZED_TYPE {
        COUNT, STRING, JSON
    }

    /**
     * Gives the hint to the content of serialized data
     * @return  SERIALIZED_TYPE
     */
    public SERIALIZED_TYPE getSerializedType();
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
