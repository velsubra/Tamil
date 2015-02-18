package tamil.lang.api.trans;

/**
 * <p>
 *     Feature that enables transliterating      அவன்idam into அவனிடம்
 * </p>
 *
 * @see tamil.lang.api.feature.Feature#TRANSLIT_NOUN_LOOKUP_FEATURE_VAL_115
 *
 *
 * @author velsubra
 */
public final class JoinFeature extends TranslitFeature {

    /*
    The singleton instance.
     */
    public static final JoinFeature  INSTANCE = new JoinFeature();
    private JoinFeature() {

    }
}
