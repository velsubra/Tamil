package tamil.lang.api.parser;

import tamil.lang.api.dictionary.TamilDictionary;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class ParseWithCustomDictionary extends  ParseWithDictionary  {

    TamilDictionary dictionary = null;

    public ParseWithCustomDictionary(TamilDictionary dictionary) {
       this.dictionary = dictionary;
    }
    @Override
    public TamilDictionary getDictionary() {
        return dictionary;
    }
}
