package tamil.lang.api.dictionary;

/**
 * <p>
 * Feature enables to search for words  that start with the given words.
 * Words that exactly match with the given words wont be returned.  It can not be used along with {@link tamil.lang.api.dictionary.ExactMatchSearch}
 * </p>
 *
 * @author velsubra
 */
public final class StartsWithHigherLengthSearch extends DictionaryFeature {
    public static final StartsWithHigherLengthSearch FEATURE = new StartsWithHigherLengthSearch();

    private StartsWithHigherLengthSearch() {

    }
}
