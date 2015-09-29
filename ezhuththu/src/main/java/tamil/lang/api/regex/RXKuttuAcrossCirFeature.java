package tamil.lang.api.regex;

import tamil.lang.api.feature.Feature;

/**
 * <p>
 *        The feature that allows to treat குற்றியலுகரம் that ends in a சீர்  when the next  சீர்  starts with an  உயிர் a மெய்
 * </p>
 *
 * @author velsubra
 */
public final class RXKuttuAcrossCirFeature extends RXFeature {
    private RXKuttuAcrossCirFeature(){}

    public static final RXKuttuAcrossCirFeature FEATURE  = new RXKuttuAcrossCirFeature();
}
