package my.interest.lang.tamil.parser.impl.sax.filter.known;

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
public interface KnowWordFilter {

    /**
     *
     * @param recognized    should be returned as part of the list if this is a valid sequence
     * @param context   the context of parsing
     * @return   the filtered list, New words can be added into it.
     */
    public List<IKnownWord>  filter(IKnownWord recognized, ParsingContext context);
}
