package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.exception.service.ServiceException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class StringSerializer implements ObjectSerializer<String> {
    public Class<String> getTypeToSerialize() {
        return String.class;
    }



    public SERIALIZED_TYPE getSerializedType() {
        return SERIALIZED_TYPE.STRING;
    }

    public String deserialize(byte[] data) {
        try {
            return new String(data, TamilUtils.ENCODING);


        } catch (Exception e) {
            throw new ServiceException("Unable to de-serialize object:" + e.getMessage());
        }
    }

    public byte[] serialize(String json) {

        try {
            return json.getBytes(TamilUtils.ENCODING);
        } catch (Exception e) {
            throw new ServiceException("Unable to serialize object:" + e.getMessage());
        }

    }
}