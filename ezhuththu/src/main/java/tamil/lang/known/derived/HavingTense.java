package tamil.lang.known.derived;

import my.interest.lang.tamil.generated.types.SimpleTense;

/**
 * <p>
 *     Anything that has a tense associated.
 * </p>
 *
 * @author velsubra
 */
public interface HavingTense {

    /**
     * Gets the tense part in the word.
     * @return  non-null tense.
     */
    public SimpleTense getTense();
}
