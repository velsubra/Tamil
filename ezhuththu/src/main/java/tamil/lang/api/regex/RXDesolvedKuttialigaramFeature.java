package tamil.lang.api.regex;

/**
 * <p>This feature allows to treat டி in பாட்டியாது  as குற்றியலிகரம்.
 * This can not differentiate if it was பாட்டு +  யாது or பாட்டி + யாது before the புணர்ச்சி was applied.
 * Note: {@link tamil.lang.api.regex.RXKuttuFeature} recognizes டு in பாட்டுயாது  குற்றியலிகரம் .
 *
 * </p>
 */

/**
 * Created by velsubra on 11/7/15.
 */
public class RXDesolvedKuttialigaramFeature extends RXFeature {
    private RXDesolvedKuttialigaramFeature(){}

    public static final RXDesolvedKuttialigaramFeature FEATURE  = new RXDesolvedKuttialigaramFeature();
}
