package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.TamilWord;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public interface KnowWordFilter {

    /**
     *
     * @param recognized    should be returned as part of the list if this is a valid sequence
     * @param nilaimozhi   the remaining
     * @param varumozhi   the actual word recognized
     * @param tail   the tail already recognized
     * @return   the filtered list, New words can be added into it.
     */
    public List<IKnownWord>  filter(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set);
}
