package tamil.lang.api.regex;

/**
 * <p>
 *     The feature  allows to override the platform in-built definitions by the custom alias definitions. Please see {@link tamil.lang.api.ezhuththu.EzhuththuSetDescription}
 *     and {@link tamil.lang.api.regex.TamilRXCompiler} for all in-built definitions. This feature allows them to override them via alias definitions supported by {@link tamil.lang.api.regex.TamilRXCompiler#compile(String, tamil.util.IPropertyFinder, RXFeature...)}
 * </p>
 *
 * @author velsubra
 */
public final class RXOverrideSysDefnFeature extends RXFeature {
    private RXOverrideSysDefnFeature(){}

    public static final RXOverrideSysDefnFeature FEATURE  = new RXOverrideSysDefnFeature();
}
