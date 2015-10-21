package my.interest.lang.tamil.impl.persist;

import my.interest.lang.tamil.TamilUtils;
import tamil.lang.api.persist.object.ObjectSerializer;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CountSerializer implements ObjectSerializer<Long> {

    public Class<Long> getTypeToSerialize() {
        return Long.class;
    }

    public Long deserialize(byte[] data) throws Exception {
        return Long.parseLong(new String(data, TamilUtils.ENCODING));
    }

    public byte[] serialize(Long object) throws Exception {
        return String.valueOf(object).getBytes(TamilUtils.ENCODING);
    }
}
