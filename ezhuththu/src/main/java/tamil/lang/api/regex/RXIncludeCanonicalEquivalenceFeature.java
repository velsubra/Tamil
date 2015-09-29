package tamil.lang.api.regex;

/**
 * <p>
 *     We know that some Tamil letter has more than one code points when it is represented in Unicode. That itself is has gone against Tamil letter system.
 *     That is bad. The worse is some Tamil letters having more than one sequence of bytes. This feature allows the matcher to treat all the possible sequence the same.
 *     There shall be performance penalty in enabling it.
 *
 * </p>
 *
 * @author velsubra
 */
public final class RXIncludeCanonicalEquivalenceFeature extends RXFeature {
    private RXIncludeCanonicalEquivalenceFeature(){}

    public static final RXIncludeCanonicalEquivalenceFeature FEATURE  = new RXIncludeCanonicalEquivalenceFeature();
}
