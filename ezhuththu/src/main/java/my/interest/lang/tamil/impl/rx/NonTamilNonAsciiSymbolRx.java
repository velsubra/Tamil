package my.interest.lang.tamil.impl.rx;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.impl.rx.block.UnicodeBlockDescription;
import my.interest.lang.tamil.internal.api.PatternGenerator;
import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * Created by velsubra on 6/19/16.
 */
public class NonTamilNonAsciiSymbolRx implements PatternGenerator  {
    String name = null;
    public  NonTamilNonAsciiSymbolRx(String name) {
        this.name = name;


    }

    public String generate(FeatureSet set) {
        return  "[\\u0100-\\u0B01\\u0BCE-"+ UnicodeBlockDescription.getUniCodeString(LAST_UNICODE_CODEPOINT)+"]";
    }

    public Set<TamilCharacter> getCharacterSet() {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return "Code points outside Tamil block and ASCII. It is same as saying ${ஒருங்குறித்தொகுதிக்கு வெளியே[TAMIL,BASIC_LATIN]}" ;
    }
}
