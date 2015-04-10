package tamil.lang.known.non.derived;

import tamil.lang.known.IKnownWord;

/**
 * <p>
 *     Base interface for all verbs   and verbs derivative.
 * </p>
 *
 * @author velsubra
 */
public interface IBaseVinai extends IKnownWord {

    /**
     * Returns the underlying root verb.
     * @return non null root verb
     */
    public Vinaiyadi getVinaiyadi();
}
