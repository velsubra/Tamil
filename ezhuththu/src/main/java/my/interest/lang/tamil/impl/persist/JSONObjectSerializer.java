package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.exception.service.ServiceException;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class JSONObjectSerializer implements ObjectSerializer<JSONObject> {
    public Class<JSONObject> getTypeToSerialize() {
        return JSONObject.class;
    }


    public SERIALIZED_TYPE getSerializedType() {
        return SERIALIZED_TYPE.JSON;
    }

    public JSONObject deserialize(byte[] data) {
        try {
            JSONObject json = new JSONObject(new String(data, TamilUtils.ENCODING));
            return json;

        } catch (Exception e) {
            throw new ServiceException("Unable to de-serialize object:" + e.getMessage());
        }
    }

    public byte[] serialize(JSONObject json) {

        try {
            return json.toString().getBytes(TamilUtils.ENCODING);
        } catch (Exception e) {
            throw new ServiceException("Unable to serialize object:" + e.getMessage());
        }

    }
}