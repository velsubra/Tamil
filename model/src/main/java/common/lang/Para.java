package common.lang;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface Para<T extends Sentence> extends List<T> {
    public int getPreviousNewLinesCount();
}
