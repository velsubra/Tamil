package tamil.lang.api.parser;

import my.interest.lang.tamil.impl.NumberDictionary;
import tamil.lang.api.dictionary.TamilDictionary;

/**
 * <p>
 *     Feature to be enabled to parse for a number.
 * </p>
 *
 * @author velsubra
 */
public final class ParseAsNumberFeature extends ParseWithDictionary {
    public  static  final   ParseAsNumberFeature FEATURE = new ParseAsNumberFeature();

    private  ParseAsNumberFeature() {

    }

    @Override
    public TamilDictionary getDictionary() {
        return NumberDictionary.INSTANCE;
    }
}
