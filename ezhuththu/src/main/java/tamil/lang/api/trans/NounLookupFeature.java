package tamil.lang.api.trans;

/**
 * <p>
 * Feature that enables transliterating tv/ into    தொலைக்காட்சி assuming that the Tamil dictionary is populated with these words.
 * If the English noun (tv) is not populated  tv/ will effectively return tv.
 * The lookup character is removed. The lookup character is recognized only if there is at least one English character.
 *  <b>tv//</b>   will be transliterated into தொலைக்காட்சி/
 *
 * </p>
 *
 * @see tamil.lang.api.feature.FeatureConstants#TRANSLIT_JOIN_FEATURE_VAL_110
 *
 * @author velsubra
 */
public final class NounLookupFeature extends TranslitFeature {

    /**
     * The singleton instance with the default lookup character.
     */
    public static final NounLookupFeature INSTANCE = new NounLookupFeature();

    /**
     * The lookup character which is specified at the end of the noun. It should be generally be a special character. The default character is /.
     *
     * @return the lookup character.
     */
    public char getLookupChar() {
        return lookupChar;
    }

    /**
     * Creates  the lookup feature with the default lookup character which is /.
     */
    private NounLookupFeature() {

    }

    /**
     * Creates a new lookup feature with the specified lookup feature.
     * @param c  the character to be used as the lookup character.
     */
    public NounLookupFeature(char c) {
        this.lookupChar = c;
    }

    private char lookupChar = '/';
}
