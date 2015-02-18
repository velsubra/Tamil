package my.interest.lang.tamil.impl;

import my.interest.lang.tamil.internal.api.PersistenceInterface;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public final class DefaultPlatformDictionary implements TamilDictionary {

    private DefaultPlatformDictionary() {

    }

    public  static    final DefaultPlatformDictionary dict = new DefaultPlatformDictionary();


    @Override
    public List<IKnownWord> lookup(TamilWord word) {
        return PersistenceInterface.findMatchingDerivedWords(word, true, -1, null);
    }


    @Override
    public IKnownWord peek(TamilWord word) {
        List<IKnownWord> list = PersistenceInterface.findMatchingDerivedWords(word, true, 1, null);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public List<IKnownWord> search(TamilWord word, boolean exactMatch, int maxCount, List<Class<? extends IKnownWord>> includeTypes) {
        return PersistenceInterface.findMatchingDerivedWords(word, exactMatch, maxCount, includeTypes);
    }

    @Override
    public IKnownWord peekEnglish(String english) {
        TamilWord word = PersistenceInterface.lookupEnglish(english);
        if (word == null) return null;
        return peek(word);
    }
}
