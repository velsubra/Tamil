package tamil.lang.api.parser;

import tamil.lang.api.feature.Feature;
import tamil.lang.api.trans.TranslitFeature;

/**
 * <p>
 *     The features that parses tamil text. See {@link CompoundWordParser#parse(tamil.lang.TamilWord, int, ParseFeature...)}
 * </p>
 *
 * @author velsubra
 */
public abstract class ParseFeature implements Feature {

    protected ParseFeature() {

    }
}
