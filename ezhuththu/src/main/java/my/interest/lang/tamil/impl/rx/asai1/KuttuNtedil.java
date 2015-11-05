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
public class KuttuNtedil implements PatternGenerator {
    public String generate(FeatureSet featureSet) {
        return "(?:"+new UgarakkuttuNtedil().generate(featureSet) +"|"+ new IgarakkuttuNtedil().generate(featureSet) + ")";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குற்றுநெடில்";
    }


    public String getDescription() {
        return "உகரக்குற்றுநெடில் அல்லது இகரக்குற்றுநெடில்";
    }
}
