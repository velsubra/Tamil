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
     * @param query  the query to calculate the letter set. The query can be anything from {@link EzhuththuDescription#getName()} or any compound word containing them.
     * @return the letter set
     * @throws TamilPlatformException  if there is any issue in finding the letter set.
     */
    public Set<TamilCharacter>  find (String query) throws TamilPlatformException;

    /**
     * Returns all the known letter sets
     * @return  the set of letter set descriptions.
     */
    public Set<EzhuththuDescription>  getEzhuththuDescriptions();
}
