package common.lang;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface Word<T extends common.lang.Character> extends List<T> {
    public Sentence getContainingSentence();

    public int getNextSpacesCount();
}
