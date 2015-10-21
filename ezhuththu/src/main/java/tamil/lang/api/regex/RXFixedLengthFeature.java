package tamil.lang.api.regex;

/**
 * <p>
 *     Feature that enforces the fixed length pattern to be generated. Java has  limitations on look-behind.
 * </p>
 *
 * @author velsubra
 */
public class RXFixedLengthFeature  extends RXFeature {
    private RXFixedLengthFeature(){}

    public static final RXFixedLengthFeature FEATURE  = new RXFixedLengthFeature();
}
