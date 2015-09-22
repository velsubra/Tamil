package tamil.lang.api.parser;

import tamil.lang.api.dictionary.TamilDictionary;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class ParseWithDictionary extends ParseFeature {
    public abstract TamilDictionary getDictionary();
}
