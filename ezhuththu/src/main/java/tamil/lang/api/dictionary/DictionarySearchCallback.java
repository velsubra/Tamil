package tamil.lang.api.dictionary;

import tamil.lang.known.IKnownWord;

import java.util.regex.Matcher;

/**
 * Created by velsubra on 11/28/15.
 */
public interface DictionarySearchCallback {

    /**
     * Called when a match is found
     * @param index the index of the word found.
     * @param word the word that has the match
     * @param matcher the matcher
     * @return true if the search should continue else the search will return.
     */
    public  boolean  matchFound(int index, IKnownWord word, Matcher matcher);


}
