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
public final class DefaultPlatformDictionary extends DefaultPlatformDictionaryBase implements TamilDictionaryProvider {

    public DefaultPlatformDictionary() {

    }


    /**
     * Adds a new word to the dictionary.
     *
     * @param word the known word to be added
     */
    @Override
    public void add(IKnownWord word) {
        PersistenceInterface.addOrUpdateKnown(word);
    }

    @Override
    public TamilDictionary create() {
        return this;
    }
}
