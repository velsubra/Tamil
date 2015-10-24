package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import org.json.JSONObject;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.persist.object.ObjectSerializer;
import tamil.lang.exception.service.ServiceException;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class KnownWordSerializer implements ObjectSerializer<IKnownWord> {
    public Class<IKnownWord> getTypeToSerialize() {
        return IKnownWord.class;
    }



    public SERIALIZED_TYPE getSerializedType() {
        return SERIALIZED_TYPE.JSON;
    }

    public IKnownWord deserialize(byte[] data) {
        try {
            JSONObject json = new JSONObject(new String(data, TamilUtils.ENCODING));
            if (json.has("word")) {
                String word = json.getString("word");
                List<IKnownWord> list = TamilFactory.getSystemDictionary().lookup(TamilWord.from(word));
                String type = json.getString("type");
                for (IKnownWord kn : list) {
                    if (kn.getClass().getName().equals(type)) {
                        return kn;
                    }
                }
            }
            return null;

        } catch (Exception e) {
            throw new ServiceException("Unable to de-serialize object:" + e.getMessage());
        }
    }

    public byte[] serialize(IKnownWord object) {
        JSONObject json = new JSONObject();
        try {
            if (object != null) {
                json.put("word", object.getWord().toString());
                json.put("type", object.getClass().getName());
            }
            return json.toString().getBytes(TamilUtils.ENCODING);
        } catch (Exception e) {
            throw new ServiceException("Unable to serialize object:" + e.getMessage());
        }

    }
}
