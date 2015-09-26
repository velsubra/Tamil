package tamil.lang.api.regex;

/**
 * <p>
 *     The feature that allows to override the system definitions by the custom alias definitions.
 * </p>
 *
 * @author velsubra
 */
public class RXOverrideSysDefnFeature extends RXFeature {
    private RXOverrideSysDefnFeature(){}

    public static final RXOverrideSysDefnFeature FEATURE  = new RXOverrideSysDefnFeature();
}
