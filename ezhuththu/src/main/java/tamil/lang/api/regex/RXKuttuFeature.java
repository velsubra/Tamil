package tamil.lang.api.regex;

/**
 * <p>
 *     The feature that allows to treat குஅ a single குறில். Also குஔ as a single நெடில்  while matching an  அசை.
 *
 * </p>
 *
 * @author velsubra
 */
public final class RXKuttuFeature extends RXFeature {
    private RXKuttuFeature(){}

    public static final RXKuttuFeature FEATURE  = new RXKuttuFeature();
}
