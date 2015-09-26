package my.interest.lang.tamil.impl.rx.thalhai;

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
public abstract class AbstractThalhaiRx implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return  "${தளை[("+ getFirstPattern()+") முன் "+ getSecondPattern()+"]}";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

   public abstract String getFirstPattern();
    public abstract String getSecondPattern();
}
