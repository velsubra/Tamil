package my.interest.lang.tamil.impl.rx.asai1;

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
public class Kuttukkurril implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:"+new Ugarakkuttukkurril().generate(featureSet) +"|"+ new Igarakkuttukkurril().generate(featureSet) + ")";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குற்றுக்குறில்";
    }


    public String getDescription() {
        return "உகரக்குற்றுக்குறில் அல்லது இகரக்குற்றுக்குறில்";
    }
}
