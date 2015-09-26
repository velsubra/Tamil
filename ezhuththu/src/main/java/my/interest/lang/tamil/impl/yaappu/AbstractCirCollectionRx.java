package my.interest.lang.tamil.impl.yaappu;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractCirCollectionRx implements PatternGenerator {
    public String generate(FeatureSet set) {
        StringBuffer buffer = new StringBuffer("(?:");
        boolean first = true;
        for (String cir : getAllCirs()) {
            if(!first) {
                buffer.append("|");
            }
            first = false;
            buffer.append("${");
            buffer.append(cir);
            buffer.append("}");
        }
        buffer.append(")");
        return buffer.toString();
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public abstract List<String> getAllCirs();
}
