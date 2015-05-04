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
public interface UnknownWordFilter {

    /**
     * Corrects  non-recognized varumozhi (varumozhiCandidate) and returns List<IKnownWord> when it can be corrected.
     * @param nilaimozhi
     * @param varumozhiCandidate the candidate to be corrected.
     * @param tail  Currently parsed tail
     * @return  the List of known words after correction, empty set when the candidate can not be corrected.
     */
    public List<IKnownWord> filterUnknown(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhiCandidate, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set);
}
