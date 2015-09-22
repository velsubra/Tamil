package tamil.lang.api.dictionary;

import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * The dictionary object that can be used to lookup known tamil words.
 * Use {@link tamil.lang.TamilFactory#getSystemDictionary()} to retrieve the default system dictionary.
 * </p>
 *
 * @author velsubra
 */
public interface TamilDictionary {

    /**
     * looks up for a known tamil word.
     * <p>Please note a single character sequence can have multiple meanings each of which corresponds to an instance of {@link IKnownWord}
     * </p>
     *
     * @param word the word to be looked at. Please see {@link tamil.lang.TamilWord#from(String)} to get a tamil word from a string.
     * @return the list of  tamil words  known to the system, empty list if the given character sequence is not known.
     * @see tamil.lang.TamilFactory#getSystemDictionary()
     */
    public List<IKnownWord> lookup(TamilWord word);


    /**
     * Gives a quick peek to see if there is any word.
     *
     * @param word to be looked up.
     * @return the first known word identified.
     */
    public IKnownWord peek(TamilWord word);


    /**
     * Searches for a specific word and of specific type.
     *
     * @param word         the word or first part of word to be searched.
     * @param exactMatch   flag to say if exact match has be performed. When false, words starting with the given word may be returned.
     * @param maxCount     the max count expected. The search returns after the maxCount is reached or when the search is finished.
     * @param includeTypes the list of classes indicating the types of word to be returned.
     * @return the list of words found matching the search criteria.
     */
    public List<IKnownWord> search(TamilWord word, boolean exactMatch, int maxCount, List<Class<? extends IKnownWord>> includeTypes);


    /**
     * Searches for a specific word and of specific type.
     *
     * @param word         the word or first part of word to be searched.
     * @param maxCount     the max count expected. The search returns after the maxCount is reached or when the search is finished.
     * @param includeTypes the list of classes indicating the types of word to be returned.
     * @param features     the features used while doing the search.
     * @return the list of words found matching the search criteria.
     */
    public List<IKnownWord> search(TamilWord word, int maxCount, List<Class<? extends IKnownWord>> includeTypes, DictionaryFeature... features);


    /**
     * Peeks a quick Tamil word from an english word
     *
     * @param english the english word
     * @return the known tamil word.
     */
    public IKnownWord peekEnglish(String english);


    /**
     * Suggests words for a specific word, and of specific type.
     *
     * @param word         the word or first part of word to be searched.
     * @param maxCount     the max count expected. The suggestion search returns after the maxCount is reached or when the search is finished.
     * @param includeTypes the list of classes indicating the types of word to be returned.
     * @return the list of words that are suggested in the  context.  Never null.
     */
    public List<IKnownWord> suggest(TamilWord word, int maxCount, List<Class<? extends IKnownWord>> includeTypes);


    /**
     * Adds a new word to the dictionary.
     *
     * @param word the known word to be added
     */
    public void add(IKnownWord word);


    /**
     * Returns the size of the dictionary
     *
     * @return the size >=0
     */
    public int size();


    /**
     * Gets the matching   IKnownWord if that already exists by doing a object equals comparison.
     *
     * @param known the word to check.
     * @return the known word if found. else returns null.
     */
    public <T extends IKnownWord> T peek(T known);


    /**
     * Returns set of classes of words that are not extended.
     * @return  set of classes that are marked final.
     */
    public Collection<Class<? extends IKnownWord>> getWordTypes();


    /**
     * Gets sub-dictionary whose words belong to the only given types
     * @param type  the type. Please see {@link #getWordTypes()}  for all available known types.
     * @return  returns null if there is no such mini dictionary.
     */
    public TamilDictionary getMiniDictionaryForWordType(Class<? extends IKnownWord> type);

}
