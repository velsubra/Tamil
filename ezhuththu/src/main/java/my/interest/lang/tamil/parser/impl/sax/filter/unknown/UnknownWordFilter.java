package my.interest.lang.tamil.parser.impl.sax.filter.unknown;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.parser.impl.sax.context.ParsingContext;
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
     * @param context - the parsing context
     * @return  the List of known words after correction, empty set when the candidate can not be corrected.
     */
    public List<IKnownWord> filterUnknown(ParsingContext context);
}
