package common.lang;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface Sentence<T extends Word> extends List<T> {
    public Para getContainingPara();
    public int getPreviousSpacesCount();
}
