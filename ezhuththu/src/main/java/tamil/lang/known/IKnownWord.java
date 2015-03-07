package tamil.lang.known;

import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;

import java.util.Set;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface IKnownWord extends Comparable {

    public TamilWord getWord();

    public TamilWord getType();

    public Set<String> getPropertyNames();

    public String getProperty(String name);
}
