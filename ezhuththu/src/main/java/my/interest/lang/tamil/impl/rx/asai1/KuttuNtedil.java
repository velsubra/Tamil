package my.interest.lang.tamil.impl.rx.asai1;

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
    public String generate() {
        return "(${வலியுகரவரிசை}${நெடிலுயிர்})";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }


    public String getName() {
        return "குற்றுநெடில்";
    }


    public String getDescription() {
        return "குஔ to be treated as a single character  கௌ";
    }
}
