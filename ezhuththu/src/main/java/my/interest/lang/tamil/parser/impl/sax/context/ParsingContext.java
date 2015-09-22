package my.interest.lang.tamil.parser.impl.sax.context;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */

public class ParsingContext {
    private ParsingContext() {

    }

    public TamilWordPartContainer nilaimozhi;
    public TamilWordPartContainer varumozhi;
    public List<IKnownWord> tail;
    public TamilDictionary dictionary;
    public FeatureSet set;

    public static ParsingContext contextFor(TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        ParsingContext context = new ParsingContext();
        context.dictionary = dictionary;
        context.nilaimozhi = nilaimozhi;
        context.set = set;
        context.tail = tail;
        context.varumozhi = varumozhi;
        return context;
    }

    public void mark() {

    }


}
