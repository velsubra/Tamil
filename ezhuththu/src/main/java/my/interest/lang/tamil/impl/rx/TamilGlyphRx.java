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
public class TamilGlyphRx implements PatternGenerator {
    public String generate() {
        // return  "\\u0bcd";
        return  "[\\u0bcd-\\u0bcd\\u0bbe-\\u0bd7]";
    }


    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }
  public String getName() {
        return "கொக்கி";
    }
    public String getDescription() {
        return "Represent Tamil Glyph";
    }
}
