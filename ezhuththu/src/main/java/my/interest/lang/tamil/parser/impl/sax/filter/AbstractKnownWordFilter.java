package my.interest.lang.tamil.parser.impl.sax.filter;

import my.interest.lang.tamil.impl.FeatureSet;
import my.interest.lang.tamil.punar.TamilWordPartContainer;
import tamil.lang.api.dictionary.TamilDictionary;
import tamil.lang.known.IKnownWord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public abstract class AbstractKnownWordFilter implements KnowWordFilter {

    protected Class<? extends IKnownWord> type = null;

    protected AbstractKnownWordFilter(Class<? extends IKnownWord> type) {
        this.type = type;
    }


    public abstract List<IKnownWord> filterMatched(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail);

    public List<IKnownWord> filter(IKnownWord recognized, TamilWordPartContainer nilaimozhi, TamilWordPartContainer varumozhi, List<IKnownWord> tail, TamilDictionary dictionary, FeatureSet set) {
        if (type.isAssignableFrom(recognized.getClass())) {
            return filterMatched(recognized, nilaimozhi, varumozhi, tail);

        } else {
            return returnSingle(recognized);
        }
    }

    protected List<IKnownWord> ignore() {
        return Collections.emptyList();
    }

    protected List<IKnownWord> returnSingle(IKnownWord word) {
        List<IKnownWord> list = new ArrayList<IKnownWord>();
        list.add(word);
        return list;
    }
}
