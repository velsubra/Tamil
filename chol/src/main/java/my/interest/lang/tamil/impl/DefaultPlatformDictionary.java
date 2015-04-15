package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.impl.dictionary.DefaultPlatformDictionaryBase;
import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;
import tamil.lang.spi.TamilDictionaryProvider;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class DefaultPlatformDictionary implements TamilDictionaryProvider {

    public DefaultPlatformDictionary() {

    }



    @Override
    public TamilDictionary create() {
        return  PersistenceInterface.get();
    }
}
