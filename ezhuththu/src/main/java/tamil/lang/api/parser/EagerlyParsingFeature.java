package tamil.lang.api.parser;

/**
 * <p>
 * The feature that allows to find sub words of larger length.
 * when on  நேரத்தை wont be split as   நேர்  +  அத்தை    instead return as     நேரத்தை (நேரம்+ஐ) as a single word.
 * </p>
 *
 * @author velsubra
 */
public final class EagerlyParsingFeature extends ParseFeature {
    public static  final EagerlyParsingFeature FEATURE = new EagerlyParsingFeature();
    private EagerlyParsingFeature() {

    }
}
