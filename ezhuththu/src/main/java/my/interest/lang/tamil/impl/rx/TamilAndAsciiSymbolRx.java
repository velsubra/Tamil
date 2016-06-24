package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 6/19/16.
 */
public class TamilAndAsciiSymbolRx implements PatternGenerator {
    String name = null;
    public  TamilAndAsciiSymbolRx(String name) {
        this.name = name;
    }
    public String generate(FeatureSet set) {
        return  "[\\u0000-\\u00FF\\u0B02-\\u0BCD]";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "Code points that covers Tamil block and ASCII. It is same as ${ஒருங்குறித்தொகுதிக்கு உள்ளே[TAMIL,BASIC_LATIN]}";
    }
}
