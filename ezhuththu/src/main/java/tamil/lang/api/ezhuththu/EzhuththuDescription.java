package tamil.lang.api.ezhuththu;

import tamil.lang.TamilCharacter;

import java.util.Set;

/**
 * <p>
 *    Represents known letter set.
 * </p>
 *
 * @author velsubra
 */
public interface EzhuththuDescription {

    /**
     * The name of the set
     * @return  the name
     */
    public String getName();

    /**
     * The descriptions for set.
     * @return the description of the letter set.
     */
    public String getDescription();

    /**
     * The letter set.
     * @return the set containing all the letters in this set.
     */
    public Set<TamilCharacter> getCharacterSet();
}
