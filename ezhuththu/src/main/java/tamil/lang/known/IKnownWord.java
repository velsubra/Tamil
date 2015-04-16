package tamil.lang.known;

import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;

import java.util.Set;

/**
 * <p>
 *     It is the base for all the known words. {@link tamil.lang.api.dictionary.TamilDictionary} contains only known words.
 *     Words include noun, verb and prepositions and all the derived words.
 * </p>
 *
 * @author velsubra
 */
public interface IKnownWord extends Comparable {

    /**
     * The character sequence of the known word
     * @return the tamil word; never null; never empty.
     */

    public TamilWord getWord();

    /**
     * The grammatical type of the word. The class  itself will tell the  word type. However, This can be used for display purpose.
     *
     * @return the character sequence of the type of the word; never null, never empty.
     */
    public TamilWord getType();

    /**
     * Custom properties that are available
     * @return  the set of properties. It could be null or empty to indicate there is no custom properties.
     */
    public Set<String> getPropertyNames();

    /**
     * Gets the value of a property.
     * @param name  the name of the property.
     * @return the value of the property, null if the property does not exist.
     */
    public String getProperty(String name);
}
