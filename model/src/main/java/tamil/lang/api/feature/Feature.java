package tamil.lang.api.feature;

import tamil.lang.api.trans.JoinFeature;
import tamil.lang.api.trans.NounLookupFeature;

/**
 * <p>
 *     Represents a feature.
 * </p>
 * @see tamil.lang.api.trans.Transliterator
 * @author velsubra
 */
public interface Feature {

    /**
     * Join feature supported by Transliteration
     * @see tamil.lang.api.trans.JoinFeature
     */
    public static final JoinFeature TRANSLIT_JOIN_FEATURE =  JoinFeature.INSTANCE;

    /**
     * Noun lookup feature supported by  Transliteration
     * @see tamil.lang.api.trans.NounLookupFeature
     */
    public static final NounLookupFeature TRANSLIT_NOUN_LOKKUP_FEATURE = NounLookupFeature.INSTANCE;

}
