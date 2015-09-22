package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class TamilSymbolRx implements PatternGenerator {
    public String generate() {
       return  "[\\u0B02-\\u0BCD]";
    }

    public String getName() {
        return "எழுத்துவடிவம்";
    }

    public String getDescription() {
        return "Represent any Tamil code point. Note: It may not be a full Tamil character. ";
    }



    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
}
