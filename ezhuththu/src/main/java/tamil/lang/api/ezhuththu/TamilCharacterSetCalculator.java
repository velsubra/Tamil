package tamil.lang.api.ezhuththu;

import tamil.lang.TamilCharacter;
import tamil.lang.exception.TamilPlatformException;

import java.util.Set;

/**
 * <p>
 *     The interface to find a set of Tamil letters satisfying the search criteria.  It is to be noted that the Tamil letters are not mere sequence of symbols as generally assumed by theory of computation.
 *     Tamil letters in fact for a framework of letter system that is very central to the Tamil language.  Tamil letters have properties that have very deep connection with other letters.
 *
 *
 * </p>
 * <p>
 *     This interface provides a way to search for Tamil letters.
 * </p>
 *
 * @author velsubra
 */
public interface TamilCharacterSetCalculator {

    /**
     * Method to find set of Tamil letters satisfying the query.
     * @param query  the query to calculate the letter set. The query can be anything from {@link EzhuththuSetDescription#getName()} or any compound word containing them. E.g) வலியுகரவரிசை
     * @return the letter set that is computed.
     * @throws TamilPlatformException  if there is any issue in finding the letter set.
     *
     * @see tamil.lang.api.regex.TamilRXCompiler#getRegEXDescriptions()
     */
    public Set<TamilCharacter>  find (String query) throws TamilPlatformException;

    /**
     * Evaluates set expression involving Tamil letter sets
     * @param expression the expression
     * @return the result of the evaluated expression
     * @throws TamilPlatformException
     */
    public Set<TamilCharacter>  evaluate (String expression) throws TamilPlatformException;

    /**
     * Returns all the known letter sets
     * @return  the set of letter set descriptions.
     */
    public Set<EzhuththuSetDescription> getEzhuththuSetDescriptions();
}

