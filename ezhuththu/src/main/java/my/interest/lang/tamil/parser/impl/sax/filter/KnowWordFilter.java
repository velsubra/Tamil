package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface KnowWordFilter {

    public List<IKnownWord>  filter(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail);
}
