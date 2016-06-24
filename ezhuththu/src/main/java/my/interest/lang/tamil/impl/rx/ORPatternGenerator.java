package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class ORPatternGenerator implements PatternGenerator {

    /**
     * List of already generated patterns
     * @param set
     * @return
     */
    public abstract List<String> getList(FeatureSet set);

    public String generate(FeatureSet set) {


        List<String> patterns = getList(set);

        StringBuffer buffer = new StringBuffer();
        if (patterns.size() > 1) {
            buffer.append("(?:");
        }
        boolean first = true;
        for (String rx : patterns) {
            if (!first) {
                buffer.append("|");
            }
            first = false;
            buffer.append(rx);

        }
        if (patterns.size() > 1) {
            buffer.append(")");
        }
        return buffer.toString();


    }
}