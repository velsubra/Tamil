package tamil.lang.api.trans;

import tamil.lang.TamilWord;

/**
 * <p>
 * The interface to perform  transliteration  to Tamil.
 * Please use {@link tamil.lang.TamilFactory#getTransliterator(String)} to get an instance of  Transliterator to Tamil.
 * <p/>
 * </p>
 *
 * @author velsubra
 */
public interface Transliterator {


    /**
     * Performs transliteration.
     *
     * @param text to be transliterated into tamil
     * @return the tamil word.
     */
    public TamilWord transliterate(String text);

    /**
     * Performs transliteration. Joining is just one of the features supported.   Please see {@link #transliterate(String, TranslitFeature...)} for passing more features.
     *
     * @param text to be transliterated into tamil
     * @param join flag indicating if the transliterated part of the word should be joined with the other part.
     *             when false, it is same as {@link #transliterate(String)},  அவன்idam will yield   அவன்இடம்.
     *             <b>when true, it performs புணர்ச்சி </b>to join the parts.
     *             ie) அவன்idam will yield அவனிடம்
     * @return the tamil word
     */
    public TamilWord transliterate(String text, boolean join);


    /**
     * Performs transliteration of text with the specified set of features.
     * <p>
     * Please see {@link tamil.lang.api.feature.FeatureConstants#TRANSLIT_JOIN_FEATURE_VAL_110}  and {@link tamil.lang.api.feature.FeatureConstants#TRANSLIT_NOUN_LOOKUP_FEATURE_VAL_115}
     * </p>
     *
     * @param text     the text to be   transliterated
     * @param features the list of TranslitFeature used to transliterate
     * @return the transliterated Tamil text.
     */
    public TamilWord transliterate(String text, TranslitFeature... features);


//
//    /**
//     * Performs transliteration of text with the specified set of features.
//     * <p>
//     * Please see {@link tamil.lang.api.feature.Feature#TRANSLIT_JOIN_FEATURE_VAL_110}  and {@link tamil.lang.api.feature.FeatureConstants#TRANSLIT_NOUN_LOOKUP_FEATURE_VAL_115}
//     * </p>
//     *
//     * @param text     the text to be   transliterated
//     * @param features the list of TranslitFeature used to transliterate
//     * @return the transliterated Tamil text.
//     */
//    public String translit(String text, TranslitFeature... features);
}
