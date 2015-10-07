package my.interest.lang.tamil.impl.rx.cir;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class NAsaichCheer implements PatternGenerator {


    private int min;
    private int max;

    public NAsaichCheer(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public String generate(FeatureSet set) {
        String singleAsai = new Asai().generate(set);

        StringBuffer buffer = new StringBuffer("(?:");
        buffer.append(singleAsai);
        if (min < 0) {
             min = -min;
        }
        buffer.append("{");
        buffer.append(min);
        if (min != max) {
            if (max<min) {
                buffer.append(",");
            } else {
                buffer.append(",");
                buffer.append(max);
            }
        }
        buffer.append("}");

        buffer.append(")");
        return buffer.toString();
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "N அசைகளைக்கொண்ட சீருக்கானபாங்கு  ";
    }

    public String getDescription() {
        return "N அசைகளைக்கொண்ட சீருக்கானபாங்கு  ";
    }
}
